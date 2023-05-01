--liquibase formatted sql
--changeset TestUsers_sql:1
-- auto-generated definition
create table user_events
(
    users_id  bigint not null
        constraint fks64anmmmm7pj9gu9k6qus20hf
            references users,
    events_id bigint not null
        constraint fkgkmgmbr0xbn7p5fcplh4gbt3y
            references events
);

alter table user_events
    owner to kushevsky01;