@echo off
echo Setting up things...
echo 
echo Setting up MySQL
..\xampp\mysql\bin\mysqld --install
net start mysql
..\xampp\mysql\bin\mysqladmin -u root password toor
echo Done setting up MySQL
pause