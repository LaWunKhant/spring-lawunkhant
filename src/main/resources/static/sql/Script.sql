SELECT * FROM users;

CREATE TABLE export_destinations (
	export_code INT PRIMARY KEY,
	destination_name VARCHAR(100),
	population INT,
	region VARCHAR(50)
);

INSERT INTO export_destinations (export_code, destination_name, population, region) VALUES
(12, 'ミナンミ王国', 100, '南洋'),
(15, 'パローヌ国', 200, '中部'),
(22, 'トカンタ国', 160, '北洋'),
(23, 'アルファ帝国', 80, '北洋');

UPDATE export_destinations
SET population =150
WHERE destination_name = 'トカンタ国';

DELETE FROM export_destinations
WHERE destination_name = 'パローヌ国';

SELECT * FROM export_destinations;

