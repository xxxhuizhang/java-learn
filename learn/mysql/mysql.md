
#查看当前加密方式：
use mysql;
select user,plugin from user where user='root';
alter user 'root'@'localhost' identified with mysql_native_password by 'password';

#修改加密方式：
alter user 'root'@'localhost' identified with mysql_native_password by 'root';
alter user 'root'@'%' identified with mysql_native_password by 'root';
flush privileges; #生效


CREATE USER 'repl3306'@'192.168.1.103' IDENTIFIED BY 'repl3306';
GRANT REPLICATION SLAVE ON *.* TO 'repl3306'@'192.168.1.103';
SHOW MASTER STATUS;
CHANGE MASTER TO MASTER_HOST='192.168.1.103', MASTER_USER='repl3306',  MASTER_PASSWORD='repl3306', MASTER_LOG_FILE='mysql3306bin.000001',MASTER_LOG_POS=472629; 
start slave ,stop slave; SHOW slave STATUS;

start slave ;
stop slave;
SHOW slave STATUS;

mysql 加表锁 :
lock table xxxtbl read;  #加读锁  
lock table xxxtbl write; #加写锁
unlock tables; 

set autocommit = 0;
update xx
commit;




# SQL_CALC_FOUND_ROWS 特性
SQL_CALC_FOUND_ROWS 特性, 简单的讲, 就是可以让你在 limit 的分页查询中, 也能一起查到总数, 对于上述的代码示例, 具体是这样去操作的:
select SQL_CALC_FOUND_ROWS * from programmer where age >= 35 limit 0, 10;
基本上, 就还是前述的分页查询, 但 select 后面增加了 SQL_CALC_FOUND_ROWS, 之后, 要取得这个总数, 再紧接着发一条这样的查询即可:
SELECT FOUND_ROWS();
说到这里, 有人可能会说, 那还不是得发两条? 不过这里的第二个查询仅仅是取出第一次那条查询的总数而已, 只是一个取结果的操作而已, 你取不取, 结果都已经在那里了, 这个取的操作不会再去查数据库, 是一个很快的操作.
而且我们可以看得, 这是一种特殊形式, 不再包含前述那些条件, 甚至连表名这些都省略了. 所以这里其实也有一个需要特别注意的事项: ** SELECT FOUND_ROWS() 需要紧接着前述分页查询去执行 **, 不然它取得的结果可能就不对了.

