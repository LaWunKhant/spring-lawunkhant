-- スタッフテーブル（Gender Enum用）
CREATE TABLE staffs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10 BYTE) NOT NULL,
    -- 'MALE', 'FEMALE' 以外の文字列が入らないように制限
    CONSTRAINT check_staff_gender CHECK (gender IN ('MALE', 'FEMALE'))
);

-- 注文テーブル（OrderStatus Enum用）
CREATE TABLE order_infos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price INT NOT NULL,
    status INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    -- 1, 2, 3 以外の数値が入らないように制限
    -- 1: 注文済み, 2: 発送準備中, 3: 発送完了
    CONSTRAINT check_order_status CHECK (status IN (1, 2, 3))
);