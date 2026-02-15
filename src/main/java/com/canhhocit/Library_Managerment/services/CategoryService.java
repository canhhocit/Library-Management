package com.canhhocit.Library_Managerment.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.canhhocit.Library_Managerment.dto.request.CategoryRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.CategoryResponse;
import com.canhhocit.Library_Managerment.entities.Category;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.mapper.CategoryMapper;
import com.canhhocit.Library_Managerment.repositories.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
        CategoryRepository categoryRepository;
        CategoryMapper categoryMapper;

        // getAll
        public ApiResponse<List<CategoryResponse>> getAllCategory() {
                List<CategoryResponse> categories = categoryRepository.findAll()
                                .stream()
                                .map(categoryMapper::toCategoryResponse)
                                .collect(Collectors.toList());

                return ApiResponse.<List<CategoryResponse>>builder()
                                .code(1000)
                                .message("Lấy danh sách category thành công")
                                .result(categories)
                                .build();
        }

        // getbyID
        public ApiResponse<CategoryResponse> getCategoryById(Long id) {
                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                return ApiResponse.<CategoryResponse>builder()
                                .code(1000)
                                .message("Lấy category thành công")
                                .result(categoryMapper.toCategoryResponse(category))
                                .build();
        }

        // Create
        public ApiResponse<CategoryResponse> createCategory(CategoryRequest request) {
                // request -> entity
                Category category = categoryMapper.toCategory(request);

                Category savedCategory = categoryRepository.save(category);

                return ApiResponse.<CategoryResponse>builder()
                                .code(1000)
                                .message("Tạo category thành công")
                                .result(categoryMapper.toCategoryResponse(savedCategory))
                                .build();
        }

        // Update
        public ApiResponse<CategoryResponse> updateCategory(Long id, CategoryRequest request) {
                Category oldCategory = categoryRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                oldCategory.setName(request.getName());
                oldCategory.setDescription(request.getDescription());

                Category updatedCategory = categoryRepository.save(oldCategory);

                return ApiResponse.<CategoryResponse>builder()
                                .code(1000)
                                .message("Cập nhật category thành công")
                                .result(categoryMapper.toCategoryResponse(updatedCategory))
                                .build();
        }

        public ApiResponse<Void> deleteCategory(Long id) {
                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                categoryRepository.delete(category);
                return ApiResponse.<Void>builder()
                                .code(1000)
                                .message("Xóa category thành công")
                                .build();
        }
}