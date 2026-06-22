SHOW TABLES;


CREATE TABLE students (
    id VARCHAR(6) PRIMARY KEY,
    name VARCHAR(20),
    gender VARCHAR(10),
    address VARCHAR(50),
    age INT
);

CREATE TABLE exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50)
);

CREATE TABLE student_exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(6),
    exam_id INT,
    score INT
);

DESCRIBE students;


INSERT INTO students VALUES ('001001', '田中', '男', '沖縄', 17);
INSERT INTO students VALUES ('001002', '山田', '女', '埼玉', 18);
INSERT INTO students VALUES ('001003', '佐々木', '男', '東京', 16);
INSERT INTO students VALUES ('001004', '藤田', '女', '愛知', 17);

SELECT COUNT(*) FROM students;

SELECT * FROM students;

SELECT id, name
FROM students;

INSERT INTO students VALUES ('001001', '田中', '男', '沖縄', 17);

SELECT * FROM students;

INSERT INTO exams (title) VALUES ('2021夏季期末テスト');
INSERT INTO exams (title) VALUES ('2021冬期期末テスト');
INSERT INTO exams (title) VALUES ('2022夏季期末テスト');
INSERT INTO exams (title) VALUES ('2022冬期期末テスト');

SELECT * FROM exams;

INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001001', 1, 100);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001001', 3, 50);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001003', 3, 50);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001003', 4, 70);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001004', 2, 77);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001004', 3, 88);
INSERT INTO student_exams (student_id, exam_id, score) VALUES ('001004', 4, 99);

SELECT * FROM student_exams;

SELECT
    s.name,
    e.title,
    se.score
FROM student_exams se
JOIN students s ON se.student_id = s.id
JOIN exams e ON se.exam_id = e.id;