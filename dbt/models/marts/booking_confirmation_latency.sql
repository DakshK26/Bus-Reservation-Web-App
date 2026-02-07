-- Mart: booking confirmation latency (time between CREATED and CONFIRMED events)
with created as (
    select
        booking_id,
        event_timestamp as created_at
    from {{ ref('stg_booking_events') }}
    where event_type = 'BOOKING_CREATED'
),

confirmed as (
    select
        booking_id,
        event_timestamp as confirmed_at
    from {{ ref('stg_booking_events') }}
    where event_type = 'BOOKING_CONFIRMED'
),

latencies as (
    select
        c.booking_id,
        c.created_at,
        cf.confirmed_at,
        timestamp_diff(cf.confirmed_at, c.created_at, MILLISECOND) as latency_ms
    from created c
    inner join confirmed cf on c.booking_id = cf.booking_id
)

select
    count(*) as sample_size,
    avg(latency_ms) as avg_latency_ms,
    approx_quantiles(latency_ms, 100)[offset(50)] as p50_ms,
    approx_quantiles(latency_ms, 100)[offset(95)] as p95_ms,
    approx_quantiles(latency_ms, 100)[offset(99)] as p99_ms,
    min(latency_ms) as min_ms,
    max(latency_ms) as max_ms
from latencies
