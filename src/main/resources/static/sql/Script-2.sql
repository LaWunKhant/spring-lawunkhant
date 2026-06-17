DROP TABLE IF EXISTS 輸出先;

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
(25, 'リトール王国', 150, '南洋'),
(31, 'タハル王国', 240, '北洋'),
(32, 'サザンナ王国', 80, '南洋'),
(33, 'マリヨン国', 300, '中部');

SELECT MIN(人口) FROM 輸出先;

SELECT MAX(人口) FROM 輸出先;

SELECT SUM(人口) FROM 輸出先;

SELECT SUM(人口) FROM 輸出先 WHERE 輸出先コード >= 20;

SELECT COUNT(*) FROM 輸出先 WHERE 人口 >= 100;

SELECT COUNT(*) FROM 輸出先 WHERE 地方 = '北洋';

SELECT MAX(人口) FROM 輸出先 WHERE 地方 = '北洋';

SELECT SUM(人口) FROM 輸出先 WHERE 輸出先名 <> 'リトール王国';

SELECT 地方, AVG(人口) 
FROM 輸出先 
GROUP BY 地方 
HAVING AVG(人口) >= 200;

SELECT 地方, COUNT(*) 
FROM 輸出先 
GROUP BY 地方 
HAVING COUNT(*) >= 3;