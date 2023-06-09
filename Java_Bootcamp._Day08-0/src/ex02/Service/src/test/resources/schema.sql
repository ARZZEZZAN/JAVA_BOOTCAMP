DROP SCHEMA IF EXISTS EMAIL CASCADE;
CREATE SCHEMA IF NOT EXISTS EMAIL;

CREATE TABLE EMAIL.USER (
                            ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
                            EMAIL VARCHAR(50) NOT NULL UNIQUE,
                            PASSWORD VARCHAR(50) NOT NULL UNIQUE
);