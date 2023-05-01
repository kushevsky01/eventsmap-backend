--liquibase formatted sql
--changeset TestUsers_sql:1
-- auto-generated definition
create table country
(
    id           bigserial
        primary key,
    created_time timestamp,
    name         varchar(128)
);

alter table country
    owner to kushevsky01;