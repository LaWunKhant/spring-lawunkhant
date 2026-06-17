CREATE TABLE 商品 (
    商品コード INT PRIMARY KEY,
    商品名 VARCHAR(50) NOT NULL,
    単価 INT NOT NULL
);

CREATE TABLE 売上明細 (
    報告書コード INT NOT NULL,
    商品コード INT NOT NULL,
    個数 INT NOT NULL,
    FOREIGN KEY (商品コード) REFERENCES 商品(商品コード)
);

INSERT INTO 商品 (商品コード, 商品名, 単価) VALUES
(101, 'メロン', 800),
(102, 'いちご', 150),
(103, 'りんご', 120),
(104, 'レモン', 200);

INSERT INTO 売上明細 (報告書コード, 商品コード, 個数) VALUES
(1101, 101, 1100),
(1101, 102, 300),
(1102, 103, 1700),
(1103, 104, 500),
(1104, 101, 2500),
(1105, 103, 2000),
(1105, 104, 700);

SELECT * FROM 売上明細 
WHERE 商品コード IN (
    SELECT 商品コード 
    FROM 商品 
    WHERE 単価 >= 300
);

SELECT * FROM 売上明細 AS U
WHERE 個数 < (
    SELECT AVG(個数) 
    FROM 売上明細 
    WHERE 商品コード = U.商品コード
);