CREATE DATABASE IF NOT EXISTS test CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS test.bid_list
(
    bid_list_id    TINYINT(4)  NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    bid_quantity   DOUBLE,
    ask_quantity   DOUBLE,
    bid            DOUBLE,
    ask            DOUBLE,
    benchmark      VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary     VARCHAR(125),
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),
    PRIMARY KEY (bid_list_id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS test.curve_point
(
    id            TINYINT(4) NOT NULL AUTO_INCREMENT,
    curve_id      TINYINT,
    as_of_date    TIMESTAMP,
    term          DOUBLE,
    value         DOUBLE,
    creation_date TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS test.rating
(
    id            TINYINT(4) NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  TINYINT,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS test.rule_name
(
    id          TINYINT(4) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(125),
    sql_str     VARCHAR(125),
    sql_part    VARCHAR(125),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS test.trade
(
    trade_id       TINYINT(4)  NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    buy_quantity   DOUBLE,
    sell_quantity  DOUBLE,
    buy_price      DOUBLE,
    sell_price     DOUBLE,
    benchmark      VARCHAR(125),
    trade_date     TIMESTAMP,
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),
    PRIMARY KEY (trade_id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS test.user
(
    id       TINYINT(4) NOT NULL AUTO_INCREMENT,
    username VARCHAR(125),
    ## TODO : password à passer à 60 lors de la mise en place de BCrypt (idem pour test)
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

INSERT INTO test.user(username, password, fullname, role)
VALUES ("admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "Administrator", "ADMIN");
INSERT INTO test.user(username, password, fullname, role)
VALUES ("user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "User", "USER");