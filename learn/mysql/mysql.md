
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