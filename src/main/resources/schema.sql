CREATE TABLE customer (
  customer_id BIGINT PRIMARY KEY,
  name VARCHAR(50)
);

CREATE TABLE product (
  product_id BIGINT PRIMARY KEY,
  product_name VARCHAR(50),
  price DOUBLE
);

CREATE TABLE transaction (
  txn_id BIGINT PRIMARY KEY,
  customer_id BIGINT,
  product_id BIGINT,
  amount DOUBLE,
  txn_date DATE
);
