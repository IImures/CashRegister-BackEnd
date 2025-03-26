create table  if not exists product (
                        id bigserial not null,
                        name varchar(255) not null,
                        image_id bigint,
                        product_description_id bigint,
                        sub_catalog_id bigint not null,
                        primary key (id),
                        constraint image_fk foreign key (image_id) references product_image,
                        constraint product_description_fk foreign key (product_description_id) references product_description,
                        constraint sub_catalog_fk foreign key (sub_catalog_id) references sub_catalog
);


alter table if exists product
    add column producer_id bigint not null;

alter table if exists product
    add constraint producer_fk
        foreign key (producer_id)
            references producer