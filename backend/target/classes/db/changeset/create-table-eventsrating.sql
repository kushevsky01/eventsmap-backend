--liquibase formatted sql
--changeset kushevsky01:1
-- auto-generated definition
create table eventsrating
(
    events_id bigint not null
        constraint fk77l84ho90bidueiskufhv2opa
            references events,
    user_id   bigint not null
        constraint fk4o3wh1glr7qnvrhh8fwa0nrlq
            references users,
    rating    bigint,
    primary key (events_id, user_id)
);

alter table eventsrating
    owner to kushevsky01;