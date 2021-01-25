create database fotoBooth;
use fotoBooth;

create table photographers(
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT, 
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL
);

create table requests(
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT, 
    name varchar(255) NOT NULL,
    photo_type varchar(255) NOT NULL,
    quantity varchar(255) NOT NULL,
	month varchar(255) NOT NULL,
    day varchar(255) NOT NULL,
    userId int NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id),
    availableId int NOT NULL,
    FOREIGN KEY (availableId) REFERENCES available(id)
);


CREATE TABLE available (
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    timeId int NOT NULL,
	month varchar(255) NOT NULL,
    day varchar(255) NOT NULL,
    FOREIGN KEY (timeId) REFERENCES photographers(id)
);

create table users(
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT, 
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

INSERT INTO photographers (name, email) 
VALUES ("John Smith","johnsmith@gmail.com");

INSERT INTO photographers (name, email) 
VALUES ("Billy Bob","billybob@gmail.com");

INSERT INTO photographers (name, email) 
VALUES ("Cameron White","cameronwhite@gmail.com");

INSERT INTO photographers (name, email) 
VALUES ("Sally Jones","sallyjones@gmail.com");

INSERT INTO photographers (name, email) 
VALUES ("James Bond","jamesbond@gmail.com");

INSERT INTO photographers (name, email) 
VALUES ("James Bond","jamesbond@gmail.com");

INSERT INTO available (timeId, month, day) 
VALUES (1,"January","15");

INSERT INTO available (timeId, month, day) 
VALUES (1,"February","28");

INSERT INTO available (timeId, month, day) 
VALUES (2,"March","16");

INSERT INTO available (timeId, month, day) 
VALUES (2,"April","20");

INSERT INTO available (timeId, month, day) 
VALUES (3,"June","24");

INSERT INTO available (timeId, month, day) 
VALUES (2,"June","24");


DROP TABLE requests;
SELECT * FROM photographers, available where photographers.id = timeId;
SELECT id FROM available WHERE month="June" AND day="24";
SELECT id FROM available WHERE month="June" AND day="24" limit 1;

ALTER TABLE available
ADD status varchar(255) default "Open";
