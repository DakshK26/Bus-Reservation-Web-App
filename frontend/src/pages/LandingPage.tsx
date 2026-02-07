import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, MapPin, Clock, Shield, Zap } from 'lucide-react';

export default function LandingPage() {
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const navigate = useNavigate();

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (origin.trim() && destination.trim()) {
      navigate(`/search?origin=${encodeURIComponent(origin)}&dest=${encodeURIComponent(destination)}`);
    }
  };

  const popularRoutes = [
    { from: 'New York', to: 'Boston', price: '$24.99' },
    { from: 'Los Angeles', to: 'San Francisco', price: '$39.99' },
    { from: 'Chicago', to: 'Detroit', price: '$27.99' },
    { from: 'Miami', to: 'Orlando', price: '$34.99' },
    { from: 'Seattle', to: 'Portland', price: '$24.99' },
    { from: 'Dallas', to: 'Houston', price: '$29.99' },
  ];

  return (
    <div>
      {/* Hero Section */}
      <section className="relative bg-gradient-to-br from-primary-600 via-primary-700 to-primary-900 text-white overflow-hidden">
        <div className="absolute inset-0 bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNjAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA2MCA2MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxnIGZpbGw9IiNmZmYiIGZpbGwtb3BhY2l0eT0iMC4wNSI+PGNpcmNsZSBjeD0iMzAiIGN5PSIzMCIgcj0iMiIvPjwvZz48L2c+PC9zdmc+')] opacity-40"></div>
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
          <div className="text-center max-w-3xl mx-auto">
            <h1 className="text-4xl md:text-6xl font-bold tracking-tight mb-6">
              Book Your Bus.
              <br />
              <span className="text-primary-200">Travel Smarter.</span>
            </h1>
            <p className="text-lg md:text-xl text-primary-100 mb-10 max-w-2xl mx-auto">
              Find and book affordable bus tickets across the country. Real-time availability, instant confirmation, and the best prices.
            </p>

            {/* Search Form */}
            <form onSubmit={handleSearch} className="bg-white rounded-2xl p-2 shadow-2xl max-w-2xl mx-auto">
              <div className="flex flex-col sm:flex-row gap-2">
                <div className="relative flex-1">
                  <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400" />
                  <input
                    type="text"
                    placeholder="From where?"
                    value={origin}
                    onChange={(e) => setOrigin(e.target.value)}
                    className="w-full pl-10 pr-4 py-3.5 rounded-xl text-gray-900 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500 text-sm"
                  />
                </div>
                <div className="relative flex-1">
                  <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-gray-400" />
                  <input
                    type="text"
                    placeholder="To where?"
                    value={destination}
                    onChange={(e) => setDestination(e.target.value)}
                    className="w-full pl-10 pr-4 py-3.5 rounded-xl text-gray-900 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500 text-sm"
                  />
                </div>
                <button type="submit" className="btn-primary py-3.5 px-8 rounded-xl flex items-center gap-2">
                  <Search className="h-4 w-4" />
                  Search
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="text-center">
              <div className="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4">
                <Zap className="h-6 w-6 text-primary-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Instant Confirmation</h3>
              <p className="text-gray-500 text-sm">Event-driven booking with real-time status updates powered by Kafka streaming.</p>
            </div>
            <div className="text-center">
              <div className="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center mx-auto mb-4">
                <Shield className="h-6 w-6 text-green-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Secure Payments</h3>
              <p className="text-gray-500 text-sm">JWT-authenticated API with optimistic locking to guarantee your seats.</p>
            </div>
            <div className="text-center">
              <div className="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center mx-auto mb-4">
                <Clock className="h-6 w-6 text-purple-600" />
              </div>
              <h3 className="text-lg font-semibold mb-2">Fast Search</h3>
              <p className="text-gray-500 text-sm">Redis-cached route searches with sub-50ms response times.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Popular Routes */}
      <section className="py-16 bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <h2 className="text-2xl font-bold text-center mb-10">Popular Routes</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {popularRoutes.map((route, i) => (
              <button
                key={i}
                onClick={() => {
                  setOrigin(route.from);
                  setDestination(route.to);
                  navigate(`/search?origin=${encodeURIComponent(route.from)}&dest=${encodeURIComponent(route.to)}`);
                }}
                className="card p-5 text-left hover:shadow-md hover:border-primary-300 transition-all group"
              >
                <div className="flex items-center justify-between">
                  <div>
                    <p className="font-semibold text-gray-900 group-hover:text-primary-600 transition-colors">
                      {route.from} &rarr; {route.to}
                    </p>
                    <p className="text-sm text-gray-500 mt-1">Multiple daily departures</p>
                  </div>
                  <div className="text-right">
                    <p className="text-lg font-bold text-primary-600">from {route.price}</p>
                  </div>
                </div>
              </button>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}
