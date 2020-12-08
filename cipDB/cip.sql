 DROP DATABASE IF EXISTS cip;
CREATE DATABASE `cip`; 
USE `cip`;

CREATE TABLE cip1 (
 id  bigint not null auto_increment,
 date_time varchar(19),
 route int,
 change_route_valve bool,
 main_feed_pump bool,
 steam_shut_valve bool,
 steam_regulating_valve int,
 output_flow_temperature_sensor int,
 return_flow_pump bool,
 input_flow_intensity_sensor int,
 input_flow_conductivity_sensor double,
 input_flow_temperature_sensor double,
 input_flow_lye_valve bool,
 input_flow_acid_valve bool,
 input_flow_rinse_water_valve bool,
 circulation_valve bool,
 drain_valve bool,
 output_flow_pure_water_valve bool,
 output_flow_rinse_water_valve bool,
 output_flow_acid_valve bool,
 output_flow_lye_valve bool,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;
 
 CREATE TABLE cip2 (
 id  bigint not null auto_increment,
 date_time varchar(19),
 route int,
 main_feed_pump bool,
 steam_shut_valve bool,
 steam_regulating_valve int,
 output_flow_temperature_sensor int,
 return_flow_pump bool,
 input_flow_intensity_sensor int,
 input_flow_conductivity_sensor double,
 input_flow_temperature_sensor double,
 input_flow_lye_valve bool,
 input_flow_acid_valve bool,
 input_flow_rinse_water_valve bool,
 circulation_valve bool,
 drain_valve bool,
 output_flow_pure_water_valve bool,
 output_flow_rinse_water_valve bool,
 output_flow_acid_valve bool,
 output_flow_lye_valve bool,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8; 
 
 CREATE TABLE cip3 (
 id bigint not null auto_increment,
 date_time varchar(19),
 route int,
  circulation_solution_valve bool,
 main_feed_pump bool,
 steam_shut_valve bool,
 steam_regulating_valve int,
 output_flow_temperature_sensor int,
 return_flow_pump bool,
 input_flow_intensity_sensor int,
 input_flow_conductivity_sensor double,
 input_flow_temperature_sensor double,
 input_flow_lye_valve bool,
 input_flow_acid_valve bool,
 input_flow_rinse_water_valve bool,
 circulation_valve bool,
 drain_valve bool,
 output_flow_pure_water_valve bool,
 output_flow_rinse_water_valve bool,
 output_flow_acid_valve bool,
 output_flow_lye_valve bool,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8; 
 
 CREATE TABLE cip4 (
 id  bigint not null auto_increment,
 date_time varchar(19),
 route int,
 main_feed_pump bool,
 steam_shut_valve bool,
 steam_regulating_valve int,
 output_flow_temperature_sensor int,
 return_flow_pump bool,
 input_flow_intensity_sensor int,
 input_flow_conductivity_sensor double,
 input_flow_temperature_sensor double,
 input_flow_lye_valve bool,
 input_flow_acid_valve bool,
 input_flow_rinse_water_valve bool,
 circulation_valve bool,
 drain_valve bool,
 output_flow_pure_water_valve bool,
 output_flow_rinse_water_valve bool,
 output_flow_acid_valve bool,
 output_flow_lye_valve bool,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;
 
 CREATE TABLE common (
 id  bigint not null auto_increment,
 date_time varchar(19),
 water_to_lye_tank_valve bool,
 water_to_acid_tank_valve bool,
 water_to_water_tank_valve bool,
 lye_to_lye_tank_pump bool,
 acid_to_acid_tank_pump bool,
 upper_level_lye_tank_valve bool,
 lower_level_lye_tank_valve bool,
 upper_level_acid_tank_valve bool,
 lower_level_acid_tank_valve bool,
 upper_level_rinse_water_tank_valve bool,
 lower_level_rinse_water_tank_valve bool,
 upper_level_pure_water_tank_valve bool,
 lower_level_pure_water_tank_valve bool,
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;
 
  CREATE TABLE warning (
 id  bigint not null auto_increment,
 date_time varchar(19),
 cip int,
 route int,
 warning_code int, 
 primary key (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8;