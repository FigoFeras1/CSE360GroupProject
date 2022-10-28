CREATE TABLE IF NOT EXISTS users
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    first_name  varchar(32) NOT NULL,
    last_name   varchar(32) NOT NULL,
    password    varchar(64) NOT NULL,
    role        varchar(16) NOT NULL DEFAULT 'customer',
    CONSTRAINT valid_account_type CHECK (lower(role) in ('customer',
                                                         'customer service',
                                                         'chef'
                                                        ))
);

CREATE TABLE IF NOT EXISTS saved_orders
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pending_orders
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS processed_orders
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL
);
