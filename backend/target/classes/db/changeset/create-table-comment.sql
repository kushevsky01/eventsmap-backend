--liquibase formatted sql
--changeset TestUsers_sql:1

-- auto-generated definition
create table comment
(
    id           bigserial
        primary key,
    created_time timestamp,
    text         varchar(255),
    user_id      bigint,
    username     varchar(255),
    events_id    bigint
        constraint fk5ranpfs8jgb0qm74frx2exks
            references events
);

alter table comment
    owner to kushevsky01;