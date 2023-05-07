sudo apt update
40 packages can be upgraded. 
Run 'apt list --upgradable' to see them.

切换root用户： sudo su 

//nginx
sudo apt update
sudo apt install nginx
sudo ln -s /etc/nginx/sites-available/example.com /etc/nginx/sites-enabled/

service nginx start|restart|stop
systemctl status nginx











