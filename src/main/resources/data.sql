INSERT INTO customers (name, email, age) VALUES ('John Doe', 'john.doe@example.com', 30);
INSERT INTO customers (name, email, age) VALUES ('Jane Smith', 'jane.smith@example.com', 25);

INSERT INTO employers (name, address) VALUES ('Tech Corp', '123 Tech Road');
INSERT INTO employers (name, address) VALUES ('Business Inc', '456 Business Avenue');

INSERT INTO accounts (number, currency, balance, customer_id) VALUES ('111', 'USD', 1000.0, 1);
INSERT INTO accounts (number, currency, balance, customer_id) VALUES ('222', 'EUR', 2000.0, 2);

INSERT INTO customer_employer (customer_id, employer_id) VALUES (1, 1);
INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 2);
-- INSERT INTO customer_employer (customer_id, employer_id) VALUES (2, 1);
