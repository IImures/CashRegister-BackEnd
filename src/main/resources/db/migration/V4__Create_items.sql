create table items
(
    id           bigserial    not null,
    description  varchar(255) not null,
    type_id         bigserial    not null,
    general_info varchar(255) not null,
    name         varchar(255) not null,
    primary key (id),
    constraint type_fk foreign key (type_id) references item_types
)