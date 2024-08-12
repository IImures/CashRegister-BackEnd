create table product (
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

ALTER TABLE if exists product_description
    ADD COLUMN product_id bigint;

ALTER TABLE if exists product_description
    ADD CONSTRAINT product_fk
        FOREIGN KEY (product_id) REFERENCES product;