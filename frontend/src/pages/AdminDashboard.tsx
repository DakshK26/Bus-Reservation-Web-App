import { useEffect, useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';
import { Loader2, TrendingUp, DollarSign, Clock, Users } from 'lucide-react';
import { analyticsApi } from '../services/api';

interface AnalyticsData {
  bookingsByDay: Record<string, unknown>[];
  revenueByRoute: Record<string, unknown>[];
  revenueByCompany: Record<string, unknown>[];
  latency: Record<string, unknown>;
}

const COLORS = ['#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899'];

export default function AdminDashboard() {
  const [data, setData] = useState<AnalyticsData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      analyticsApi.bookingsByDay(),
      analyticsApi.revenueByRoute(),
      analyticsApi.revenueByCompany(),
      analyticsApi.confirmationLatency(),
    ])
      .then(([byDay, byRoute, byCompany, latency]) => {
        setData({
          bookingsByDay: byDay.data,
          revenueByRoute: byRoute.data,
          revenueByCompany: byCompany.data,
          latency: latency.data,
        });
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center py-32">
        <Loader2 className="h-8 w-8 text-primary-600 animate-spin" />
      </div>
    );
  }

  if (!data) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-16 text-center">
        <p className="text-gray-500">Unable to load analytics data.</p>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900">Analytics Dashboard</h1>
        <p className="text-gray-500 mt-1">OLAP data powered by BigQuery + dbt</p>
      </div>

      {/* KPI Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
        <div className="card p-5">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 bg-primary-100 rounded-lg flex items-center justify-center">
              <TrendingUp className="h-5 w-5 text-primary-600" />
            </div>
            <div>
              <p className="text-sm text-gray-500">Total Bookings</p>
              <p className="text-2xl font-bold">
                {data.bookingsByDay.reduce((sum: number, d: Record<string, unknown>) => sum + (d.total_bookings as number || 0), 0)}
              </p>
            </div>
          </div>
        </div>
        <div className="card p-5">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <DollarSign className="h-5 w-5 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-gray-500">Total Revenue</p>
              <p className="text-2xl font-bold">
                ${data.revenueByCompany.reduce((sum: number, d: Record<string, unknown>) => sum + (d.total_revenue as number || 0), 0).toFixed(2)}
              </p>
            </div>
          </div>
        </div>
        <div className="card p-5">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
              <Users className="h-5 w-5 text-purple-600" />
            </div>
            <div>
              <p className="text-sm text-gray-500">Companies</p>
              <p className="text-2xl font-bold">{data.revenueByCompany.length}</p>
            </div>
          </div>
        </div>
        <div className="card p-5">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 bg-yellow-100 rounded-lg flex items-center justify-center">
              <Clock className="h-5 w-5 text-yellow-600" />
            </div>
            <div>
              <p className="text-sm text-gray-500">p95 Latency</p>
              <p className="text-2xl font-bold">{String(data.latency.p95_ms || 0)}ms</p>
            </div>
          </div>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Bookings by Day */}
        <div className="card p-6">
          <h3 className="font-semibold text-gray-900 mb-4">Bookings by Day</h3>
          {data.bookingsByDay.length > 0 ? (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={data.bookingsByDay}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="booking_date" tick={{ fontSize: 12 }} />
                <YAxis />
                <Tooltip />
                <Bar dataKey="total_bookings" fill="#3b82f6" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          ) : (
            <p className="text-gray-400 text-center py-16">No booking data yet</p>
          )}
        </div>

        {/* Revenue by Company */}
        <div className="card p-6">
          <h3 className="font-semibold text-gray-900 mb-4">Revenue by Company</h3>
          {data.revenueByCompany.length > 0 ? (
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={data.revenueByCompany}
                  dataKey="total_revenue"
                  nameKey="company"
                  cx="50%"
                  cy="50%"
                  outerRadius={100}
                  label={({ company }) => company as string}
                >
                  {data.revenueByCompany.map((_, index) => (
                    <Cell key={index} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          ) : (
            <p className="text-gray-400 text-center py-16">No revenue data yet</p>
          )}
        </div>

        {/* Revenue by Route */}
        <div className="card p-6 lg:col-span-2">
          <h3 className="font-semibold text-gray-900 mb-4">Top Routes by Revenue</h3>
          {data.revenueByRoute.length > 0 ? (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={data.revenueByRoute.slice(0, 10)} layout="vertical">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis type="number" />
                <YAxis dataKey="route" type="category" width={200} tick={{ fontSize: 11 }} />
                <Tooltip />
                <Bar dataKey="total_revenue" fill="#10b981" radius={[0, 4, 4, 0]} />
              </BarChart>
            </ResponsiveContainer>
          ) : (
            <p className="text-gray-400 text-center py-16">No route data yet</p>
          )}
        </div>
      </div>

      {/* Confirmation Latency */}
      <div className="card p-6 mt-6">
        <h3 className="font-semibold text-gray-900 mb-4">Booking Confirmation Latency</h3>
        <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
          {[
            { label: 'p50', value: data.latency.p50_ms, unit: 'ms' },
            { label: 'p95', value: data.latency.p95_ms, unit: 'ms' },
            { label: 'p99', value: data.latency.p99_ms, unit: 'ms' },
            { label: 'Average', value: typeof data.latency.avg_ms === 'number' ? data.latency.avg_ms.toFixed(0) : data.latency.avg_ms, unit: 'ms' },
            { label: 'Sample Size', value: data.latency.sample_size, unit: '' },
          ].map(({ label, value, unit }) => (
            <div key={label} className="bg-gray-50 rounded-lg p-4 text-center">
              <p className="text-sm text-gray-500 mb-1">{label}</p>
              <p className="text-2xl font-bold text-gray-900">{String(value || 0)}{unit}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
