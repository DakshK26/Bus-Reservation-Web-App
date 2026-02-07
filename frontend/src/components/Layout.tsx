import { Outlet, Link, useNavigate } from 'react-router-dom';
import { Bus, LogOut, Ticket, User } from 'lucide-react';
import { useAuthStore } from '../store/authStore';

export default function Layout() {
  const { user, isAuthenticated, logout } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="min-h-screen flex flex-col">
      {/* Navbar */}
      <header className="bg-white border-b border-gray-200 sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <Link to="/" className="flex items-center gap-2 text-primary-600 font-bold text-xl">
              <Bus className="h-6 w-6" />
              <span>BusReserve</span>
            </Link>

            <nav className="flex items-center gap-4">
              <Link to="/" className="text-sm text-gray-600 hover:text-gray-900 transition-colors">
                Search
              </Link>
              {isAuthenticated ? (
                <>
                  <Link to="/bookings" className="flex items-center gap-1 text-sm text-gray-600 hover:text-gray-900 transition-colors">
                    <Ticket className="h-4 w-4" />
                    My Bookings
                  </Link>
                  <div className="flex items-center gap-3 pl-4 border-l border-gray-200">
                    <span className="flex items-center gap-1 text-sm text-gray-500">
                      <User className="h-4 w-4" />
                      {user?.displayName}
                    </span>
                    <button
                      onClick={handleLogout}
                      className="flex items-center gap-1 text-sm text-gray-500 hover:text-red-600 transition-colors"
                    >
                      <LogOut className="h-4 w-4" />
                    </button>
                  </div>
                </>
              ) : (
                <div className="flex items-center gap-2">
                  <Link to="/login" className="btn-secondary text-sm py-2 px-4">
                    Log In
                  </Link>
                  <Link to="/register" className="btn-primary text-sm py-2 px-4">
                    Sign Up
                  </Link>
                </div>
              )}
            </nav>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1">
        <Outlet />
      </main>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-8">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex flex-col md:flex-row items-center justify-between gap-4">
            <div className="flex items-center gap-2 text-white font-semibold">
              <Bus className="h-5 w-5" />
              BusReserve
            </div>
            <p className="text-sm">
              Event-driven reservation platform built with Spring Boot, Kafka, BigQuery, and React.
            </p>
            <div className="flex gap-4 text-sm">
              <a href="/swagger-ui.html" target="_blank" rel="noopener" className="hover:text-white transition-colors">
                API Docs
              </a>
              <a href="https://github.com" target="_blank" rel="noopener" className="hover:text-white transition-colors">
                GitHub
              </a>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
}
