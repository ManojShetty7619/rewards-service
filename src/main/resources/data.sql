---------------------------------------------------------
-- Customers
---------------------------------------------------------

INSERT INTO customer VALUES (1, 'John');
INSERT INTO customer VALUES (2, 'Alice');
INSERT INTO customer VALUES (3, 'Bob');
INSERT INTO customer VALUES (4, 'David');
INSERT INTO customer VALUES (5, 'Emma');
INSERT INTO customer VALUES (6, 'Charlie');

---------------------------------------------------------
-- Products
---------------------------------------------------------

INSERT INTO product VALUES (101, 'Mobile', 600);
INSERT INTO product VALUES (102, 'Headphones', 120);
INSERT INTO product VALUES (103, 'Charger', 40);

---------------------------------------------------------
-- Customer 1 (Full 3 months - Good rewards)
---------------------------------------------------------

INSERT INTO transaction VALUES (1, 1, 101, 120, '2026-01-15');
INSERT INTO transaction VALUES (2, 1, 102, 80,  '2026-02-10');
INSERT INTO transaction VALUES (3, 1, 103, 150, '2026-03-05');

---------------------------------------------------------
-- Customer 2 (Only 2 months data)
---------------------------------------------------------

INSERT INTO transaction VALUES (4, 2, 101, 200, '2026-02-18');
INSERT INTO transaction VALUES (5, 2, 102, 60,  '2026-03-01');

---------------------------------------------------------
-- Customer 3 (Only 1 month data)
---------------------------------------------------------

INSERT INTO transaction VALUES (6, 3, 101, 130, '2026-03-10');

---------------------------------------------------------
-- Customer 4 (Older than 3 months ONLY)
---------------------------------------------------------

INSERT INTO transaction VALUES (7, 4, 101, 180, '2025-10-10');
INSERT INTO transaction VALUES (8, 4, 102, 90,  '2025-11-15');

---------------------------------------------------------
-- Customer 5 (Low purchases - No rewards)
---------------------------------------------------------

INSERT INTO transaction VALUES (9, 5, 101, 20,  '2026-01-05');
INSERT INTO transaction VALUES (10, 5, 102, 45, '2026-02-08');
INSERT INTO transaction VALUES (11, 5, 103, 30, '2026-03-12');

---------------------------------------------------------
-- Customer 6 (Mixed old + recent)
---------------------------------------------------------

INSERT INTO transaction VALUES (12, 6, 101, 300, '2025-12-20');
INSERT INTO transaction VALUES (13, 6, 102, 110, '2026-02-25');