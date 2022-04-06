-- create database
-- DROP SCHEMA contacts;
CREATE SCHEMA contacts;
USE contacts;

-- DROP TABLE contacts
CREATE TABLE CONTACTS.contacts (
	id INT PRIMARY KEY,
	name VARCHAR(100),
	number VARCHAR(20),
	email VARCHAR(100),
	address VARCHAR(200)
); 

