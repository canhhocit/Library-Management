const API_BASE_URL = '/my-lib/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// Request Interceptor to add JWT token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response Interceptor to handle unauthorized errors
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.reload();
        }
        return Promise.reject(error);
    }
);

const AuthAPI = {
    login: (credentials) => api.post('/auth/login', credentials),
};

const BookAPI = {
    getAll: () => api.get('/books'),
    getById: (id) => api.get(`/books/${id}`),
    create: (book) => api.post('/books', book),
    update: (id, book) => api.put(`/books/${id}`, book),
    delete: (id) => api.delete(`/books/${id}`),
    search: (keyword) => api.get(`/books/search?keyword=${keyword}`),
    getAvailable: () => api.get('/books/available')
};

const AuthorAPI = {
    getAll: (name = '') => api.get(`/authors${name ? '?name=' + name : ''}`),
    create: (author) => api.post('/authors', author),
    update: (id, author) => api.put(`/authors/${id}`, author),
    delete: (id) => api.delete(`/authors/${id}`)
};

const CategoryAPI = {
    getAll: () => api.get('/categories'),
    create: (category) => api.post('/categories', category),
    update: (id, category) => api.put(`/categories/${id}`, category)
};

const UserAPI = {
    getAll: () => api.get('/users'),
    create: (user) => api.post('/users', user),
    update: (id, user) => api.put(`/users/${id}`, user),
    delete: (id) => api.delete(`/users/${id}`),
    getMyInfo: () => api.get('/users/myInfo') // Note: need to implement this in backend
};

const BorrowAPI = {
    getAll: () => api.get('/borrows'),
    getById: (id) => api.get(`/borrows/${id}`),
    getByUserId: (userId) => api.get(`/borrows/user/${userId}`),
    create: (borrowRequest) => api.post('/borrows', borrowRequest),
    returnBook: (id) => api.put(`/borrows/${id}/return`)
};
