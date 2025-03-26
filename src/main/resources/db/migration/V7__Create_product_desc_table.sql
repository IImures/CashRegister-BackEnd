create table  if not exists product_description (
                        id bigserial not null,
                        description TEXT not null,
                        characteristics TEXT not null,
                        image_id bigint,
                        title TEXT not null,
                        primary key (id),
                        constraint image_fk foreign key (image_id) references product_image
);

