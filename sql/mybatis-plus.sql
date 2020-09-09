-- 创建库
CREATE DATABASE mp;
-- 使用库
USE mp;
-- 创建表
CREATE TABLE tbl_employee(
    id INT(11) PRIMARY KEY AUTO_INCREMENT, last_name VARCHAR(50),
    email VARCHAR(50),
    gender CHAR(1),
    age int,
    version int(11),
    emp_status varchar (11)
);
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Tom','tom@atguigu.com',1,22);
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Jerry','jerry@atguigu.com',0,25);
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Black','black@atguigu.com',1,30);
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('White','white@atguigu.com',0,35);

create table tbl_user(
 id INT(11) PRIMARY KEY AUTO_INCREMENT, last_name VARCHAR(50),
 name VARCHAR(50),
 logic_flag int(11)
);