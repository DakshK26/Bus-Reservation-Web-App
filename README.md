# Bus Reservation System

An event-driven bus reservation platform demonstrating distributed systems patterns: **Kafka event streaming** (transactional outbox), **OLTP/OLAP separation** (PostgreSQL + BigQuery), **Redis caching**, and a modern **React + Spring Boot** architecture.

## Architecture

```
React SPA ──REST/JWT──▸ Spring Boot REST API
                              │
                    ┌─────────┼──────────┐
                    ▼         ▼          ▼
               PostgreSQL   Redis    Kafka (Aiven)
               (OLTP)     (Cache)   (Event Streaming)
                                        │
                              ┌─────────┼──────────┐
                              ▼                    ▼
                     Confirmation          Analytics Sink
                       Worker              (BigQuery OLAP)
                              │                    │
                              ▼                    ▼
                          PostgreSQL          BigQuery + dbt
                        (status update)    (bookings_by_day,
                                           revenue_by_route,
                                           confirmation_latency)
```

### Key Patterns

- **Transactional Outbox** — Booking + event written in one DB transaction, then reliably published to Kafka
- **Event Sourcing** — Kafka topics (`booking-created`, `booking-confirmed`, `booking-cancelled`) are the durable log of all booking lifecycle events
- **Idempotent Consumers** — Events carry UUIDs; consumers deduplicate to handle redeliveries
- **OLTP/OLAP Split** — PostgreSQL for transactions, BigQuery + dbt for analytics
- **Optimistic Locking** — `@Version` on bus seats prevents overselling under concurrency
- **Cache-Aside** — Redis caches route searches and seat availability with TTL + eviction on writes

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React 18, TypeScript, Tailwind CSS, Vite, Zustand, Recharts |
| Backend | Java 17, Spring Boot 3.2, Spring Security (JWT), Spring Data JPA |
| Database | PostgreSQL (prod), H2 (dev) |
| Caching | Redis (Render managed) |
| Messaging | Apache Kafka (Aiven managed, mTLS) |
| Analytics | Google BigQuery + dbt |
| CI/CD | GitHub Actions |
| Deployment | Render (Web Service + Static Site + PostgreSQL + Redis) |

## Project Structure

```
├── backend/            Spring Boot REST API
│   ├── controller/     REST endpoints (auth, routes, bookings, analytics)
│   ├── service/        Business logic layer
│   ├── entity/         JPA entities with relationships
│   ├── repository/     Spring Data JPA repositories
│   ├── security/       JWT authentication filter
│   ├── kafka/          Outbox producer + consumer groups
│   ├── analytics/      BigQuery sink + analytics service
│   └── seed/           Demo data loader
├── frontend/           React + TypeScript SPA
│   ├── pages/          Landing, Search, Booking, Tickets, Admin Dashboard
│   ├── components/     Layout, shared UI
│   ├── services/       Axios API client
│   └── store/          Zustand auth store
├── dbt/                BigQuery transformation models
│   └── models/         bookings_by_day, revenue_by_route, confirmation_latency
└── .github/workflows/  CI + dbt scheduling
```

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+
- Maven 3.9+ (or use the wrapper `./mvnw`)

### Run Backend (dev mode with H2)

```bash
cd backend
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`. Swagger UI: `http://localhost:8080/swagger-ui.html`

### Run Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend starts at `http://localhost:5173` and proxies API calls to the backend.

### Demo Credentials

| Role | Username/Email | Password |
|------|---------------|----------|
| Customer | `alice` | `password123` |
| Customer | `bob` | `password123` |
| Company | `info@greyhound.com` | `password123` |

## API Endpoints

| Method | Path | Description | Auth |
|--------|------|-------------|------|
| POST | `/api/auth/register` | Register customer | Public |
| POST | `/api/auth/login` | Login customer | Public |
| GET | `/api/routes?origin=X&dest=Y` | Search routes | Public |
| GET | `/api/routes/{routeId}/buses` | Available buses | Public |
| POST | `/api/bookings` | Create booking | JWT |
| GET | `/api/bookings` | My bookings | JWT |
| DELETE | `/api/bookings/{id}` | Cancel booking | JWT |
| GET | `/api/admin/analytics/*` | Analytics data | JWT (Company) |

Full API documentation available at `/swagger-ui.html` when the backend is running.

## Event Flow

1. Customer creates booking via `POST /api/bookings`
2. Backend saves `Booking` (PENDING) + `BookingEvent` in same DB transaction
3. Outbox producer polls and publishes event to `booking-created` Kafka topic
4. **Confirmation worker** consumes event, simulates payment, updates to CONFIRMED
5. **Analytics sink** consumes all events, writes to BigQuery `raw_booking_events`
6. dbt transforms raw events into analytical tables (run on schedule via GitHub Actions)
7. Frontend polls booking status — user sees PENDING → CONFIRMED transition

## Deployment

Deployed to **Render** with:
- Backend: Web Service (Java)
- Frontend: Static Site (Vite build)
- PostgreSQL: Render managed database
- Redis: Render managed cache
- Kafka: Aiven managed (SSL/mTLS)
- BigQuery: GCP free tier

## License

MIT
