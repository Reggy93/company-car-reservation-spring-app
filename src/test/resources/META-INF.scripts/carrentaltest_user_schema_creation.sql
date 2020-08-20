CREATE DATABASE carrentaltest;
CREATE USER 'carrentalusertest'@'%' IDENTIFIED BY 'carrentalusertest';
GRANT ALL PRIVILEGES ON carrentaltest.* TO 'carrentalusertest'@'%';