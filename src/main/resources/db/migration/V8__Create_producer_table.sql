create table  if not exists producer (
        id bigserial not null,
        edrpou varchar(255) not null,
        producer_name varchar(255) not null,
        primary key (id)
    );

INSERT INTO producer (producer_name, edrpou) VALUES ('"Датекс"ООД', '39121386');
INSERT INTO producer (producer_name, edrpou) VALUES ('ДП"Компанія"ATLAS"', '22911050');
INSERT INTO producer (producer_name, edrpou) VALUES ('ПП "ХЕЛП МІКРО"', '31565308');
