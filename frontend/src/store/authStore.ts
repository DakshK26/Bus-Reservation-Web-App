import { create } from 'zustand';
import type { User } from '../types';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  login: (user: User) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => {
  // Restore from localStorage on init
  const stored = localStorage.getItem('user');
  const token = localStorage.getItem('token');
  const initial = stored && token ? JSON.parse(stored) as User : null;

  return {
    user: initial,
    isAuthenticated: !!initial,
    login: (user: User) => {
      localStorage.setItem('token', user.token);
      localStorage.setItem('user', JSON.stringify(user));
      set({ user, isAuthenticated: true });
    },
    logout: () => {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      set({ user: null, isAuthenticated: false });
    },
  };
});
