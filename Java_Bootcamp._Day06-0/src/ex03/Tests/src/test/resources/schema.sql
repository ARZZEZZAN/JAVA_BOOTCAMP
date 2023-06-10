create schema if not exists shop;

create table if not exists shop.product (
    id INTEGER IDENTITY PRIMARY KEY,
    name varchar(25),
    price INT
);