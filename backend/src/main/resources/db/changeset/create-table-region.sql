--liquibase formatted sql
--changeset TestUsers_sql:1

-- auto-generated definition
create table region
(
    id           bigserial
        primary key,
    created_time timestamp,
    name         varchar(128),
    country_id   bigint
        constraint fk7vb2cqcnkr9391hfn72louxkq
            references country
);

alter table region
    owner to kushevsky01;