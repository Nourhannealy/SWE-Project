-- DROP TABLES IF THEY EXIST (Optional clean start)
DROP TABLE IF EXISTS owned_assets;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS assets_history;

-- CREATE TABLES
CREATE TABLE assets (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    price FLOAT NOT NULL
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE owned_assets (
    user_id INTEGER,
    asset_id INTEGER,
    amount DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (asset_id) REFERENCES assets(id)
);

CREATE TABLE assets_history (
    user_id INTEGER,
    asset_id INTEGER,
    transaction_type TEXT CHECK (transaction_type IN ('BUY', 'SELL')),
    amount Double,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (asset_id) REFERENCES assets(id)
);

-- INSERT INTO ASSETS
INSERT INTO assets (name, type, price) VALUES 
('Apple Inc.', 'Stock', 172.34),
('Bitcoin', 'Cryptocurrency', 60000.50),
('Gold', 'Commodity', 1950.75),
('Tesla Inc.', 'Stock', 680.10),
('Ethereum', 'Cryptocurrency', 3100.25),
('Microsoft Corp.', 'Stock', 320.45),
('Amazon.com Inc.', 'Stock', 127.89),
('Alphabet Inc. (Google)', 'Stock', 141.56),
('NVIDIA Corp.', 'Stock', 890.77),
('Meta Platforms Inc.', 'Stock', 245.12),
('Netflix Inc.', 'Stock', 390.60),
('Johnson & Johnson', 'Stock', 157.33),
('Visa Inc.', 'Stock', 278.42),
('Coca-Cola Co.', 'Stock', 61.29),
('JPMorgan Chase & Co.', 'Stock', 198.54);

-- INSERT INTO USERS
INSERT INTO users (username, email, password) VALUES 
('alice', 'alice@example.com', 'hashed_password1'),
('bob', 'bob@example.com', 'hashed_password2'),
('charlie', 'charlie@example.com', 'hashed_password3');

-- INSERT INTO OWNED_ASSETS
-- Original data
INSERT INTO owned_assets (user_id, asset_id, amount) VALUES 
(1, 1, 10),
(1, 2, 0.5),
(2, 3, 20),
(2, 5, 1.2),
(3, 4, 5),
(3, 2, 0.1);

-- New owned assets
INSERT INTO owned_assets (user_id, asset_id, amount) VALUES
-- Alice
(1, 6, 15),
(1, 8, 3),
(1, 10, 5),

-- Bob
(2, 7, 4),
(2, 9, 8),
(2, 14, 25),

-- Charlie
(3, 11, 2),
(3, 12, 6),
(3, 15, 3);

-- Alice (user_id = 1)
INSERT INTO assets_history (user_id, asset_id, transaction_type, amount) VALUES
(1, 1, 'BUY', 10),
(1, 2, 'BUY', 1),
(1, 6, 'BUY', 15),
(1, 8, 'BUY', 3),
(1, 10, 'BUY', 5),
(1, 2, 'SELL', 0.5);  -- She now holds 0.5 BTC

-- Bob (user_id = 2)
INSERT INTO assets_history (user_id, asset_id, transaction_type, amount) VALUES
(2, 3, 'BUY', 20),
(2, 5, 'BUY', 2),
(2, 5, 'SELL', 0.8),  -- Now owns 1.2
(2, 7, 'BUY', 4),
(2, 9, 'BUY', 8),
(2, 14, 'BUY', 25);

-- Charlie (user_id = 3)
INSERT INTO assets_history (user_id, asset_id, transaction_type, amount) VALUES
(3, 4, 'BUY', 5),
(3, 2, 'BUY', 0.1),
(3, 11, 'BUY', 2),
(3, 12, 'BUY', 6),
(3, 15, 'BUY', 3);
