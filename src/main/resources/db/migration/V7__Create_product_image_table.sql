create table product_image (
                               id bigserial not null,
                               image_data oid not null,
                               name varchar(255) not null,
                               type varchar(255) not null,
                               primary key (id)
);

