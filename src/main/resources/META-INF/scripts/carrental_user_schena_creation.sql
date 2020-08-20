CREATE DATABASE carrental;
CREATE USER 'carrentaluser'@'%' IDENTIFIED BY 'carrentaluser';
GRANT ALL PRIVILEGES ON carrental.* TO 'carrentaluser'@'%';