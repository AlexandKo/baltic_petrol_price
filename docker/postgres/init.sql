\connect 'petrol_station';

GRANT ALL PRIVILEGES ON DATABASE petrol_station TO postgres;

CREATE SCHEMA IF NOT EXISTS neste;

CREATE TABLE IF NOT EXISTS neste.petrol_price
(
    id                            UUID      NOT NULL PRIMARY KEY,
    created_date                  TIMESTAMP NOT NULL,
    updated_date                  TIMESTAMP NOT NULL,
    entity_version                BIGINT    NOT NULL,
    country                       VARCHAR(2),
    petrol                        VARCHAR(10),
    petrol_best_price_address     TEXT,
    petrol_pro                    VARCHAR(10),
    petrol_pro_best_price_address TEXT,
    diesel                        VARCHAR(10),
    diesel_best_price_address     TEXT,
    diesel_pro                    VARCHAR(10),
    diesel_pro_best_price_address TEXT
);
