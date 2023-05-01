--liquibase formatted sql
--changeset TestUsers_sql:1
-- auto-generated definition
create table events
(
    id           bigserial
        primary key,
    adress       varchar(255),
    created_time timestamp,
    date         timestamp,
    description  varchar(255),
    latitude     double precision,
    longitude    double precision,
    rating       double precision,
    title        varchar(255),
    update_time  timestamp,
    city_id      bigint
        constraint fk90ap0a8qh96om4jeowoa9wniu
            references city,
    users_id     bigint
        constraint fkryrm7yeuaauejxgi3mnec4yul
            references users
);

alter table events
    owner to kushevsky01;
