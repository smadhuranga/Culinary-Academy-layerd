DROP DATABASE IF EXISTS CulinaryAcademy;
CREATE DATABASE CulinaryAcademy;
USE CulinaryAcademy;

CREATE TABLE student (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          contact VARCHAR(20) UNIQUE NOT NULL,
                          address VARCHAR(255),
                          joinedDate DATETIME
);


CREATE TABLE course (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           courseId VARCHAR(100) UNIQUE NOT NULL,
                           courseName VARCHAR(255) NOT NULL,
                           durationMonths INT,
                           fee DECIMAL(10, 2),
                           description VARCHAR(255)
);


CREATE TABLE registration (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               studentId INT,
                               courseId INT,
                               registration_date DATETIME,
                               status VARCHAR(50),
                               startDate DATETIME,
                               endDate DATETIME,
                               FOREIGN KEY (studentId) REFERENCES student(id) ON UPDATE CASCADE ON DELETE CASCADE,
                               FOREIGN KEY (courseId) REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE payment (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          registrationId INT,
                          amount DECIMAL(10, 2),
                          paymentMethod VARCHAR(50),
                          paymentDate DATETIME,
                          transactionId VARCHAR(100),
                          status VARCHAR(50),
                          FOREIGN KEY (registrationId) REFERENCES registration(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE user (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       passwordHash VARCHAR(255) NOT NULL
);


CREATE TABLE role (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100) UNIQUE NOT NULL,
                       description VARCHAR(255)
);


CREATE TABLE userRole (
                            userId INT,
                            roleId INT,
                            PRIMARY KEY (userId, roleId),
                            FOREIGN KEY (userId) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (roleId) REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE
);
