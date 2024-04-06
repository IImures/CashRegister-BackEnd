create table cash_registers (
                                id bigserial not null,
                                description varchar(255) not null,
                                general_info varchar(255) not null,
                                name varchar(255) not null,
                                primary key (id)
)