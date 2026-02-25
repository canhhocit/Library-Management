# 📚 Library Management System API

> Spring Boot REST API cho hệ thống quản lý thư viện.

**Base URL:** `http://localhost:8080/my-lib`

---

## 📋 Mục lục

- [User API](#-user-api)
- [Book API](#-book-api)
- [Category API](#-category-api)
- [Author API](#-author-api)
- [Response Format](#-response-format)

---

## 👤 User API

**Endpoint:** `/api/users`

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `POST` | `/api/users` | Tạo user mới |
| `GET` | `/api/users` | Lấy tất cả users |
| `GET` | `/api/users/{username}` | Tìm user theo username |
| `GET` | `/api/users/search?fullName=` | Tìm user theo họ tên |
| `PUT` | `/api/users/{username}` | Cập nhật user |
| `DELETE` | `/api/users/{username}` | Xóa user |

**Ví dụ tạo user** `POST /api/users`
```json
{
  "username": "nguyenvana",
  "password": "123456",
  "fullName": "Nguyen Van A"
}
```

---

## 📖 Book API

**Endpoint:** `/api/books`

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `GET` | `/api/books` | Lấy tất cả sách (chưa xóa) |
| `GET` | `/api/books/{id}` | Lấy sách theo ID |
| `GET` | `/api/books/search?keyword=` | Tìm sách theo tiêu đề |
| `GET` | `/api/books/available` | Lấy sách còn trong kho |
| `POST` | `/api/books` | Tạo sách mới |
| `PUT` | `/api/books/{id}` | Cập nhật sách |
| `DELETE` | `/api/books/{id}` | Xóa sách *(soft delete)* |

**Ví dụ tạo sách** `POST /api/books`
```json
{
  "title": "Clean Code",
  "description": "A handbook of agile software craftsmanship",
  "publishYear": 2008,
  "quantity": 5,
  "category": { "id": 1 },
  "authors": [{ "id": 1 }]
}
```

> 💡 **Soft Delete:** Sách bị xóa sẽ không hiện trong danh sách, nhưng dữ liệu vẫn còn trong database.

---

## 🏷️ Category API

**Endpoint:** `/api/categories`

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `GET` | `/api/categories` | Lấy tất cả category |
| `GET` | `/api/categories/{id}` | Lấy category theo ID |
| `POST` | `/api/categories` | Tạo category mới |
| `PUT` | `/api/categories/{id}` | Cập nhật category |
| `DELETE` | `/api/categories/{id}` | Xóa category |

**Ví dụ tạo category** `POST /api/categories`
```json
{
  "name": "Công nghệ",
  "description": "Sách về lập trình và công nghệ thông tin"
}
```

---

## ✍️ Author API

**Endpoint:** `/api/authors`

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `GET` | `/api/authors` | Lấy tất cả tác giả |
| `GET` | `/api/authors?name=` | Tìm tác giả theo tên |
| `POST` | `/api/authors` | Tạo tác giả mới |
| `PUT` | `/api/authors/{id}` | Cập nhật tác giả |
| `DELETE` | `/api/authors/{id}` | Xóa tác giả |

**Ví dụ tạo tác giả** `POST /api/authors`
```json
{
  "name": "Robert C. Martin",
  "biography": "Tác giả nổi tiếng với cuốn Clean Code"
}
```

---

## 📦 Response Format

Tất cả API trả về định dạng chuẩn `ApiResponse`:

```json
{
  "code": 1000,
  "message": "Thành công",
  "result": { }
}
```

**Mã lỗi thường gặp:**

| Code | Ý nghĩa |
|------|---------|
| `1000` | Thành công |
| `1002` | User đã tồn tại |
| `1003` | User không tồn tại |
| `2001` | Category không tìm thấy |
| `3001` | Book không tìm thấy |
| `4001` | Author không tìm thấy |
| `9999` | Lỗi hệ thống |

---

## 🛠️ Công nghệ sử dụng

- **Java 17** + **Spring Boot 3.x**
- **Spring Data JPA** + **MySQL**
- **MapStruct** — mapping DTO
- **Lombok** — giảm boilerplate code

## ⚙️ Cấu hình

Chỉnh sửa file `application.properties`:



> 🔐 **Tài khoản admin mặc định:** `username: admin` / `password: 123`  