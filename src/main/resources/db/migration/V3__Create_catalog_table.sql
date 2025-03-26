create table  if not exists catalog (
                         id bigserial not null,
                         catalog_name varchar(255) not null,
                         primary key (id)
);

INSERT INTO catalog (catalog_name) values  ('Фіскальне обладнання');
INSERT INTO catalog (catalog_name) values  ('Лазерні принтери');