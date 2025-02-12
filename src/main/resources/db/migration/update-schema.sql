CREATE SEQUENCE IF NOT EXISTS recommendations_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS rule_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE recommendations
(
    id           BIGINT NOT NULL,
    product_id   UUID,
    product_name VARCHAR(255),
    product_text VARCHAR(255),
    CONSTRAINT pk_recommendations PRIMARY KEY (id)
);

CREATE TABLE rule
(
    id        BIGINT  NOT NULL,
    query     VARCHAR(255),
    arguments TEXT[],
    negate    BOOLEAN NOT NULL,
    CONSTRAINT pk_rule PRIMARY KEY (id)
);

ALTER TABLE rule
    ADD CONSTRAINT FK_RULE_ON_ID FOREIGN KEY (id) REFERENCES recommendations (id);