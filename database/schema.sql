CREATE SCHEMA IF NOT EXISTS orders;
CREATE TABLE IF NOT EXISTS public.users
(
    id          int PRIMARY KEY UNIQUE,
    first_name  varchar(32) NOT NULL,
    last_name   varchar(32) NOT NULL,
    password    varchar(64) NOT NULL,
    role        varchar(16) NOT NULL DEFAULT 'customer',
    CONSTRAINT valid_account_type CHECK (lower(role) in ('customer',
                                                         'customer service',
                                                         'chef'
                                                        ))
);

CREATE TABLE IF NOT EXISTS orders.saved
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    customer_id int,
    FOREIGN KEY (customer_id) references public.users(id)
);

CREATE TABLE IF NOT EXISTS orders.pending
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    customer_id int,
    FOREIGN KEY (customer_id) references public.users(id)
);


CREATE TABLE IF NOT EXISTS orders.processed
(
    id          int PRIMARY KEY UNIQUE AUTO_INCREMENT,
    items       json NOT NULL,
    order_date  DATE NOT NULL,
    customer_id int,
    FOREIGN KEY  (customer_id) references public.users(id)
);
