
docker exec -it postgres psql -U  postgres
docker exec -it postgres psql mydb -U  postgres

createdb  mydb;
dropdb mydb;

create database activiti;
create schema activiti;
drop schema activiti;
alter schema activiti owner to activiti ;


1、相当与mysql的show databases;
select datname from pg_database;

2、相当于mysql的show tables;
SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
public 是默认的schema的名字

3、相当与mysql的describe table_name;
SELECT column_name FROM information_schema.columns WHERE table_name ='table_name';
'table_name'是要查询的表的名字


select current_date;
select current_time;
select current_timestamp;
select localtimestamp(0);
select Date 'today';
select Date 'tomorrow';
SELECT TO_DATE('20180301','yyyyMMdd') ;
SELECT TO_DATE('2018#03#01','yyyy#MM#dd') ;
SELECT TO_DATE('20180301','yyyyMMdd') - CAST('1' AS INT) * interval '1 month' ; -- day
SELECT to_char(current_timestamp,'yyyy-MM-dd hh:MM:ss');
-- 一个是写若干个0，如果待转换的值位数少于于你定义的转换格式位数，输出值会自动在左边补0，位数补齐到转换格式的长度；
-- 如果待转换的值位数多于你定义的转换格式位数，输出值为：##（长度跟你定义的转换格式一样）；
-- 另一个是写若干个9，如果待转换的值位数少于你定义的转换格式位数，正常输出；
-- 如果待转换的值位数多于于你定义的转换格式位数，输出值为：##（长度跟你定义的转换格式一样）；
-- 转换格式如果写其他数字，输出结果为转换格式的值。
SELECT to_char(123,'00');
SELECT to_char(123,'9999999');
SELECT to_number('123');
SELECT FLOOR(1000 + random() * 100);


SELECT version();

select  user;
select  replace(gen_random_uuid()::varchar,'-','');
select  length(replace(gen_random_uuid()::varchar,'-',''));
select  split_part('a-b','-',1); -- a
select  'a' || '-' || 'b';  -- 'a-b'
select  generate_series(1,10);


select regexp_split_to_table('12,23,34,45,56,66',',');
select regexp_split_to_array('12,23,34,45,56,66',',');

if('1','2','3'); -- 三元运算
select coalesce('1','2'); --postgresql
--select nvl('1','2'); --Oracle postgresql
--select ifnull('1','2'); --mysql

select string_agg(distinct num,','order by num desc) from (
  select generate_series(1,5)::varchar num
) t_num;

select array_to_string(array_agg(distinct num order by num desc), ',') from (
select generate_series(1,5) num
) t_num;


select position('abd' in 'abcdab');
select strpos( 'abcdab','cd');
select strpos( 'abcdab','cd')::BOOLEAN;
select  'ABcdef' ~* 'a.*f';  --  ~ 匹配   ~* 匹配(case ignroe)  !~ 不匹配


create unique index grouId_and_memberId on chat_member (group_id, member_id);
alter table if exists chat_message add constraint FKdgx6rii6jdb7vcelx67r1h2r1 foreign key (group_id) references chat_group;
alter table if exists chat_member add constraint grouId_and_memberId unique (group_id, member_id);

ALTER TABLE `USERINFO`
    ADD CONSTRAINT `FK_USER_ID` FOREIGN KEY (`USERID`) REFERENCES `USERACCOUNT` (`USERID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;