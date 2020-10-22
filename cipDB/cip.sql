DROP DATABASE IF EXISTS ciptest;
CREATE DATABASE `ociptest`; 
USE `ciptest`;

CREATE TABLE cip1 (
 id  int (11) not null auto_increment,
 main_feed_pump bool,
 output_flow_lye_valve bool,
 output_flow_temperature_sensor int,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;