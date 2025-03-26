create table  if not exists roles
(
    id        bigserial,
    authority varchar(255) not null,
    primary key (id)
);

INSERT INTO roles(id, authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO roles(id, authority) VALUES (2,'ROLE_USER');