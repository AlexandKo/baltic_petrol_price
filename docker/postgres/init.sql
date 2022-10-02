\connect 'petrol_price';

GRANT ALL PRIVILEGES ON DATABASE petrol_price TO postgres;

CREATE SCHEMA IF NOT EXISTS petrol_station;

CREATE TABLE IF NOT EXISTS petrol_station.neste
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

CREATE TABLE IF NOT EXISTS petrol_station.gotika
(
    id                        UUID      NOT NULL PRIMARY KEY,
    created_date              TIMESTAMP NOT NULL,
    updated_date              TIMESTAMP NOT NULL,
    entity_version            BIGINT    NOT NULL,
    country                   VARCHAR(2),
    petrol                    VARCHAR(10),
    petrol_best_price_address TEXT,
    diesel                    VARCHAR(10),
    diesel_best_price_address TEXT
);

CREATE TABLE IF NOT EXISTS petrol_station.virsi
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
    gas                           VARCHAR(10),
    gas_best_price_address        TEXT
);

CREATE TABLE IF NOT EXISTS petrol_station.circle
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
    diesel_pro_best_price_address TEXT,
    gas                           VARCHAR(10),
    gas_best_price_address        TEXT
);

CREATE TABLE IF NOT EXISTS petrol_station.viada
(
    id                                  UUID      NOT NULL PRIMARY KEY,
    created_date                        TIMESTAMP NOT NULL,
    updated_date                        TIMESTAMP NOT NULL,
    entity_version                      BIGINT    NOT NULL,
    country                             VARCHAR(2),
    petrol_ecto                         VARCHAR(10),
    petrol_ecto_best_price_address      TEXT,
    petrol_ecto_plus                    VARCHAR(10),
    petrol_ecto_plus_best_price_address TEXT,
    petrol_pro                          VARCHAR(10),
    petrol_pro_best_price_address       TEXT,
    diesel                              VARCHAR(10),
    diesel_best_price_address           TEXT,
    diesel_ecto                         VARCHAR(10),
    diesel_ecto_best_price_address      TEXT,
    gas                                 VARCHAR(10),
    gas_best_price_address              TEXT,
    petrol_eco                          VARCHAR(10),
    petrol_eco_best_price_address       TEXT
);

CREATE UNIQUE INDEX circle_created_date_index
    ON petrol_station.circle (created_date DESC);

CREATE UNIQUE INDEX gotika_created_date_index
    ON petrol_station.gotika (created_date DESC);

CREATE UNIQUE INDEX neste_created_date_index
    ON petrol_station.neste (created_date DESC);

CREATE UNIQUE INDEX viada_created_date_index
    ON petrol_station.viada (created_date DESC);

CREATE UNIQUE INDEX virshi_created_date_index
    ON petrol_station.virsi (created_date DESC);

