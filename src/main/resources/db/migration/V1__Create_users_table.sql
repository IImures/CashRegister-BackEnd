create table user_tab ( -- name 'user' is preserved so -> user_tab
                          id bigserial not null,
                          email varchar(255) not null,
                          password varchar(255) not null,
                          is_enabled boolean not null,
                          primary key (id)
);

