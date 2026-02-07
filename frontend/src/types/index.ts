export interface RouteResponse {
  id: number;
  origin: string;
  destination: string;
  distanceKm: number;
  durationMinutes: number;
  basePrice: number;
  companyName: string;
  companyId: number;
}

export interface BusResponse {
  id: number;
  busNumber: string;
  capacity: number;
  availableSeats: number;
  departureTime: string;
  routeId: number;
  origin: string;
  destination: string;
  pricePerSeat: number;
  companyName: string;
}

export interface BookingResponse {
  id: number;
  seatsBooked: number;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED';
  totalPrice: number;
  createdAt: string;
  confirmedAt: string | null;
  busId: number;
  busNumber: string;
  departureTime: string;
  origin: string;
  destination: string;
  companyName: string;
  customerName: string;
}

export interface AuthResponse {
  token: string;
  role: string;
  userId: number | null;
  displayName: string;
}

export interface User {
  token: string;
  role: string;
  userId: number | null;
  displayName: string;
}
