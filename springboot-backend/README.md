### script to create the database
-- create database my_database;
-- drop database my_database
SELECT * FROM pg_catalog.pg_tables;

### Script to initialise the database
DROP TABLE employees;
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR (50) UNIQUE NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    email_id VARCHAR (255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL
);