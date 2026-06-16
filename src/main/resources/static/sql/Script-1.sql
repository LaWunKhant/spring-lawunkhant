CREATE TABLE 輸出先 (
    輸出先コード INT PRIMARY KEY,
    輸出先名 VARCHAR(50),
    人口 INT,
    地方 VARCHAR(50)
);

INSERT INTO 輸出先 (輸出先コード, 輸出先名, 人口, 地方) VALUES
(12, 'ミナンミ王国', 100, '南洋'),
(15, 'パローヌ国', 200, '中部'),
(22, 'トカンタ国', 150, '北洋'),
(23, 'アルファ帝国', 80, '北洋'),
(30, NULL, 110, '中部');

SELECT * FROM 輸出先 WHERE 人口 >= 100;

SELECT * FROM 輸出先 WHERE 人口 < 100;

SELECT * FROM 輸出先 WHERE 輸出先コード < 20 AND 人口 > 150;

SELECT * FROM 輸出先 WHERE 輸出先コード >= 20 OR 人口 >= 200;

SELECT 人口 FROM 輸出先 WHERE 輸出先名 = 'トカンタ国';

SELECT * FROM 輸出先 WHERE 輸出先名 LIKE '%ン%';

SELECT * FROM 輸出先 WHERE 輸出先名 IS NOT NULL;