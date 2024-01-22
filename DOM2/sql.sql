CREATE DATABASE IF NOT EXISTS libraries;
USE libraries;

CREATE TABLE IF NOT EXISTS libraries (
    id INT NOT NULL,
    address VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS books (
    id VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    publish_date DATE NOT NULL,
    description TEXT NOT NULL,
    library_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (library_id) REFERENCES libraries(id)
);