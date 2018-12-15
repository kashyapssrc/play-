CREATE TABLE f_address (
    PRIMARY KEY (`id`),
    `id`            BIGINT(20)  AUTO_INCREMENT,
    `street`        VARCHAR(100)    NOT NULL,
    `city`          VARCHAR(15)     NOT NULL,
    `postal_code`   INT(11)         NOT NULL
);

CREATE TABLE f_person (
    PRIMARY KEY (`id`),
    `id`            BIGINT(20)   AUTO_INCREMENT,
    `firstname`     VARCHAR(50)           NOT NULL,
    `lastname`      VARCHAR(50)           NOT NULL,
    `email`         VARCHAR(100) UNIQUE   NOT NULL,
    `address_id`    BIGINT(20),
                    KEY k_person_f_address_id (address_id),
                    CONSTRAINT fk_f_person_f_address
                    FOREIGN KEY (address_id) REFERENCES f_address(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    `birth_date`    DATE    NOT NULL,
    `created_date`  DATETIME,
    `password`      VARCHAR(100)          NOT NULL,
    `isAdmin`       BOOLEAN  
); 
                           