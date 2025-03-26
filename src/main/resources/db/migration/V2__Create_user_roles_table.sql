create table  if not exists user_roles (
                            role_id bigint not null,
                            user_id bigint not null,
                            primary key (role_id, user_id),
                            constraint role_fk foreign key (role_id) references roles,
                            constraint user_fk foreign key (user_id) references user_tab
);

insert into user_roles (role_id, user_id) VALUES (1,1);
insert into user_roles (role_id, user_id) VALUES (1,2);
