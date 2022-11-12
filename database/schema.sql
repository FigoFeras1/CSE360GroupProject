CREATE SCHEMA IF NOT EXISTS orders;
CREATE TABLE IF NOT EXISTS public.users
(
    id          Long PRIMARY KEY UNIQUE,
    first_name  varchar(32) NOT NULL,
    last_name   varchar(32) NOT NULL,
    password    varchar(64) NOT NULL,
    role        varchar(16) NOT NULL DEFAULT 'customer',
    CONSTRAINT valid_account_type CHECK (lower(role) in ('customer',
                                                         'customer service',
                                                         'chef'
                                                        )),
    CONSTRAINT valid_id CHECK (id >= 1000000000 AND id <= 9999999999)
);

CREATE TABLE IF NOT EXISTS orders.accepted
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    cost        NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    customer_id Long,
    FOREIGN KEY (customer_id) references public.users(id)
);


CREATE TABLE IF NOT EXISTS orders.ready_to_cook
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    cost        NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    customer_id Long,
    FOREIGN KEY  (customer_id) references public.users(id)
);

CREATE TABLE IF NOT EXISTS orders.cooking
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    cost        NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    customer_id Long,
    FOREIGN KEY  (customer_id) references public.users(id)
);

CREATE TABLE IF NOT EXISTS orders.ready
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    cost        NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    customer_id Long,
    FOREIGN KEY (customer_id) references public.users(id)
);
