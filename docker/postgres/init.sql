\connect 'petrol_station';

GRANT ALL PRIVILEGES ON DATABASE petrol_station TO postgres;

CREATE SCHEMA IF NOT EXISTS neste;

CREATE TABLE IF NOT EXISTS neste.petrol_price
(
    id                            UUID        NOT NULL PRIMARY KEY,
    created_date                  TIMESTAMP   NOT NULL,
    updated_date                  TIMESTAMP   NOT NULL,
    entity_version                BIGINT      NOT NULL,
    petrol                        VARCHAR(10) NOT NULL,
    petrol_best_price_address     TEXT        NOT NULL,
    petrol_pro                    VARCHAR(10) NOT NULL,
    petrol_pro_best_price_address TEXT        NOT NULL,
    diesel                        VARCHAR(10) NOT NULL,
    diesel_best_price_address     TEXT        NOT NULL,
    diesel_pro                    VARCHAR(10) NOT NULL,
    diesel_pro_best_price_address TEXT        NOT NULL
);
