create table user_tab (
                          id bigserial not null,
                          email varchar(255) not null,
                          first_name varchar(255) not null,
                          last_name varchar(255) not null,
                          login varchar(255) not null,
                          password varchar(255) not null,
                          is_enabled boolean not null,
                          primary key (id)
)