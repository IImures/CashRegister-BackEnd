create table  if not exists user_tab  ( -- name 'user' is preserved so -> user_tab
                          id bigserial not null,
                          email varchar(255) not null,
                          password varchar(255) not null,
                          is_enabled boolean not null,
                          primary key (id)
);

insert into user_tab(email, password, is_enabled) values ('vladyslav.kalynchenko@gmail.com', '$2a$10$FgbuYaB44gyVkSq2i/1Z2OT3T1gJQLO3kkDF.ZOakUX6sjxCHRARG', true);
insert into user_tab(email, password, is_enabled) VALUES ('igor.kalinchenko@gmail.com', '$2a$10$7o.3v.YN4kRxwgE2uauuOe8q5hSH39TMzpmY2dbp741u90tYGiZHS', true);
