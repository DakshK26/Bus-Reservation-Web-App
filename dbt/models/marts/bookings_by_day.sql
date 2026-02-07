-- Mart: daily booking aggregations for trend analysis
with created_events as (
    select *
    from {{ ref('stg_booking_events') }}
    where event_type = 'BOOKING_CREATED'
)

select
    event_date as booking_date,
    count(*) as total_bookings,
    sum(total_price) as total_revenue,
    avg(seats_booked) as avg_seats_per_booking,
    count(distinct customer_id) as unique_customers
from created_events
group by event_date
order by event_date desc
