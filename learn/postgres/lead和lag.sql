# lead()/lag()函数
-- https://www.cnblogs.com/shiliye/p/12361624.html


CREATE TABLE table1 (
                        id int NOT NULL,
                        emp_id varchar(255),
                        telecom_id varchar(255)
);

insert into table1 (id, emp_id, telecom_id) values(1, '1', '1');
insert into table1 (id, emp_id, telecom_id) values(2, '1', '1');
insert into table1 (id, emp_id, telecom_id) values(3, '1', '1');
insert into table1 (id, emp_id, telecom_id) values(4, '1', '2');
insert into table1 (id, emp_id, telecom_id) values(5, '1', '3');
insert into table1 (id, emp_id, telecom_id) values(6, '1', '3');
insert into table1 (id, emp_id, telecom_id) values(7, '1', '1');
insert into table1 (id, emp_id, telecom_id) values(8, '2', '5');
insert into table1 (id, emp_id, telecom_id) values(9, '2', '1');
insert into table1 (id, emp_id, telecom_id) values(10, '1', '1');
insert into table1 (id, emp_id, telecom_id) values(11, '2', '1');
insert into table1 (id, emp_id, telecom_id) values(12, '2', '1');



SELECT id,emp_id,telecom_id,
       SUM(CASE
           WHEN pretelecomVal = telecom_id and pre_emp_idVal = emp_id
           then 0 else 1 end) over(order by id) rnk
FROM (
         select *,
                lag(telecom_id) over(partition by emp_id order by id) pretelecomVal,
                lag(emp_id) over(order by id) pre_emp_idVal
         from table1
     ) t1







