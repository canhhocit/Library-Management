document.addEventListener('DOMContentLoaded', () => {
    // State management
    const state = {
        user: JSON.parse(localStorage.getItem('user')) || null,
        token: localStorage.getItem('token') || null,
        currentPage: 'dashboard'
    };

    // DOM Elements
    const loginPage = document.getElementById('login-page');
    const mainApp = document.getElementById('main-app');
    const loginForm = document.getElementById('login-form');
    const loginError = document.getElementById('login-error');
    const pageTitle = document.getElementById('page-title');
    const contentArea = document.getElementById('content-area');
    const userFullName = document.getElementById('user-fullname');
    const btnLogout = document.getElementById('btn-logout');
    const navLinks = document.querySelectorAll('#sidebar .nav-link');
    const navUsers = document.getElementById('nav-users');

    // Initialize App
    function init() {
        if (state.token) {
            showMainApp();
            renderPage(state.currentPage);
        } else {
            showLogin();
        }
    }

    function showLogin() {
        loginPage.classList.remove('d-none');
        mainApp.classList.add('d-none');
    }

    function showMainApp() {
        loginPage.classList.add('d-none');
        mainApp.classList.remove('d-none');
        userFullName.textContent = state.user.username;
        
        // Hide users menu if not Admin
        if (state.user.role !== 'ADMIN') {
            navUsers.classList.add('d-none');
        } else {
            navUsers.classList.remove('d-none');
        }
    }

    // Login Logic
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await AuthAPI.login({ username, password });
            const { token, role } = response.data.result;
            
            state.token = token;
            state.user = { username, role };
            
            localStorage.setItem('token', token);
            localStorage.setItem('user', JSON.stringify(state.user));
            
            showMainApp();
            renderPage('dashboard');
            loginError.classList.add('d-none');
        } catch (error) {
            console.error(error);
            loginError.textContent = 'Sai tên đăng nhập hoặc mật khẩu';
            loginError.classList.remove('d-none');
        }
    });

    // Logout Logic
    btnLogout.addEventListener('click', () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.reload();
    });

    // Navigation logic
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const page = link.getAttribute('data-page');
            
            // Update active state
            navLinks.forEach(l => l.classList.remove('active'));
            link.classList.add('active');
            
            state.currentPage = page;
            renderPage(page);
        });
    });

    // Page Router
    async function renderPage(page) {
        contentArea.innerHTML = '<div class="text-center mt-5"><div class="spinner-border text-primary" role="status"></div></div>';
        
        const titles = {
            'dashboard': 'Tổng quan',
            'books': 'Quản lý Sách',
            'categories': 'Quản lý Thể loại',
            'authors': 'Quản lý Tác giả',
            'borrows': 'Mượn & Trả sách',
            'users': 'Quản lý Người dùng'
        };
        
        pageTitle.textContent = titles[page] || 'Library Management';

        switch(page) {
            case 'dashboard':
                renderDashboard();
                break;
            case 'books':
                renderBooks();
                break;
            case 'categories':
                renderCategories();
                break;
            case 'authors':
                renderAuthors();
                break;
            case 'borrows':
                renderBorrows();
                break;
            case 'users':
                renderUsers();
                break;
            default:
                contentArea.innerHTML = '<h2>Trang đang phát triển</h2>';
        }
    }

    // --- Page Rendering Functions ---

    async function renderDashboard() {
        try {
            const booksRes = await BookAPI.getAll();
            const borrowsRes = await BorrowAPI.getAll();
            const authorsRes = await AuthorAPI.getAll();
            
            const bookCount = booksRes.data.result.length;
            const borrowCount = borrowsRes.data.result.length;
            const authorCount = authorsRes.data.result.length;
            
            contentArea.innerHTML = `
                <div class="row g-4">
                    <div class="col-md-4">
                        <div class="card bg-primary text-white h-100 shadow-sm border-0 card-hover">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="card-title text-uppercase opacity-75">Tổng số Sách</h6>
                                        <h2 class="display-5 fw-bold">${bookCount}</h2>
                                    </div>
                                    <i class="fas fa-book fa-3x opacity-25"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card bg-success text-white h-100 shadow-sm border-0 card-hover">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="card-title text-uppercase opacity-75">Phiếu mượn</h6>
                                        <h2 class="display-5 fw-bold">${borrowCount}</h2>
                                    </div>
                                    <i class="fas fa-exchange-alt fa-3x opacity-25"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card bg-info text-white h-100 shadow-sm border-0 card-hover">
                            <div class="card-body">
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <h6 class="card-title text-uppercase opacity-75">Tác giả</h6>
                                        <h2 class="display-5 fw-bold">${authorCount}</h2>
                                    </div>
                                    <i class="fas fa-user-edit fa-3x opacity-25"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mt-5">
                    <h3>Sách mới cập nhật</h3>
                    <div class="table-responsive table-container mt-3">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Tên sách</th>
                                    <th>Số lượng</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${booksRes.data.result.slice(0, 5).map(book => `
                                    <tr>
                                        <td>${book.title}</td>
                                        <td>${book.quantity}</td>
                                        <td><span class="badge ${book.quantity > 0 ? 'bg-success' : 'bg-danger'}">${book.quantity > 0 ? 'Còn sách' : 'Hết sách'}</span></td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
        } catch (error) {
            contentArea.innerHTML = '<div class="alert alert-danger">Lỗi khi tải dữ liệu dashboard</div>';
        }
    }

    async function renderBooks() {
        try {
            const response = await BookAPI.getAll();
            const books = response.data.result;
            
            contentArea.innerHTML = `
                <div class="d-flex justify-content-end mb-3">
                    <button class="btn btn-primary" id="btn-add-book">
                        <i class="fas fa-plus me-2"></i> Thêm sách mới
                    </button>
                </div>
                <div class="table-responsive table-container">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Mã</th>
                                <th>Tiêu đề</th>
                                <th>Năm XB</th>
                                <th>Số lượng</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${books.map(book => `
                                <tr>
                                    <td>${book.id}</td>
                                    <td>${book.title}</td>
                                    <td>${book.publishYear}</td>
                                    <td>${book.quantity}</td>
                                    <td>
                                        <button class="btn btn-sm btn-info btn-action text-white" onclick="editBook(${book.id})"><i class="fas fa-edit"></i></button>
                                        <button class="btn btn-sm btn-danger btn-action" onclick="deleteBook(${book.id})"><i class="fas fa-trash"></i></button>
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            `;
            
            document.getElementById('btn-add-book').onclick = () => showBookModal();
        } catch (error) {
            contentArea.innerHTML = '<div class="alert alert-danger">Lỗi khi tải danh sách sách</div>';
        }
    }

    // Placeholder for other pages
    async function renderCategories() {
        try {
            const res = await CategoryAPI.getAll();
            const categories = res.data.result;
            contentArea.innerHTML = `
                <div class="d-flex justify-content-end mb-3">
                    <button class="btn btn-primary" id="btn-add-category">Thêm thể loại</button>
                </div>
                <div class="table-container">
                    <table class="table table-hover">
                        <thead><tr><th>ID</th><th>Tên thể loại</th><th>Mô tả</th></tr></thead>
                        <tbody>
                            ${categories.map(c => `<tr><td>${c.id}</td><td>${c.name}</td><td>${c.description}</td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            `;
        } catch (e) { contentArea.innerHTML = '<div class="alert alert-danger">Lỗi</div>'; }
    }

    async function renderAuthors() {
        try {
            const res = await AuthorAPI.getAll();
            const authors = res.data.result;
            contentArea.innerHTML = `
                <div class="table-container">
                    <table class="table table-hover">
                        <thead><tr><th>ID</th><th>Tên tác giả</th><th>Tiểu sử</th></tr></thead>
                        <tbody>
                            ${authors.map(a => `<tr><td>${a.id}</td><td>${a.name}</td><td>${a.biography}</td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            `;
        } catch (e) { contentArea.innerHTML = '<div class="alert alert-danger">Lỗi</div>'; }
    }

    async function renderUsers() {
        try {
            const res = await UserAPI.getAll();
            const users = res.data.result;
            contentArea.innerHTML = `
                <div class="table-container">
                    <table class="table table-hover">
                        <thead><tr><th>Username</th><th>Họ tên</th><th>Quyền</th><th>Trạng thái</th></tr></thead>
                        <tbody>
                            ${users.map(u => `<tr><td>${u.username}</td><td>${u.fullName}</td><td>${u.role}</td><td>${u.status}</td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            `;
        } catch (e) { contentArea.innerHTML = '<div class="alert alert-danger">Lỗi access denied hoặc Server error</div>'; }
    }

    async function renderBorrows() {
        try {
            const res = await BorrowAPI.getAll();
            const borrows = res.data.result;
            contentArea.innerHTML = `
                <div class="d-flex justify-content-end mb-3">
                    <button class="btn btn-success" id="btn-create-borrow">Lập phiếu mượn</button>
                </div>
                <div class="table-container">
                    <table class="table table-hover">
                        <thead><tr><th>ID</th><th>Người mượn</th><th>Ngày mượn</th><th>Ngày trả</th><th>Trạng thái</th><th>Hành động</th></tr></thead>
                        <tbody>
                            ${borrows.map(b => `
                                <tr>
                                    <td>${b.id}</td>
                                    <td>${b.user.username}</td>
                                    <td>${new Date(b.borrowDate).toLocaleString()}</td>
                                    <td>${b.returnDate ? new Date(b.returnDate).toLocaleString() : '-'}</td>
                                    <td><span class="badge ${b.status === 'BORROWING' ? 'bg-warning' : 'bg-success'}">${b.status}</span></td>
                                    <td>
                                        ${b.status === 'BORROWING' ? `<button class="btn btn-sm btn-primary" onclick="returnBook(${b.id})">Trả sách</button>` : ''}
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            `;
        } catch (e) { contentArea.innerHTML = '<div class="alert alert-danger">Lỗi</div>'; }
    }

    // --- Modals and Form Handling ---

    async function showBookModal(bookId = null) {
        let book = { title: '', publishYear: '', quantity: 0, authorId: 1, categoryId: 1 };
        if (bookId) {
            const res = await BookAPI.getById(bookId);
            book = res.data.result;
        }

        const authorsRes = await AuthorAPI.getAll();
        const categoriesRes = await CategoryAPI.getAll();

        const modalHtml = `
            <div class="modal fade" id="bookModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">${bookId ? 'Sửa sách' : 'Thêm sách mới'}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form id="book-form">
                                <div class="mb-3">
                                    <label class="form-label">Tiêu đề</label>
                                    <input type="text" class="form-control" id="book-title" value="${book.title}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Năm xuất bản</label>
                                    <input type="number" class="form-control" id="book-year" value="${book.publishYear}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" class="form-control" id="book-qty" value="${book.quantity}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Tác giả</label>
                                    <select class="form-select" id="book-author">
                                        ${authorsRes.data.result.map(a => `<option value="${a.id}" ${book.author && book.author.id == a.id ? 'selected' : ''}>${a.name}</option>`).join('')}
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Thể loại</label>
                                    <select class="form-select" id="book-category">
                                        ${categoriesRes.data.result.map(c => `<option value="${c.id}" ${book.category && book.category.id == c.id ? 'selected' : ''}>${c.name}</option>`).join('')}
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Lưu lại</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.getElementById('modal-container').innerHTML = modalHtml;
        const modal = new bootstrap.Modal(document.getElementById('bookModal'));
        modal.show();

        document.getElementById('book-form').addEventListener('submit', async (e) => {
            e.preventDefault();
            const data = {
                title: document.getElementById('book-title').value,
                publishYear: document.getElementById('book-year').value,
                quantity: parseInt(document.getElementById('book-qty').value),
                authorId: parseInt(document.getElementById('book-author').value),
                categoryId: parseInt(document.getElementById('book-category').value)
            };

            try {
                if (bookId) await BookAPI.update(bookId, data);
                else await BookAPI.create(data);
                modal.hide();
                renderBooks();
            } catch (err) { alert('Lỗi khi lưu sách'); }
        });
    }

    async function showBorrowModal() {
        const booksRes = await BookAPI.getAvailable();
        const usersRes = await UserAPI.getAll();

        const modalHtml = `
            <div class="modal fade" id="borrowModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Lập phiếu mượn sách</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form id="borrow-form">
                                <div class="mb-3">
                                    <label class="form-label">Người mượn</label>
                                    <select class="form-select" id="borrow-user">
                                        ${usersRes.data.result.map(u => `<option value="${u.id}">${u.username} (${u.fullName})</option>`).join('')}
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Sách mượn</label>
                                    <select class="form-select" id="borrow-book">
                                        ${booksRes.data.result.map(b => `<option value="${b.id}">${b.title} (Còn: ${b.quantity})</option>`).join('')}
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" class="form-control" id="borrow-qty" value="1" min="1" required>
                                </div>
                                <button type="submit" class="btn btn-success w-100">Xác nhận mượn</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.getElementById('modal-container').innerHTML = modalHtml;
        const modal = new bootstrap.Modal(document.getElementById('borrowModal'));
        modal.show();

        document.getElementById('borrow-form').addEventListener('submit', async (e) => {
            e.preventDefault();
            const data = {
                userId: parseInt(document.getElementById('borrow-user').value),
                books: [{
                    bookId: parseInt(document.getElementById('borrow-book').value),
                    quantity: parseInt(document.getElementById('borrow-qty').value)
                }]
            };

            try {
                await BorrowAPI.create(data);
                modal.hide();
                renderBorrows();
            } catch (err) { alert(err.response?.data?.message || 'Lỗi khi mượn sách'); }
        });
    }

    // Global action handlers (exposed for onclick)
    window.editBook = (id) => showBookModal(id);
    window.deleteBook = async (id) => {
        if (confirm('Bạn có chắc chắn muốn xóa sách này?')) {
            try {
                await BookAPI.delete(id);
                renderBooks();
            } catch (err) { alert('Lỗi khi xóa sách'); }
        }
    };

    window.returnBook = async (id) => {
        if (confirm('Xác nhận trả sách cho phiếu mượn này?')) {
            try {
                await BorrowAPI.returnBook(id);
                renderBorrows();
            } catch (err) { alert('Lỗi khi trả sách'); }
        }
    };

    init();
});
