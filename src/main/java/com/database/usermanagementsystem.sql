CREATE DATABASE usermanagementsystem;
use usermanagementsystem;

CREATE TABLE users(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(40),
email VARCHAR(40),
mobile VARCHAR(20),
dob DATE,
city VARCHAR(40),
gender VARCHAR(20)
);

SELECT id,name,email,mobile,dob,city,gender FROM users;

drop table users;