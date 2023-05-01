--liquibase formatted sql
--changeset TestUsers_sql:1

-- auto-generated definition
create table users
(
    id       bigserial
        primary key,
    active   boolean,
    email    varchar(50),
    name     varchar(128),
    password varchar(255),
    role     varchar(50),
    username varchar(128)
);

alter table users
    owner to kushevsky01;