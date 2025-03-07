CREATE TABLE IF NOT EXISTS brands
(
    id          BIGINT auto_increment PRIMARY KEY,
    description VARCHAR (255) NOT NULL
);

CREATE TABLE IF NOT EXISTS prices
(
    id         INT auto_increment PRIMARY KEY,
    brand_id   BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date   TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    product_id BIGINT NOT NULL,
    priority   INT NOT NULL,
    price      DECIMAL (10, 2) NOT NULL,
    curr       VARCHAR (3) NOT NULL,
    CONSTRAINT fk_price_brand FOREIGN KEY ( brand_id ) REFERENCES brands ( id )
);