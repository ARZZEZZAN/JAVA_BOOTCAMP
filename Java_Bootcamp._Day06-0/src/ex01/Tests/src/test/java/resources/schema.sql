create schema if not exists shop;

create table if not exists shop.product (
    id INTEGER PRIMARY KEY,
    name varchar(25),
    price INT
);