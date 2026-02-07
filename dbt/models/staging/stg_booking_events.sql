-- Staging model: deduplicates raw events and casts types
with raw as (
    select
        event_id,
        event_type,
        schema_version,
        booking_id,
        customer_id,
        bus_id,
        route_id,
        company_id,
        origin,
        destination,
        seats_booked,
        total_price,
        status,
        event_timestamp,
        ingested_at,
        row_number() over (partition by event_id order by ingested_at desc) as rn
    from {{ source('raw', 'raw_booking_events') }}
)

select
    event_id,
    event_type,
    schema_version,
    cast(booking_id as int64) as booking_id,
    cast(customer_id as int64) as customer_id,
    cast(bus_id as int64) as bus_id,
    cast(route_id as int64) as route_id,
    cast(company_id as int64) as company_id,
    origin,
    destination,
    cast(seats_booked as int64) as seats_booked,
    cast(total_price as numeric) as total_price,
    status,
    event_timestamp,
    date(event_timestamp) as event_date,
    extract(hour from event_timestamp) as event_hour
from raw
where rn = 1
