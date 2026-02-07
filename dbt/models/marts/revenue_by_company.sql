-- Mart: revenue and booking counts by bus company
with created_events as (
    select *
    from {{ ref('stg_booking_events') }}
    where event_type = 'BOOKING_CREATED'
)

select
    company_id,
    count(*) as total_bookings,
    sum(total_price) as total_revenue,
    avg(total_price) as avg_booking_price,
    sum(seats_booked) as total_seats_sold,
    count(distinct customer_id) as unique_customers
from created_events
group by company_id
order by total_revenue desc
