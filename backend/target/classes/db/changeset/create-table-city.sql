--liquibase formatted sql
--changeset TestUsers_sql:1
-- auto-generated definition
create table city
(
    id           bigserial
        primary key,
    created_time timestamp,
    name         varchar(128),
    region_id    bigint
        constraint fksi0dkm9kk6dyuedmc0j18t770
            references region
);

alter table city
    owner to kushevsky01;