create table user_roles (
                            role_id bigint not null,
                            user_id bigint not null,
                            primary key (role_id, user_id),
                            constraint role_fk foreign key (role_id) references roles,
                            constraint user_fk foreign key (user_id) references user_tab
);

