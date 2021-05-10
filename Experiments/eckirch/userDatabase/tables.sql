-- create a database for user
create database classes;
use classes;

CREATE TABLE user (
UserID INTEGER,
name VARCHAR(35),
email VARCHAR(50),
password VARCHAR(20),
admin boolean,
banned boolean,
primary key (UserID,email,password)
);

CREATE TABLE puzzles (
puzzleID INTEGER,
creatorID INTEGER,
puzzleName VARCHAR(50),
-- 1 = Slider, 2 = Jigsaw, 3 = Picross, or something like that
puzzleType INTEGER,
-- 1 = Easy, 2 = Medium, 3 = Hard
puzzleDifficulty INTEGER,
primary key (PuzzleID),
foreign key (creatorID) references user(userID)
);

CREATE TABLE scores (
puzzleScored INTEGER,
scoreMaker INTEGER,
-- In seconds
timeToComplete INTEGER,
time_completed DATETIME,
primary key (scoreMaker,timeToComplete,time_completed),
foreign key (scoreMaker) references user(userID),
foreign key (puzzleScored) references puzzles(puzzleID)
);

