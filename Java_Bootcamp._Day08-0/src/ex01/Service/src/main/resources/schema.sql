drop schema if exists Email cascade;
create schema if not exists Email;

CREATE TABLE Email.User (
                           Id SERIAL PRIMARY KEY,
                           Email varchar(50) NOT NULL UNIQUE
);