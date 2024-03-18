DROP DATABASE if exists agroprojectdb;
CREATE DATABASE agroprojectdb;
use agroprojectdb;


-- Create the AF_PRODUCT table
CREATE TABLE AF_PRODUCT (
    PRODUCT_ID INT AUTO_INCREMENT,
    NAME VARCHAR(580) NOT NULL,
    DESCRIPTION VARCHAR(1000) NOT NULL,
    CATEGORY VARCHAR(200) NOT NULL,
    PRICE BIGINT NOT NULL,
    QUANTITY SMALLINT NOT NULL,
    CONSTRAINT AF_PRODUCT_ID_PK PRIMARY KEY (PRODUCT_ID)
);

-- Create the AF_CUSTOMER table
CREATE TABLE AF_CUSTOMER (
    EMAIL_ID VARCHAR(255),
    NAME VARCHAR(58) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL,
    PHONE_NUMBER VARCHAR(15) NOT NULL UNIQUE,
    ADDRESS VARCHAR(500) ,
    CONSTRAINT AF_CUSTOMER_EMAIL_ID_PK PRIMARY KEY (EMAIL_ID)
);

-- Create the AF_CUSTOMER_CART table
CREATE TABLE AF_CUSTOMER_CART (
    CART_ID INT AUTO_INCREMENT,
    CUSTOMER_EMAIL_ID VARCHAR(50),
    CONSTRAINT AF_CART_ID_PK PRIMARY KEY (CART_ID)
);

-- Create the AF_CART_PRODUCT table
CREATE TABLE AF_CART_PRODUCT (
    CART_PRODUCT_ID INT AUTO_INCREMENT,
    CART_ID INT,
    PRODUCT_ID INT NOT NULL,
    QUANTITY SMALLINT,
    CONSTRAINT AF_CART_PRODUCT_ID_PK PRIMARY KEY (CART_PRODUCT_ID),
    CONSTRAINT AF_CART_PRODUCT_CUSTOMER_CART_FK FOREIGN KEY (CART_ID) REFERENCES AF_CUSTOMER_CART (CART_ID)
);

-- Create the AF_ORDER table
CREATE TABLE AF_ORDER (
    ORDER_ID BIGINT NOT NULL AUTO_INCREMENT,
    DATE_OF_ORDER DATETIME NOT NULL,
    TOTAL_PRICE DECIMAL(12, 2) NOT NULL,
    ORDER_STATUS VARCHAR(20) NOT NULL,
    PAYMENT_THROUGH VARCHAR(20) NOT NULL,
    DATE_OF_DELIVERY DATETIME,
    CUSTOMER_EMAIL_ID VARCHAR(50),
    DISCOUNT DECIMAL(5, 2),
    DELIVERY_ADDRESS VARCHAR(500),
    CONSTRAINT AF_ORDER_PK PRIMARY KEY (ORDER_ID)
);

-- Create the AF_ORDERED_PRODUCT table
CREATE TABLE AF_ORDERED_PRODUCT (
    ORDERED_PRODUCT_ID INT AUTO_INCREMENT,
    ORDER_ID BIGINT,
    PRODUCT_ID INT,
    QUANTITY INT,
    CONSTRAINT AF_ORDERED_PRODUCT_ID_PK PRIMARY KEY (ORDERED_PRODUCT_ID),
    CONSTRAINT AF_ORDERED_PRODUCT_ORDER_FK FOREIGN KEY (ORDER_ID) REFERENCES AF_ORDER(ORDER_ID)
);

-- Create the AF_CARD table
CREATE TABLE AF_CARD (
    CARD_ID INT AUTO_INCREMENT,
    CARD_TYPE VARCHAR(11) NOT NULL,
    CARD_NUMBER VARCHAR(16) NOT NULL,
    CVV VARCHAR(70) NOT NULL,
    EXPIRY_DATE DATETIME NOT NULL,
    NAME_ON_CARD VARCHAR(50) NOT NULL,
    CUSTOMER_EMAIL_ID VARCHAR(58),
    CONSTRAINT AF_CARD_ID_PK PRIMARY KEY (CARD_ID)
);

-- Create the AF_TRANSACTION table
CREATE TABLE AF_TRANSACTION (
    TRANSACTION_ID INT AUTO_INCREMENT,
    ORDER_ID INT NOT NULL,
    CARD_ID INT NOT NULL,
    TOTAL_PRICE DECIMAL(12, 2) NOT NULL,
    TRANSACTION_STATUS VARCHAR(58),
    TRANSACTION_DATE DATETIME NOT NULL,
    CONSTRAINT AF_TRANSACTION_ID_PK PRIMARY KEY (TRANSACTION_ID)
);




ALTER TABLE AF_ORDER ADD CONSTRAINT AF_ORDER_STATUS_CK CHECK (ORDER_STATUS IN ('PLACED', 'CONFIRMED', 'CANCELLED'));
ALTER TABLE AF_ORDER ADD CONSTRAINT AF_PAYMENT_THROUGH_CK CHECK (PAYMENT_THROUGH IN ('DEBIT_CARD', 'CREDIT_CARD'));

ALTER TABLE AF_CARD ADD CONSTRAINT AF_CARD_TYPE_CK CHECK (CARD_TYPE IN ('DEBIT_CARD', 'CREDIT_CARD'));

ALTER TABLE AF_TRANSACTION ADD CONSTRAINT AF_TRANSACTION_STATUS_CK CHECK (TRANSACTION_STATUS IN ('TRANSACTION_SUCCESS', 'TRANSACTION_FAILED'));



INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1000, 'Wheat', 'High-quality wheat grains', 'Grains', 550, 500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1001, 'Rice', 'Premium long-grain rice', 'Grains', 400, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1002, 'Apples', 'Fresh and juicy apples', 'Fruits', 30, 1000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1003, 'Tomatoes', 'Organically grown tomatoes', 'Vegetables', 25, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1004, 'Potatoes', 'Farm-fresh potatoes', 'Vegetables', 20, 1200);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1005, 'Cucumbers', 'Crunchy cucumbers', 'Vegetables', 15, 900);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1006, 'Carrots', 'Sweet and tender carrots', 'Vegetables', 18, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1007, 'Milk', 'Fresh cow', 'Dairy', 30, 2000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1008, 'Eggs', 'Organic farm eggs', 'Dairy', 15, 3000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1009, 'Honey', 'Pure and natural honey', 'Honey', 108, 100);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1010, 'Corn', 'Freshly harvested corn', 'Grains', 35, 750);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1011, 'Oranges', 'Juicy and ripe oranges', 'Fruits', 28, 900);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1012, 'Lettuce', 'Crisp and green lettuce', 'Vegetables', 125, 1200);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1013, 'Strawberries', 'Sweet and succulent strawberries', 'Fruits', 220, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1014, 'Mutton','High-quality mutton cuts', 'Meat', 540, 500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1015, 'Fish', 'Fresh fish meat', 'Meat', 150, 400);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1016, 'Chicken', 'Farm-raised chicken', 'Meat', 140, 500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1017, 'SoyaBeans', 'Organic soya beans', 'Grains', 5600, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1018, 'Peppers', 'Colorful bell peppers', 'Vegetables', 25, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1019, 'Cabbage', 'Fresh cabbage heads', 'Vegetables', 14, 900);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1020, 'Goat Cheese', 'Artisanal goat cheese', 'Dairy', 65, 200);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1021, 'Oats', 'Whole grain oats', 'Grains', 35, 500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1022, 'Pineapples', 'Sweet and tangy pineapples', 'Fruits', 20, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1023, 'Cauliflower', 'Fresh cauliflower florets', 'Vegetables', 15, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1024, 'Blueberries', 'Plump and juicy blueberries', 'Fruits', 25, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1025, 'Salmon', 'Wild-caught salmon fillets', 'Seafood', 145, 300);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1026, 'Shrimp', 'Fresh shrimp from the coast', 'Seafood', 130, 400);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1027, 'Bananas', 'Fresh Bananas', 'Vegetables', 50, 350);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1028, 'Green Peas', 'Organic green peas', 'Vegetables', 20, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1029, 'Broccoli', 'Fresh broccoli crowns', 'Vegetables', 18, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1030, 'Goat Milk', 'Farm-fresh goat milk', 'Dairy', 27, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1031, 'Watermelon', 'Sweet and water melons', 'Fruits', 15, 400);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1032, 'coriander', 'Fresh coriander bunches', 'Herbs', 15, 1200);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1033, 'Papayas', 'Tropical and flavorful papayas', 'Fruits', 18, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1034, 'Garlic', 'Aromatic and fresh garlic bulbs', 'Herbs', 40, 1500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1035, 'Raspberries', 'Delicious and plump raspberries', 'Fruits', 30, 500);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1036, 'Lavender', 'Fragrant lavender bundles', 'Herbs', 15, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1037, 'Cherries', 'Sweet and succulent cherries', 'Fruits', 49, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1038, 'Basil', 'Fresh basil leaves', 'Herbs', 60, 1000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1039, 'Strawberry Plants', 'Healthy strawberry plant starters', 'Plants', 100, 300);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1040, 'Lettuce Seeds', 'High-quality lettuce seeds for planting', 'Seeds', 22, 5000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1041, 'Cherry Tomatoes', 'Sweet cherry tomatoes', 'Vegetables', 20, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1042, 'Peaches', 'Juicy and ripe peaches', 'Fruits', 28, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1043, 'Pumpkins', 'Fresh and colorful pumpkins', 'Vegetables', 70, 400);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1044, 'Basil Seeds', 'Top-quality basil seeds for planting', 'Seeds', 34, 3000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1045, 'Spinach', 'Nutrient-rich spinach leaves', 'Vegetables', 36, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1046, 'Avocados', 'Creamy and delicious avocados', 'Fruits', 18, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1047, 'Cilantro Seeds', 'High-yield cilantro seeds for planting', 'Seeds', 40, 2000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1048, 'Zucchini', 'Fresh zucchini squash', 'Vegetables', 18, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1049, 'Thyme', 'Aromatic thyme herbs', 'Herbs', 38, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1050, 'Onion Sets', 'Quality onion sets for planting', 'Seeds', 50, 4000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1051, 'Sweet Potatoes', 'Sweet and nutritious sweet potatoes', 'Vegetables', 20, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1052, 'Cucumber Seeds', 'High-yield cucumber seeds for planting', 'Seeds', 67, 3000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1053, 'Mangoes', 'Fresh and tropical mangoes', 'Fruits', 30, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1054, 'Rosemary', 'Aromatic rosemary herbs', 'Herbs', 100, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1055, 'Eggplant', 'Healthy and flavorful eggplants', 'Vegetables', 34, 800);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1056, 'Lemon Seeds', 'High-quality lemon seeds for planting', 'Seeds', 20, 4000);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1057, 'Channa Daal', 'Healthy channa daal', 'Grains', 150, 400);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1058, 'Tur Daal', 'Ripe and delicious Tur daal', 'Grains', 180, 600);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1059, 'Oregano', 'Flavorful oregano herbs', 'Herbs', 35, 700);

INSERT INTO AF_PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY) VALUES (1060, 'Moong Daal', 'Top-quality moong daal', 'Grains', 120, 3000);


INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('susan@gmail.com', 'Susan', 'Susan123', '9876543201', 'Sunset Avenue');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('michael@yahoo.com', 'Michael', 'Michael123', '1234567802', 'Mountain View Drive');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('lisa@hotmail.com', 'Lisa', 'Lisa123', '5555555503', 'Lakefront Road');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('peter@outlook.com', 'Peter', 'Peter123', '8888888804', 'Pine Street');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('emily@aol.com', 'Emily', 'Emily123', '2222222205', 'Elm Avenue');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('robert@protonmail.com', 'Robert', 'Robert123', '9999999906', 'Riverfront Lane');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('linda@live.com', 'Linda', 'Linda123', '7777777707', 'Ocean View Road');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('william@icloud.com', 'William', 'William123', '4444444408', 'Hillside Drive');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('grace@yahoo.com', 'Grace', 'Grace123', '6666666609', 'Meadow Lane');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('david@protonmail.com', 'David', 'David@123', '1111111110', 'Garden Street');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('sophia@gmail.com', 'Sophia', 'Sophia123', '3333333311', 'Sunrise Avenue');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('joseph@yahoo.com', 'Joseph', 'Joseph123', '9999999912', 'Valley View Road');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('olivia@hotmail.com', 'Olivia', 'Olivia123', '5555555513', 'River Road');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('jennifer@outlook.com', 'Jennifer', 'Jennifer123', '7777777714', 'Meadowview Drive');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('thomas@aol.com', 'Thomas', 'Thomas123', '3333333315', 'Forest Lane');
INSERT INTO AF_CUSTOMER (EMAIL_ID, NAME, PASSWORD, PHONE_NUMBER, ADDRESS) VALUES ('mary@icloud.com', 'Mary', 'Mary123', '1111111116', 'Highland Avenue');


INSERT INTO AF_ORDER (ORDER_ID, DATE_OF_ORDER, TOTAL_PRICE, ORDER_STATUS, PAYMENT_THROUGH, DATE_OF_DELIVERY, CUSTOMER_EMAIL_ID, DISCOUNT, DELIVERY_ADDRESS)
VALUES
  (1000, SYSDATE()- INTERVAL 155 DAY, 125.99, 'PLACED', 'CREDIT_CARD', SYSDATE()- INTERVAL 150 DAY, 'william@icloud.com', 10.00, 'Hillside Drive'),
  (1001, SYSDATE()- INTERVAL 145 DAY, 79.99, 'CONFIRMED', 'DEBIT_CARD', SYSDATE()- INTERVAL 140 DAY, 'grace@yahoo.com', 5.00, 'Meadow Lane'),
  (1002, SYSDATE()- INTERVAL 108 DAY, 45.00, 'CONFIRMED', 'DEBIT_CARD', SYSDATE()- INTERVAL 103 DAY, 'david@protonmail.com', 5.00, 'Garden Street'),
  (1003, SYSDATE()- INTERVAL 76 DAY, 199.99, 'CONFIRMED', 'CREDIT_CARD', SYSDATE()- INTERVAL 73 DAY, 'sophia@gmail.com', 10.00, 'Sunrise Avenue'),
  (1004, SYSDATE()- INTERVAL 6 DAY, 59.50, 'CONFIRMED', 'DEBIT_CARD', SYSDATE()- INTERVAL 150 DAY, 'joseph@yahoo.com', 5.00, 'Valley View Road'),
  (1005, SYSDATE()- INTERVAL 2 DAY, 139.95, 'CANCELLED', 'CREDIT_CARD', SYSDATE()- INTERVAL 150 DAY, 'olivia@hotmail.com', 10.00, 'Highland Avenue');



INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60000, 1000, 1000, 1);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60001, 1000, 1001, 2);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60002, 1001, 1002, 3);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60003, 1001, 1003, 1);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60004, 1002, 1004, 4);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60005, 1002, 1005, 2);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60006, 1003, 1006, 5);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60007, 1004, 1007, 1);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60008, 1005, 1008, 3);
INSERT INTO AF_ORDERED_PRODUCT (ORDERED_PRODUCT_ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (60009, 1005, 1009, 2);

select * from AF_ORDERED_PRODUCT;


INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3000, 'william@icloud.com');
INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3001, 'grace@yahoo.com');
INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3002, 'david@protonmail.com');
INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3003, 'sophia@gmail.com');
INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3004, 'joseph@yahoo.com');
INSERT INTO AF_CUSTOMER_CART (CART_ID, CUSTOMER_EMAIL_ID) VALUES (3005, 'olivia@hotmail.com');



INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8000, 3000, 1010, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8001, 3000, 1008, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8002, 3001, 1052, 2);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8003, 3002, 1058, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8004, 3002, 1054, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8005, 3003, 1054, 3);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8006, 3003, 1035, 4);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8007, 3004, 1074, 10);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8008, 3005, 1033, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8009, 3005, 1034, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8010, 3005, 1045, 1);
INSERT INTO AF_CART_PRODUCT (CART_PRODUCT_ID, CART_ID, PRODUCT_ID, QUANTITY) VALUES (8011, 3005, 1052, 1);




INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202002, 'CREDIT_CARD', '6421100412142', '5088c1bc42f5cc6a32cdb92d7524ea06febe006baac86a0fc8986a8ee00602bc', '2023-01-21', 'David', 'david@protonmail.com');
-- CVV => 517
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202003, 'CREDIT_CARD', '682012001000', 'c9a5da075f9e5c3e7a916570946fed4826e181656382e13696fbe0aaf1412bf5', '2023-03-15', 'David', 'david@protonmail.com');
-- CVV => 699
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202004, 'DEBIT_CARD', '44225000586278', 'f7b856c054de7ccced087ad4f9413380ec494e40abc818b840aaad990ca3c5bc', '2023-04-10', 'David', 'david@protonmail.com');
-- CVV => 768
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202005, 'CREDIT_CARD', '682012001000', '0c658eb5d61e88c86f37613342bbce6cbf278a9a86ba6514dc7e5c205f76c99f', '2023-06-22', 'David', 'david@protonmail.com');
-- CVV => 748
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202006, 'CREDIT_CARD', '682012001000', '114bd151f8fb0c58642d2170da4ae7d7c57977260ac2cc8905306cab6b2acabc', '2023-09-14', 'Grace', 'grace@yahoo.com');
-- CVV => 234
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202007, 'CREDIT_CARD', '682012001000', '40962624bfc236888ff8a68a74b0c30166b7245423520bb28196b67f57d5e332', '2023-11-30', 'Grace', 'grace@yahoo.com');
-- CVV => 739
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202008, 'CREDIT_CARD', '682012001000', 'eaa1938017b2d55e02387d0837e1b56bc124ae8a17624e10e366bae6c4b9c834', '2023-12-01', 'Grace', 'grace@yahoo.com');
-- CVV => 894
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202009, 'CREDIT_CARD', '682012001000', 'd1c78c9aa5dcb0991f46b25fbaaa359d7d5823ac7a2a94c4d4a31da42a26c24f', '2024-02-14', 'Grace', 'grace@yahoo.com');
-- CVV => 805
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202010, 'DEBIT_CARD', '442250005862', '52f11620e397f867b7d9f19e48caeb64658356a6b5d17138c00dd9feaf5d7ad6', '2024-04-20', 'Grace', 'grace@yahoo.com');
-- CVV => 184
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202011, 'CREDIT_CARD', '400054222501', '10ba045e9ee40807e57f6093280b9fa9eaf640ba4955e340ae4c749382ad96fc', '2024-07-13', 'Jennifer', 'jennifer@outlook.com');
-- CVV => 684
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202012, 'CREDIT_CARD', '400054222501', '61182f39851829ca78c919a83ecbfa045fc0686bff16d0cfa3e643988d9dfecd', '2024-09-30', 'Jennifer', 'jennifer@outlook.com');
-- CVV => 730
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202013, 'DEBIT_CARD', '442250005862', 'fce86e339dc3131c489202ec3b6c8d4319c61f152b3541ba0e4141e5d5c3fac3', '2024-12-05', 'Jennifer', 'jennifer@outlook.com');
-- CVV => 807
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202014, 'CREDIT_CARD', '400054222501', '728bf33aab1d32d4dca8fcc6a020d8baeb6b9910104541aa68c2d408154734fc', '2025-02-19', 'Jennifer', 'jennifer@outlook.com');
-- CVV => 920
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202015, 'CREDIT_CARD', '400054222501', 'bda584056eb9957d6c681e00079eff36fec289e2a0432a4221b95438dfef5ca4', '2025-04-10', 'Jennifer', 'jennifer@outlook.com');
-- CVV => 637
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202016, 'DEBIT_CARD', '442250005862', '4621c1d55fa4e86ce0dae4288302641baac86dd53f76227c892df9d300682d41', '2025-06-22', 'Michael', 'michael@yahoo.com');
-- CVV => 203
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202017, 'CREDIT_CARD', '400054222501', '303c8bd55875dda240897db158acf70afe4226f300757f3518b86e6817c00022', '2025-09-15', 'Michael', 'michael@yahoo.com');
-- CVV => 273
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202018, 'DEBIT_CARD', '442250005862', '00bebc5be79d19e1b8b3f250dc39aebfa9a054baf5f8d61380438d92394c476a', '2025-11-28', 'Michael', 'michael@yahoo.com');
-- CVV => 671
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202019, 'CREDIT_CARD', '400054222501', '02e6295d8f522840f09b5194b3f023799ad6ed3306d9296005787e792224df20', '2026-01-16', 'Michael', 'michael@yahoo.com');
-- CVV => 344
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202020, 'CREDIT_CARD', '400054222501', '23e8b0175874e1bb3b4799e13a6634a8eddb456c1b8675b871e07ec09abc0c07', '2026-03-09', 'Joseph','joseph@yahoo.com');
-- CVV => 492
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202021, 'DEBIT_CARD', '442250005862', 'f4466a4b51d21014b34f621813a1ed75f1c750ec328d908d9edc989c64778962', '2026-05-22', 'Robert','robert@protonmail.com');
-- CVV => 673
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202022, 'CREDIT_CARD', '400054222501', '62a0eae98b9fc0bd0ad941ae07ae5e2af545a64c8ddc43407bdfe6ae82addb4c', '2026-07-15', 'William','william@icloud.com');
-- CVV => 358
INSERT INTO AF_CARD (CARD_ID, CARD_TYPE, CARD_NUMBER, CVV, EXPIRY_DATE, NAME_ON_CARD, CUSTOMER_EMAIL_ID) VALUES (202023, 'CREDIT_CARD', '400054222501', 'c032851ed192d8ac0a3ad04b0ef3060b44d1f6d62f8c17414006702787c5d88b', '2026-09-30', 'Olivia','olivia@hotmail.com');
-- CVV => 795

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4001, 1002, 3002, 30409.00, 'TRANSACTION_SUCCESS', SYSDATE()- INTERVAL 155 DAY);

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4002, 1001, 3001, 14725.00, 'TRANSACTION_FAILED', SYSDATE()- INTERVAL 145 DAY);

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4003, 1001, 3001, 15750.00, 'TRANSACTION_SUCCESS', SYSDATE()- INTERVAL 108 DAY);

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4004, 1000, 3000, 7553.00, 'PLACED', SYSDATE()- INTERVAL 76 DAY);

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4005, 1004, 3004, 139500.00, 'TRANSACTION_SUCCESS', SYSDATE()- INTERVAL 6 DAY);

INSERT INTO AF_TRANSACTION (TRANSACTION_ID, ORDER_ID, CARD_ID, TOTAL_PRICE, TRANSACTION_STATUS, TRANSACTION_DATE)
VALUES (4006, 1005, 3005, 35290.00, 'TRANSACTION_SUCCESS', SYSDATE()- INTERVAL 2 DAY);


select * from AF_TRANSACTION;

commit;