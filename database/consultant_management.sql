CREATE DATABASE IF NOT EXISTS consultant_management;

USE consultant_management;

CREATE TABLE IF NOT EXISTS consultants (
                                           id BIGINT NOT NULL AUTO_INCREMENT,
                                           name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    technology VARCHAR(100) NOT NULL,
    experience INT NOT NULL,
    PRIMARY KEY (id)
    );