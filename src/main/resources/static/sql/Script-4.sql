
DROP TABLE IF EXISTS 売上明細;
DROP TABLE IF EXISTS 商品;

-- Create the "商品" (Products) Table
CREATE TABLE 商品 (
    商品コード INT PRIMARY KEY,
    商品名 VARCHAR(50),
    単価 INT
);


CREATE TABLE 売上明細 (
    報告書コード INT,
    商品コード INT,
    個数 INT
);


-- Populate "商品" Table
INSERT INTO 商品 (商品コード, 商品名, 単価) VALUES
(101, 'メロン', 800),
(102, 'いちご', 150),
(103, 'りんご', 120),
(104, 'レモン', 200),
(555, 'なし', 555);


INSERT INTO 売上明細 (報告書コード, 商品コード, 個数) VALUES
(1101, 101, 1100),
(1101, 102, 300),
(1102, 103, 1700),
(1103, 104, 500),
(1104, 101, 2500),
(1105, 103, 2000),
(1105, 104, 700),
(9999, 999, 999);


SELECT * FROM 商品 
LEFT JOIN 売上明細 
ON 商品.商品コード = 売上明細.商品コード;

SELECT * FROM 商品 
RIGHT JOIN 売上明細 
ON 商品.商品コード = 売上明細.商品コード
WHERE 商品.単価 >= 150;

SELECT * FROM 商品 
INNER JOIN 売上明細 
ON 商品.商品コード = 売上明細.商品コード
WHERE 売上明細.個数 >= 1000;