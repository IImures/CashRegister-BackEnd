create table sub_catalog (
                             id bigserial not null,
                             sub_catalog_name varchar(255) not null,
                             catalog_fk bigint,
                             sub_category_type_fk bigint not null,
                             primary key (id),
                             constraint fk_catalog foreign key (catalog_fk) references catalog,
                             constraint fk_sub_category_type foreign key (sub_category_type_fk) references sub_catalog_type
);
