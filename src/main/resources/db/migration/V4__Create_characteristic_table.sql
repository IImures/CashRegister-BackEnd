create table characteristic (
                                id bigserial not null,
                                key varchar(255) not null,
                                value varchar(255) not null,
                                cash_register_id bigint,
                                primary key (id),
                                constraint cash_register_fk foreign key (cash_register_id) references cash_registers
)