create table  if not exists sub_catalog (
     id bigserial not null,
     sub_catalog_name varchar(255) not null,
     catalog_fk bigint,
     sub_category_type_fk bigint not null,
     primary key (id),
     constraint fk_catalog foreign key (catalog_fk) references catalog,
     constraint fk_sub_category_type foreign key (sub_category_type_fk) references sub_catalog_type
);

INSERT INTO sub_catalog (sub_catalog_name, catalog_fk, sub_category_type_fk) VALUES ('Касові апарати', 1, 1);
INSERT INTO sub_catalog (sub_catalog_name, catalog_fk, sub_category_type_fk) VALUES ('Фіскальні реєстратори', 1, 1);

INSERT INTO sub_catalog (sub_catalog_name, catalog_fk, sub_category_type_fk) VALUES ('Ремонт лазерних принтерів', 2, 1);
INSERT INTO sub_catalog (sub_catalog_name, catalog_fk, sub_category_type_fk) VALUES ('Заправка картриджів', 2, 1);
