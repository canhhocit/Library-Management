package com.canhhocit.Library_Managerment.services;

import com.canhhocit.Library_Managerment.dto.request.BorrowDetailRequest;
import com.canhhocit.Library_Managerment.dto.request.BorrowRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.BorrowResponse;
import com.canhhocit.Library_Managerment.entities.*;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.mapper.BorrowMapper;
import com.canhhocit.Library_Managerment.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowService {
    
    BorrowRepository borrowRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    BorrowMapper borrowMapper;

    public ApiResponse<List<BorrowResponse>> getAllBorrows() {
        List<BorrowResponse> borrows = borrowRepository.findAll()
                .stream()
                .map(borrowMapper::toBorrowResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<BorrowResponse>>builder()
                .code(1000)
                .message("Lấy danh sách mượn sách thành công")
                .result(borrows)
                .build();
    }
    
    public ApiResponse<BorrowResponse> getBorrowById(Long id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BORROW_NOT_FOUND));
        
        return ApiResponse.<BorrowResponse>builder()
                .code(1000)
                .message("Lấy thông tin mượn sách thành công")
                .result(borrowMapper.toBorrowResponse(borrow))
                .build();
    }
    
    public ApiResponse<List<BorrowResponse>> getBorrowsByUserId(Long userId) {
        List<BorrowResponse> borrows = borrowRepository.findByUserId(userId)
                .stream()
                .map(borrowMapper::toBorrowResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<BorrowResponse>>builder()
                .code(1000)
                .message("Lấy lịch sử mượn sách thành công")
                .result(borrows)
                .build();
    }
    
    @Transactional
    public ApiResponse<BorrowResponse> createBorrow(BorrowRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        
        // new borrow
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBorrowDate(LocalDateTime.now());
        borrow.setStatus("BORROWING");
        
        // new borrow details
        List<BorrowDetail> borrowDetails = new ArrayList<>();
        for (BorrowDetailRequest detailRequest : request.getBooks()) {
            Book book = bookRepository.findById(detailRequest.getBookId())
                    .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
            
            // check sl
            if (book.getQuantity() < detailRequest.getQuantity()) {
                throw new AppException(ErrorCode.BOOK_OUT_OF_STOCK);
            }
            
            // update sl book
            book.setQuantity(book.getQuantity() - detailRequest.getQuantity());
            bookRepository.save(book);
            
            // new borrow detail
            BorrowDetail detail = new BorrowDetail();
            BorrowDetailId detailId = new BorrowDetailId();
            detail.setId(detailId);
            detail.setBorrow(borrow);
            detail.setBook(book);
            detail.setQuantity(detailRequest.getQuantity());
            
            borrowDetails.add(detail);
        }
        
        borrow.setBorrowDetails(borrowDetails);
        Borrow savedBorrow = borrowRepository.save(borrow);
        
        return ApiResponse.<BorrowResponse>builder()
                .code(1000)
                .message("Mượn sách thành công")
                .result(borrowMapper.toBorrowResponse(savedBorrow))
                .build();
    }
    
    @Transactional
    public ApiResponse<BorrowResponse> returnBorrow(Long id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BORROW_NOT_FOUND));
        
        if ("RETURNED".equals(borrow.getStatus())) {
            throw new AppException(ErrorCode.BORROW_ALREADY_RETURNED);
        }
        
        // update status và return date
        borrow.setStatus("RETURNED");
        borrow.setReturnDate(LocalDateTime.now());
        
        // update sl book
        for (BorrowDetail detail : borrow.getBorrowDetails()) {
            Book book = detail.getBook();
            book.setQuantity(book.getQuantity() + detail.getQuantity());
            bookRepository.save(book);
        }
        
        Borrow updatedBorrow = borrowRepository.save(borrow);
        
        return ApiResponse.<BorrowResponse>builder()
                .code(1000)
                .message("Trả sách thành công")
                .result(borrowMapper.toBorrowResponse(updatedBorrow))
                .build();
    }
}