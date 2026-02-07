import { useEffect, useState } from 'react';
import { useSearchParams, Link, useNavigate } from 'react-router-dom';
import { Clock, MapPin, Users, ArrowRight, Loader2 } from 'lucide-react';
import { routeApi } from '../services/api';
import type { RouteResponse, BusResponse } from '../types';

export default function SearchResults() {
  const [searchParams] = useSearchParams();
  const origin = searchParams.get('origin') || '';
  const dest = searchParams.get('dest') || '';
  const navigate = useNavigate();

  const [routes, setRoutes] = useState<RouteResponse[]>([]);
  const [buses, setBuses] = useState<Record<number, BusResponse[]>>({});
  const [expandedRoute, setExpandedRoute] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (origin && dest) {
      setLoading(true);
      routeApi.search(origin, dest)
        .then(res => setRoutes(res.data))
        .catch(console.error)
        .finally(() => setLoading(false));
    }
  }, [origin, dest]);

  const loadBuses = async (routeId: number) => {
    if (expandedRoute === routeId) {
      setExpandedRoute(null);
      return;
    }
    if (!buses[routeId]) {
      const res = await routeApi.getBuses(routeId);
      setBuses(prev => ({ ...prev, [routeId]: res.data }));
    }
    setExpandedRoute(routeId);
  };

  const formatTime = (iso: string) => {
    const date = new Date(iso);
    return date.toLocaleString('en-US', {
      weekday: 'short', month: 'short', day: 'numeric',
      hour: 'numeric', minute: '2-digit',
    });
  };

  const formatDuration = (minutes: number) => {
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    return `${h}h ${m}m`;
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-32">
        <Loader2 className="h-8 w-8 text-primary-600 animate-spin" />
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900">
          {origin} <ArrowRight className="inline h-5 w-5 mx-2" /> {dest}
        </h1>
        <p className="text-gray-500 mt-1">{routes.length} route{routes.length !== 1 ? 's' : ''} found</p>
      </div>

      {routes.length === 0 ? (
        <div className="card p-12 text-center">
          <p className="text-gray-500 text-lg mb-4">No routes found for this search.</p>
          <Link to="/" className="btn-primary">Try another search</Link>
        </div>
      ) : (
        <div className="space-y-4">
          {routes.map(route => (
            <div key={route.id} className="card overflow-hidden">
              <button
                onClick={() => loadBuses(route.id)}
                className="w-full p-5 text-left hover:bg-gray-50 transition-colors"
              >
                <div className="flex items-center justify-between">
                  <div className="flex-1">
                    <div className="flex items-center gap-3 mb-2">
                      <span className="font-semibold text-gray-900">{route.origin}</span>
                      <ArrowRight className="h-4 w-4 text-gray-400" />
                      <span className="font-semibold text-gray-900">{route.destination}</span>
                    </div>
                    <div className="flex items-center gap-4 text-sm text-gray-500">
                      <span className="flex items-center gap-1">
                        <MapPin className="h-3.5 w-3.5" />
                        {route.distanceKm} km
                      </span>
                      <span className="flex items-center gap-1">
                        <Clock className="h-3.5 w-3.5" />
                        {formatDuration(route.durationMinutes)}
                      </span>
                      <span className="text-gray-400">by {route.companyName}</span>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="text-2xl font-bold text-primary-600">${route.basePrice}</p>
                    <p className="text-xs text-gray-400">per seat</p>
                  </div>
                </div>
              </button>

              {/* Expanded bus list */}
              {expandedRoute === route.id && buses[route.id] && (
                <div className="border-t border-gray-100 bg-gray-50 p-4">
                  {buses[route.id].length === 0 ? (
                    <p className="text-gray-500 text-sm text-center py-4">No available buses for this route.</p>
                  ) : (
                    <div className="space-y-3">
                      <p className="text-sm font-medium text-gray-700 mb-3">Available Departures</p>
                      {buses[route.id].map(bus => (
                        <div key={bus.id} className="bg-white rounded-lg p-4 flex items-center justify-between border border-gray-200">
                          <div>
                            <p className="font-medium text-gray-900">{formatTime(bus.departureTime)}</p>
                            <div className="flex items-center gap-3 mt-1 text-sm text-gray-500">
                              <span>Bus #{bus.busNumber}</span>
                              <span className="flex items-center gap-1">
                                <Users className="h-3.5 w-3.5" />
                                {bus.availableSeats} seats left
                              </span>
                            </div>
                          </div>
                          <div className="flex items-center gap-4">
                            <span className="text-lg font-bold text-primary-600">${bus.pricePerSeat}</span>
                            <button
                              onClick={(e) => { e.stopPropagation(); sessionStorage.setItem(`bus-${bus.id}`, JSON.stringify(bus)); navigate(`/book/${bus.id}`); }}
                              className="btn-primary py-2 px-4 text-sm"
                            >
                              Book Now
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
