CREATE TABLE ORDERS
(
    ORDER_ID INT,
    TOTAL_PRICE NUMERIC(18,2) NOT NULL,
    STATUS CHAR(20) DEFAULT 'RECEIVED',
    CREATE_DATE DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (ORDER_ID)
);


CREATE TABLE ORDER_ITEMS
(
    ORDER_ITEM_ID INT,
    PRODUCT_ID INT NOT NULL,
    PRODUCT_NAME CHAR(200) NOT NULL,
    QUANTITY INT NOT NULL,
    UNIT_PRICE NUMERIC(18,2) NOT NULL,
    TOTAL_PRICE NUMERIC(18,2) NOT NULL,
    ORDER_ID INT NOT NULL,
    CREATE_DATE DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (ORDER_ITEM_ID),
        CONSTRAINT FK_ORDER_ITEMS_ORDER_ID
        FOREIGN KEY(ORDER_ID)
        REFERENCES ORDERS(ORDER_ID)
);

CREATE SEQUENCE SEQ_ORDERS_ID  MINVALUE 1 INCREMENT BY 1 START WITH 1000 CACHE 100 ;
CREATE SEQUENCE SEQ_ORDER_ITEMS_ID  MINVALUE 1 INCREMENT BY 1 START WITH 1000 CACHE 100 ;

CREATE INDEX IDX_ORDER_ITEMS_ORDER_ID
    ON ORDER_ITEMS(ORDER_ID);
