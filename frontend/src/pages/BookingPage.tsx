import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { MapPin, Clock, Users, Minus, Plus, Loader2, CheckCircle } from 'lucide-react';
import { bookingApi } from '../services/api';
import { useAuthStore } from '../store/authStore';
import { toast } from 'sonner';
import type { BusResponse } from '../types';

export default function BookingPage() {
  const { busId } = useParams<{ busId: string }>();
  const navigate = useNavigate();
  const isAuthenticated = useAuthStore((s) => s.isAuthenticated);

  const [bus, setBus] = useState<BusResponse | null>(null);
  const [seats, setSeats] = useState(1);
  const [loading, setLoading] = useState(true);
  const [booking, setBooking] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    // We need to fetch the bus info. We'll get it from the buses list via route.
    // For now, we use a simulated approach - fetch all buses for a dummy route.
    // In practice, we'd have a GET /api/buses/{id} endpoint or pass state.
    setLoading(false);
  }, [busId, isAuthenticated, navigate]);

  // Fetch bus data from search params or via API
  useEffect(() => {
    const storedBus = sessionStorage.getItem(`bus-${busId}`);
    if (storedBus) {
      setBus(JSON.parse(storedBus));
      setLoading(false);
    } else {
      // Fallback: we'd need a direct bus endpoint. For now show loading then redirect.
      setLoading(false);
    }
  }, [busId]);

  const handleBook = async () => {
    if (!busId) return;
    setBooking(true);
    try {
      const res = await bookingApi.create({ busId: parseInt(busId), seats });
      toast.success('Booking created! Confirming...');
      navigate(`/bookings/${res.data.id}`);
    } catch (err: unknown) {
      const error = err as { response?: { data?: { message?: string } } };
      toast.error(error?.response?.data?.message || 'Booking failed');
    } finally {
      setBooking(false);
    }
  };

  const formatTime = (iso: string) => {
    return new Date(iso).toLocaleString('en-US', {
      weekday: 'long', month: 'long', day: 'numeric',
      hour: 'numeric', minute: '2-digit',
    });
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-32">
        <Loader2 className="h-8 w-8 text-primary-600 animate-spin" />
      </div>
    );
  }

  if (!bus) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-16 text-center">
        <p className="text-gray-500 mb-4">Bus information not available. Please search again.</p>
        <button onClick={() => navigate('/')} className="btn-primary">Back to Search</button>
      </div>
    );
  }

  const totalPrice = (bus.pricePerSeat * seats).toFixed(2);

  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Complete Your Booking</h1>

      <div className="card p-6 mb-6">
        <div className="flex items-center gap-3 mb-4">
          <MapPin className="h-5 w-5 text-primary-600" />
          <span className="text-lg font-semibold">{bus.origin} &rarr; {bus.destination}</span>
        </div>

        <div className="grid grid-cols-2 gap-4 text-sm text-gray-600 mb-6">
          <div className="flex items-center gap-2">
            <Clock className="h-4 w-4" />
            <span>{formatTime(bus.departureTime)}</span>
          </div>
          <div className="flex items-center gap-2">
            <Users className="h-4 w-4" />
            <span>{bus.availableSeats} seats available</span>
          </div>
          <div>
            <span className="text-gray-400">Bus #</span> {bus.busNumber}
          </div>
          <div>
            <span className="text-gray-400">Operated by</span> {bus.companyName}
          </div>
        </div>

        <hr className="my-6" />

        {/* Seat selector */}
        <div className="flex items-center justify-between mb-6">
          <label className="font-medium text-gray-900">Number of Seats</label>
          <div className="flex items-center gap-3">
            <button
              onClick={() => setSeats(Math.max(1, seats - 1))}
              className="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50"
            >
              <Minus className="h-4 w-4" />
            </button>
            <span className="text-xl font-bold w-8 text-center">{seats}</span>
            <button
              onClick={() => setSeats(Math.min(bus.availableSeats, seats + 1))}
              className="w-10 h-10 rounded-lg border border-gray-300 flex items-center justify-center hover:bg-gray-50"
            >
              <Plus className="h-4 w-4" />
            </button>
          </div>
        </div>

        {/* Price breakdown */}
        <div className="bg-gray-50 rounded-lg p-4 mb-6">
          <div className="flex justify-between text-sm text-gray-600 mb-2">
            <span>${bus.pricePerSeat} x {seats} seat{seats !== 1 ? 's' : ''}</span>
            <span>${totalPrice}</span>
          </div>
          <hr className="my-2" />
          <div className="flex justify-between font-bold text-gray-900">
            <span>Total</span>
            <span className="text-xl text-primary-600">${totalPrice}</span>
          </div>
        </div>

        <button
          onClick={handleBook}
          disabled={booking}
          className="btn-primary w-full py-3 text-base flex items-center justify-center gap-2"
        >
          {booking ? (
            <><Loader2 className="h-5 w-5 animate-spin" /> Processing...</>
          ) : (
            <><CheckCircle className="h-5 w-5" /> Confirm Booking</>
          )}
        </button>
      </div>
    </div>
  );
}
