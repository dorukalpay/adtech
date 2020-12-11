
create table if not exists swaarm.advertisement_record
(
    id               uuid         not null
        constraint advertisement_record_pkey
            primary key,
    advertisement_id bigint       not null,
    browser          varchar(255) not null,
    operating_system varchar(255) not null,
    site             varchar(255) not null
);

create table if not exists swaarm.advertisement_record_detail
(
    id                      uuid                        not null
        constraint advertisement_record_detail_pkey
            primary key,
    advertisement_record_id uuid                        not null
        constraint "fk__advertisement_record_detail__advertisement_record_id"
            references swaarm.advertisement_record,
    type                    varchar(31)                 not null,
    time                    timestamp without time zone not null,
    delivery_id             uuid,
    click_id                uuid,
    install_id              uuid
);

create unique index if not exists ux__advertisement_record_detail__delivery_id
    on swaarm.advertisement_record_detail (delivery_id)
    where click_id is null and install_id is null;

create unique index if not exists ux__advertisement_record_detail__delivery_id__click_id
    on swaarm.advertisement_record_detail (delivery_id, click_id)
    where install_id is null;

create unique index if not exists ux__advertisement_record_detail__click_id
    on swaarm.advertisement_record_detail (click_id)
    where install_id is null;

create unique index if not exists ux__advertisement_record_detail__install_id
    on swaarm.advertisement_record_detail (install_id);


create table if not exists swaarm.advertisement_data_count
(
    id                      uuid not null
        constraint advertisement_data_count_pkey
            primary key,
    advertisement_record_id uuid not null
        constraint "fk__advertisement_data_count__advertisement_record_id"
            references swaarm.advertisement_record,
    delivered               bigint,
    clicked                 bigint,
    installed               bigint
);
