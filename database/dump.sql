-- H2 2.1.214;
-- noinspection ALL
;              
CREATE USER IF NOT EXISTS "ADMIN" SALT 'd47276c9f03c5f49' HASH '6f451e0d2b4107a6198ab383afabcddd18e13be9927782aeabdbbfa3ecd7dbef' ADMIN;       
CREATE SCHEMA IF NOT EXISTS "ORDERS" AUTHORIZATION "ADMIN";    
CREATE CACHED TABLE "PUBLIC"."USERS"(
    "ID" BIGINT NOT NULL,
    "FIRST_NAME" CHARACTER VARYING(32) NOT NULL,
    "LAST_NAME" CHARACTER VARYING(32) NOT NULL,
    "PASSWORD" CHARACTER VARYING(64) NOT NULL,
    "ROLE" CHARACTER VARYING(16) DEFAULT 'customer' NOT NULL
);
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");         
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.USERS;    
INSERT INTO "PUBLIC"."USERS" VALUES
(1000000000, 'feras', 'a', 'password', 'customer'),
(9999999999, 'admin', 'admin', 'password', 'customer');
CREATE CACHED TABLE "ORDERS"."ACCEPTED"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ITEMS" JSON NOT NULL,
    "ORDER_DATE" DATE NOT NULL,
    "COST" NUMERIC(5, 2) DEFAULT 0.00 NOT NULL,
    "CUSTOMER_ID" BIGINT
);      
ALTER TABLE "ORDERS"."ACCEPTED" ADD CONSTRAINT "ORDERS"."CONSTRAINT_A" PRIMARY KEY("ID");      
-- 0 +/- SELECT COUNT(*) FROM ORDERS.ACCEPTED; 
CREATE CACHED TABLE "ORDERS"."READY_TO_COOK"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ITEMS" JSON NOT NULL,
    "ORDER_DATE" DATE NOT NULL,
    "COST" NUMERIC(5, 2) DEFAULT 0.00 NOT NULL,
    "CUSTOMER_ID" BIGINT
); 
ALTER TABLE "ORDERS"."READY_TO_COOK" ADD CONSTRAINT "ORDERS"."CONSTRAINT_2" PRIMARY KEY("ID"); 
-- 0 +/- SELECT COUNT(*) FROM ORDERS.READY_TO_COOK;            
CREATE CACHED TABLE "ORDERS"."COOKING"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ITEMS" JSON NOT NULL,
    "ORDER_DATE" DATE NOT NULL,
    "COST" NUMERIC(5, 2) DEFAULT 0.00 NOT NULL,
    "CUSTOMER_ID" BIGINT
);       
ALTER TABLE "ORDERS"."COOKING" ADD CONSTRAINT "ORDERS"."CONSTRAINT_6" PRIMARY KEY("ID");       
-- 0 +/- SELECT COUNT(*) FROM ORDERS.COOKING;  
CREATE CACHED TABLE "ORDERS"."READY"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ITEMS" JSON NOT NULL,
    "ORDER_DATE" DATE NOT NULL,
    "COST" NUMERIC(5, 2) DEFAULT 0.00 NOT NULL,
    "CUSTOMER_ID" BIGINT
);         
ALTER TABLE "ORDERS"."READY" ADD CONSTRAINT "ORDERS"."CONSTRAINT_4" PRIMARY KEY("ID");         
-- 0 +/- SELECT COUNT(*) FROM ORDERS.READY;    
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."VALID_ID" CHECK(("ID" >= CAST(1000000000 AS BIGINT))
    AND ("ID" <= 9999999999)) NOCHECK;              
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."VALID_ACCOUNT_TYPE" CHECK(LOWER("ROLE") IN('customer', 'customer service', 'chef')) NOCHECK;             
ALTER TABLE "ORDERS"."READY" ADD CONSTRAINT "ORDERS"."CONSTRAINT_4A" UNIQUE("ID");             
ALTER TABLE "ORDERS"."ACCEPTED" ADD CONSTRAINT "ORDERS"."CONSTRAINT_AE" UNIQUE("ID");          
ALTER TABLE "ORDERS"."COOKING" ADD CONSTRAINT "ORDERS"."CONSTRAINT_63" UNIQUE("ID");           
ALTER TABLE "ORDERS"."READY_TO_COOK" ADD CONSTRAINT "ORDERS"."CONSTRAINT_2A" UNIQUE("ID");     
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4D" UNIQUE("ID");             
ALTER TABLE "ORDERS"."ACCEPTED" ADD CONSTRAINT "ORDERS"."CONSTRAINT_AEB" FOREIGN KEY("CUSTOMER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK; 
ALTER TABLE "ORDERS"."COOKING" ADD CONSTRAINT "ORDERS"."CONSTRAINT_638" FOREIGN KEY("CUSTOMER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;  
ALTER TABLE "ORDERS"."READY_TO_COOK" ADD CONSTRAINT "ORDERS"."CONSTRAINT_2A7" FOREIGN KEY("CUSTOMER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;            
ALTER TABLE "ORDERS"."READY" ADD CONSTRAINT "ORDERS"."CONSTRAINT_4A3" FOREIGN KEY("CUSTOMER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;    
