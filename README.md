# 📚 HỆ THỐNG QUẢN LÝ THƯ VIỆN (Library Management System)

Hệ thống RESTful API quản lý thư viện cho phép quản lý sách, người dùng và nghiệp vụ mượn/trả sách.  
Dự án được xây dựng theo định hướng backend thực tế, chú trọng vào kiến trúc, xử lý nghiệp vụ và bảo toàn dữ liệu.

---

## 🚀 Công nghệ sử dụng

- Java 17
- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security (JWT)
  - Spring Validation
- MySQL
- Hibernate
- Lombok
- MapStruct
- JUnit 5 & MockMvc
- Maven

---

## 🏗 Kiến trúc hệ thống

Dự án tổ chức theo mô hình phân tầng:

controller
service
repository
entity
dto
mapper
exception
security

Nguyên tắc áp dụng:

- Phân tách trách nhiệm rõ ràng (Separation of Concerns)
- Sử dụng DTO để bảo vệ Entity
- Xử lý Exception tập trung (Global Exception Handler)
- Quản lý Transaction 
- Soft Delete để đảm bảo toàn vẹn dữ liệu
- Phân quyền theo Role

---

## 🗄 Thiết kế cơ sở dữ liệu

### Các bảng chính:

- User
- Role
- Book
- Author
- Category
- Borrow
- BorrowDetail

### Quan hệ chính:

- User (1) — (N) Borrow
- Borrow (1) — (N) BorrowDetail
- Book (1) — (N) BorrowDetail
- User (N) — (N) Role
- Author (1) — (N) Book
- Category (1) — (N) Book

---

## 🔐 Xác thực & Phân quyền

- Đăng nhập sử dụng JWT
- Hệ thống Stateless
- Phân quyền theo Role:

### ADMIN:

- Thêm / Sửa / Xóa sách
- Xem toàn bộ lịch sử mượn

### USER:

- Tìm kiếm sách
- Mượn sách
- Trả sách
- Xem lịch sử của mình

---

## 📌 Chức năng chính

### 📖 Quản lý sách

- Thêm / Cập nhật / Xóa mềm (Soft Delete)
- Tìm kiếm theo tên, tác giả, thể loại
- Phân trang (Pagination)
- Validate dữ liệu đầu vào
- Kiểm tra trùng sách

---

### 📚 Quản lý mượn/trả

- Một lần mượn có thể nhiều sách
- Kiểm tra tồn kho trước khi mượn
- Giảm số lượng sách khi mượn
- Tăng lại số lượng khi trả
- Theo dõi trạng thái:
  - BORROWED
  - RETURNED
  - OVERDUE
- Sử dụng Transaction để đảm bảo tính toàn vẹn dữ liệu

---

## 🧠 Điểm nổi bật về xử lý nghiệp vụ

### 1. Transaction Management

Nghiệp vụ mượn sách được xử lý trong `@Transactional` nhằm:

- Tránh trạng thái cập nhật dở dang
- Đảm bảo dữ liệu nhất quán

---

### 2. Soft Delete

Sách không bị xóa khỏi database mà chỉ cập nhật trạng thái:

```sql
UPDATE book SET is_deleted = true WHERE id = ?

Giúp:

Không mất lịch sử mượn

Không phá vỡ quan hệ khóa ngoại

3. Quản lý trạng thái tài khoản

User có trường status để:

Khóa tài khoản

Vô hiệu hóa

Ngăn đăng nhập khi vi phạm
```

🧪 Kiểm thử

Unit test cho tầng Service


#### 🛠 Hướng dẫn chạy dự án
1. Clone project

git clone <https://github.com/canhhocit/Library-Management.git>

2. Cấu hình database

spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


3. Chạy ứng dụng

`mvn spring-boot:run`

---

## API endpoints: nằm trong file `APItest.md`

----- chạy docker image thì phải tạo db `library_db` trong mysql-identity