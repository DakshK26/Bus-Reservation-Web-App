import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '/api',
  headers: { 'Content-Type': 'application/json' },
});

// Attach JWT token from localStorage
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle 401 responses
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;

// --- Auth ---
export const authApi = {
  register: (data: { username: string; name: string; email: string; password: string }) =>
    api.post('/auth/register', data),
  login: (data: { username: string; password: string }) =>
    api.post('/auth/login', data),
  companyRegister: (data: { name: string; email: string; password: string }) =>
    api.post('/company/auth/register', data),
  companyLogin: (data: { email: string; password: string }) =>
    api.post('/company/auth/login', data),
};

// --- Routes ---
export const routeApi = {
  search: (origin: string, dest: string) =>
    api.get(`/routes?origin=${encodeURIComponent(origin)}&dest=${encodeURIComponent(dest)}`),
  getById: (id: number) => api.get(`/routes/${id}`),
  getBuses: (routeId: number) => api.get(`/routes/${routeId}/buses`),
  getOrigins: () => api.get('/routes/origins'),
  getDestinations: () => api.get('/routes/destinations'),
};

// --- Bookings ---
export const bookingApi = {
  create: (data: { busId: number; seats: number }) =>
    api.post('/bookings', data),
  getAll: () => api.get('/bookings'),
  getById: (id: number) => api.get(`/bookings/${id}`),
  cancel: (id: number) => api.delete(`/bookings/${id}`),
};

// --- Analytics ---
export const analyticsApi = {
  bookingsByDay: () => api.get('/admin/analytics/bookings-by-day'),
  revenueByRoute: () => api.get('/admin/analytics/revenue-by-route'),
  revenueByCompany: () => api.get('/admin/analytics/revenue-by-company'),
  confirmationLatency: () => api.get('/admin/analytics/confirmation-latency'),
};
