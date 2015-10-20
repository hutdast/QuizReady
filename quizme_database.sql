-- phpMyAdmin SQL Dump
-- version 4.2.5
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Oct 20, 2015 at 07:35 PM
-- Server version: 5.5.38
-- PHP Version: 5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `quizme`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `createUser`(IN `login` VARCHAR(50), IN `pass` VARCHAR(50), IN `uEmail` VARCHAR(50))
    SQL SECURITY INVOKER
    COMMENT 'Create a new user '
BEGIN

     Insert into user(Login,Passcode,Date_of_Registration,Email)
values(login,pass, NOW(), uEmail);        

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verify`(IN `login` VARCHAR(50), IN `pass` VARCHAR(50))
    NO SQL
BEGIN

              
               declare x VARCHAR(50);
               declare y VARCHAR(50);
                  set x = (select Login from user  where Login = login and Passcode = pass);     
                  
               
             if  x is NULL then
             set x = 'no';
             select x;
             else 
             set x ='match';
             select x;
             end if;
          
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `quizmanagement`
--

CREATE TABLE `quizmanagement` (
`quizID` int(11) NOT NULL,
  `Login` varchar(50) DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `category` varchar(50) NOT NULL,
  `Date_Created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
`userID` int(11) NOT NULL,
  `Login` varchar(50) NOT NULL,
  `Passcode` varchar(50) NOT NULL,
  `Date_of_Registration` datetime NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `Login`, `Passcode`, `Date_of_Registration`, `Email`) VALUES
(25, 'Default', 'ERtyu459$$ ', '2015-08-23 16:43:29', 'plotu@yahoo.com'),
(35, 'hutdast', 'palascaYU#$12', '2015-09-24 21:52:31', 'hg@yahoo.com'),
(36, 'nicky', 'MeRcro23%^', '2015-09-24 21:59:11', 'nik@gmail.com'),
(37, 'pmidi', 'nikeman12', '2015-09-27 23:59:04', 'pmidi20@yahoo.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `quizmanagement`
--
ALTER TABLE `quizmanagement`
 ADD PRIMARY KEY (`quizID`), ADD UNIQUE KEY `title` (`title`), ADD KEY `Login` (`Login`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`userID`), ADD UNIQUE KEY `Login` (`Login`), ADD UNIQUE KEY `Passcode` (`Passcode`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `quizmanagement`
--
ALTER TABLE `quizmanagement`
MODIFY `quizID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=38;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `quizmanagement`
--
ALTER TABLE `quizmanagement`
ADD CONSTRAINT `quizmanagement_ibfk_1` FOREIGN KEY (`Login`) REFERENCES `user` (`Login`) ON DELETE CASCADE;
