CREATE TABLE address (
    PRIMARY KEY (`id`),
    `id`            BIGINT(20)  AUTO_INCREMENT,
    `street`        VARCHAR(100)    NOT NULL,
    `city`          VARCHAR(15)     NOT NULL,
    `postal_code`   INT(11)         NOT NULL
);

CREATE TABLE person (
    PRIMARY KEY (`id`),
    `id`            BIGINT(20)   AUTO_INCREMENT,
    `name`          VARCHAR(50)           NOT NULL,
    `email`         VARCHAR(100) UNIQUE   NOT NULL,
    `address_id`    BIGINT(20),
                    KEY k_person_address_id (address_id),
                    CONSTRAINT fk_person_address
                    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    `birth_date`    DATE    NOT NULL,
    `created_date`  DATETIME    
); 
                           