-- Mart: revenue and booking counts by origin-destination pair
with created_events as (
    select *
    from {{ ref('stg_booking_events') }}
    where event_type = 'BOOKING_CREATED'
)

select
    origin,
    destination,
    concat(origin, ' -> ', destination) as route_name,
    count(*) as total_bookings,
    sum(total_price) as total_revenue,
    avg(total_price) as avg_booking_price,
    sum(seats_booked) as total_seats_sold
from created_events
group by origin, destination
order by total_revenue desc
