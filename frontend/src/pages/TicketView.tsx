import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowRight, Bus, Calendar, Clock, Loader2, MapPin, Users } from 'lucide-react';
import { bookingApi } from '../services/api';
import type { BookingResponse } from '../types';

export default function TicketView() {
  const { id } = useParams<{ id: string }>();
  const [booking, setBooking] = useState<BookingResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [pollCount, setPollCount] = useState(0);

  useEffect(() => {
    loadBooking();
  }, [id]);

  // Poll for status changes (PENDING -> CONFIRMED)
  useEffect(() => {
    if (booking?.status === 'PENDING' && pollCount < 15) {
      const timer = setTimeout(() => {
        loadBooking();
        setPollCount(c => c + 1);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [booking?.status, pollCount]);

  const loadBooking = async () => {
    if (!id) return;
    try {
      const res = await bookingApi.getById(parseInt(id));
      setBooking(res.data);
    } catch {
      // handle error
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (iso: string) => {
    return new Date(iso).toLocaleString('en-US', {
      weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',
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

  if (!booking) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-16 text-center">
        <p className="text-gray-500 mb-4">Booking not found.</p>
        <Link to="/bookings" className="btn-primary">Back to Bookings</Link>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-6 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">Booking #{booking.id}</h1>
        <span className={`${statusBadge(booking.status)} text-sm px-3 py-1`}>
          {booking.status}
          {booking.status === 'PENDING' && (
            <Loader2 className="inline h-3 w-3 ml-1 animate-spin" />
          )}
        </span>
      </div>

      {/* Ticket Card */}
      <div className="card overflow-hidden">
        {/* Header strip */}
        <div className={`px-6 py-4 ${
          booking.status === 'CONFIRMED' ? 'bg-green-600' :
          booking.status === 'PENDING' ? 'bg-yellow-500' :
          'bg-red-600'
        } text-white`}>
          <div className="flex items-center gap-2">
            <Bus className="h-5 w-5" />
            <span className="font-semibold">{booking.companyName}</span>
          </div>
        </div>

        {/* Route info */}
        <div className="p-6">
          <div className="flex items-center justify-between mb-8">
            <div className="text-center">
              <p className="text-2xl font-bold text-gray-900">{booking.origin}</p>
              <p className="text-sm text-gray-500 mt-1 flex items-center gap-1 justify-center">
                <MapPin className="h-3.5 w-3.5" /> Origin
              </p>
            </div>
            <div className="flex-1 px-6">
              <div className="border-t-2 border-dashed border-gray-300 relative">
                <ArrowRight className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 h-5 w-5 text-gray-400 bg-white px-0.5" />
              </div>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-gray-900">{booking.destination}</p>
              <p className="text-sm text-gray-500 mt-1 flex items-center gap-1 justify-center">
                <MapPin className="h-3.5 w-3.5" /> Destination
              </p>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-6">
            <div>
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Departure</p>
              <p className="text-sm font-medium flex items-center gap-1">
                <Calendar className="h-4 w-4 text-gray-400" />
                {formatDate(booking.departureTime)}
              </p>
            </div>
            <div>
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Bus Number</p>
              <p className="text-sm font-medium">{booking.busNumber}</p>
            </div>
            <div>
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Seats</p>
              <p className="text-sm font-medium flex items-center gap-1">
                <Users className="h-4 w-4 text-gray-400" />
                {booking.seatsBooked}
              </p>
            </div>
            <div>
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Passenger</p>
              <p className="text-sm font-medium">{booking.customerName}</p>
            </div>
          </div>

          <hr className="my-6 border-dashed" />

          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Booked At</p>
              <p className="text-sm text-gray-600">{formatDate(booking.createdAt)}</p>
            </div>
            <div className="text-right">
              <p className="text-xs text-gray-400 uppercase tracking-wider mb-1">Total Price</p>
              <p className="text-3xl font-bold text-primary-600">${booking.totalPrice}</p>
            </div>
          </div>

          {booking.confirmedAt && (
            <div className="mt-4 text-sm text-green-600 flex items-center gap-1">
              <Clock className="h-4 w-4" />
              Confirmed at {formatDate(booking.confirmedAt)}
            </div>
          )}
        </div>
      </div>

      <div className="mt-6 flex gap-4">
        <Link to="/bookings" className="btn-secondary flex-1 justify-center">
          All Bookings
        </Link>
        <Link to="/" className="btn-primary flex-1 justify-center">
          Book Another Trip
        </Link>
      </div>
    </div>
  );
}
