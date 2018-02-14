CREATE SCHEMA Users;


CREATE TABLE account2
(
   username   TEXT NOT NULL UNIQUE PRIMARY KEY,
   password   TEXT NOT NULL,
   name   TEXT NOT NULL,
   lastname   TEXT NOT NULL,
   created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO account2 (username, password, name, lastname)
     VALUES ('konstantin', '123456', 'Konstantin', 'Ralev'),
            ('lyubomir', '654321', 'Lyubomir', 'Nikov'),
            ('tugbars', '111111', 'Tugbars', 'Hepsakin'),
            ('james', '000000', 'James', 'Mwaura'),
            ('abdullah', '222222', 'Abdullah', 'Haras');