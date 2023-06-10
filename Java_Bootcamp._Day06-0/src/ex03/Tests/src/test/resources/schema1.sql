create schema if not exists service;

create table if not exists service.user (
    id INTEGER IDENTITY PRIMARY KEY,
    login varchar(50),
    password varchar(50),
    authenticated boolean not null
);