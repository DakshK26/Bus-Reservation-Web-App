import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, Loader2, Ticket, XCircle } from 'lucide-react';
import { bookingApi } from '../services/api';
import { toast } from 'sonner';
import type { BookingResponse } from '../types';

export default function MyBookings() {
  const [bookings, setBookings] = useState<BookingResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      const res = await bookingApi.getAll();
      setBookings(res.data);
    } catch {
      toast.error('Failed to load bookings');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (id: number) => {
    try {
      await bookingApi.cancel(id);
      toast.success('Booking cancelled');
      loadBookings();
    } catch {
      toast.error('Failed to cancel booking');
    }
  };

  const formatTime = (iso: string) => {
    return new Date(iso).toLocaleString('en-US', {
      weekday: 'short', month: 'short', day: 'numeric',
      hour: 'numeric', minute: '2-digit',
    });
  };

  const statusBadge = (status: string) => {
    switch (status) {
      case 'PENDING': return 'badge-pending';
      case 'CONFIRMED': return 'badge-confirmed';
      case 'CANCELLED': return 'badge-cancelled';
      default: return 'badge';
    }
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
      <h1 className="text-2xl font-bold text-gray-900 mb-6">My Bookings</h1>

      {bookings.length === 0 ? (
        <div className="card p-12 text-center">
          <Ticket className="h-12 w-12 text-gray-300 mx-auto mb-4" />
          <p className="text-gray-500 text-lg mb-4">No bookings yet.</p>
          <Link to="/" className="btn-primary">Search for Buses</Link>
        </div>
      ) : (
        <div className="space-y-4">
          {bookings.map(booking => (
            <div key={booking.id} className="card p-5">
              <div className="flex items-center justify-between">
                <div className="flex-1">
                  <div className="flex items-center gap-3 mb-2">
                    <span className="font-semibold text-gray-900">{booking.origin}</span>
                    <ArrowRight className="h-4 w-4 text-gray-400" />
                    <span className="font-semibold text-gray-900">{booking.destination}</span>
                    <span className={statusBadge(booking.status)}>{booking.status}</span>
                  </div>
                  <div className="flex items-center gap-4 text-sm text-gray-500">
                    <span>{formatTime(booking.departureTime)}</span>
                    <span>Bus #{booking.busNumber}</span>
                    <span>{booking.seatsBooked} seat{booking.seatsBooked !== 1 ? 's' : ''}</span>
                    <span>by {booking.companyName}</span>
                  </div>
                </div>
                <div className="flex items-center gap-4">
                  <div className="text-right">
                    <p className="text-lg font-bold text-gray-900">${booking.totalPrice}</p>
                    <p className="text-xs text-gray-400">Booking #{booking.id}</p>
                  </div>
                  <div className="flex gap-2">
                    <Link to={`/bookings/${booking.id}`} className="btn-secondary py-2 px-3 text-sm">
                      View
                    </Link>
                    {booking.status !== 'CANCELLED' && (
                      <button
                        onClick={() => handleCancel(booking.id)}
                        className="btn-danger py-2 px-3 text-sm flex items-center gap-1"
                      >
                        <XCircle className="h-3.5 w-3.5" />
                        Cancel
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
