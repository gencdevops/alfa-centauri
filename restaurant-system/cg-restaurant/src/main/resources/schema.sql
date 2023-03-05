 CREATE TABLE SUPPLIERS
 (
     SUPPLIER_ID   INT,
     SUPPLIER_NAME CHAR(200) NOT NULL UNIQUE,
     CREATE_DATE   DATE DEFAULT CURRENT_DATE,
     UPDATE_DATE   DATE DEFAULT CURRENT_DATE,
     PRIMARY KEY (SUPPLIER_ID)
 );

-- CREATE TABLE BRANCHES
-- (
--     BRANCH_ID   INT,
--     BRANCH_NAME CHAR(200) NOT NULL UNIQUE,
--     SUPPLIER_ID INT       NOT NULL,
--     CREATE_DATE DATE DEFAULT CURRENT_DATE,
--     UPDATE_DATE DATE DEFAULT CURRENT_DATE,
--     PRIMARY KEY (BRANCH_ID),
--     CONSTRAINT FK_BRANCHES_SUPPLIER_ID
--         FOREIGN KEY (SUPPLIER_ID)
--             REFERENCES SUPPLIERS (SUPPLIER_ID)
-- );
--
-- CREATE TABLE PRODUCTS
-- (
--     PRODUCT_ID    INT,
--     PRODUCT_NAME  CHAR(100) NOT NULL UNIQUE,
--     DEFAULT_PRICE NUMERIC(18, 2) DEFAULT 0,
--     SUPPLIER_ID   INT       NOT NULL,
--     STATUS        CHAR(20)       DEFAULT 'ACTIVE',
--     CREATE_DATE   DATE           DEFAULT CURRENT_DATE,
--     UPDATE_DATE   DATE           DEFAULT CURRENT_DATE,
--     PRIMARY KEY (PRODUCT_ID),
--     CONSTRAINT FK_PRODUCTS_SUPPLIER_ID
--         FOREIGN KEY (SUPPLIER_ID)
--             REFERENCES SUPPLIERS (SUPPLIER_ID)
-- );
--
-- CREATE TABLE PRODUCT_PRICE
-- (
--     PRODUCT_ID    INT,
--     BRANCH_ID     INT,
--     PRODUCT_PRICE numeric(18, 2),
--     PRIMARY KEY (PRODUCT_ID, BRANCH_ID),
--     CONSTRAINT FK_PRODUCT_PRICE_PRODUCT_ID
--         FOREIGN KEY (PRODUCT_ID)
--             REFERENCES PRODUCTS (PRODUCT_ID),
--     CONSTRAINT FK_PRODUCT_PRICE_BRANCH_ID
--         FOREIGN KEY (BRANCH_ID)
--             REFERENCES BRANCHES (BRANCH_ID)
-- );
--
-- CREATE VIEW VIEW_PRODUCT_PRICE AS
-- SELECT PRODUCTS.PRODUCT_ID,
--        PRODUCT_NAME,
--        COALESCE(PRODUCT_PRICE, DEFAULT_PRICE),
--        SUPPLIER_ID,
--        STATUS,
--        CREATE_DATE,
--        UPDATE_DATE
-- FROM PRODUCTS
--          LEFT JOIN PRODUCT_PRICE
--                    ON PRODUCTS.PRODUCT_ID = PRODUCT_PRICE.PRODUCT_ID;
--
-- CREATE SEQUENCE SEQ_SUPPLIERS_ID MINVALUE 1 INCREMENT BY 1 START WITH 1000 CACHE 100;
-- CREATE SEQUENCE SEQ_BRANCH_ID MINVALUE 1 INCREMENT BY 1 START WITH 1000 CACHE 100;
-- CREATE SEQUENCE SEQ_PRODUCT_ID MINVALUE 1 INCREMENT BY 1 START WITH 1000 CACHE 100;
--
-- CREATE INDEX IDX_BRANCHES_SUPPLIER
--     ON BRANCHES (SUPPLIER_ID);
-- CREATE INDEX IDX_PRODUCTS_SUPPLIER_STATUS
--     ON PRODUCTS (SUPPLIER_ID, STATUS);

