create table characteristic (
                                id bigserial not null,
                                key varchar(255) not null,
                                value varchar(255) not null,
                                item_id bigint,
                                primary key (id),
                                constraint item_fk foreign key (item_id) references items
)