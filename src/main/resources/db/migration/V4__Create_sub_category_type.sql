create table sub_catalog_type (
                                  id bigserial not null,
                                  type varchar(255) not null,
                                  primary key (id)
);

INSERT INTO sub_catalog_type (type) VALUES ('list');
INSERT INTO sub_catalog_type (type) VALUES ('page');