create database NatureBlissHolidays ;

use NatureBlissHolidays;

CREATE TABLE Admin (
                       Email varchar(25) NOT NULL,
                       User_name varchar(25) DEFAULT NULL,
                       Passward varchar(255) DEFAULT NULL,
                       Type varchar(10) DEFAULT NULL,
                       PRIMARY KEY (Email)
);


CREATE TABLE Guest (
                       identityDetails varchar(100) NOT NULL,
                       name varchar(25) NOT NULL,
                       password varchar(255) DEFAULT NULL,
                       Email varchar(50) DEFAULT NULL,
                       PRIMARY KEY (identityDetails)
);


CREATE TABLE Room (
                      Room_id varchar(5) NOT NULL PRIMARY KEY,
                      Room_number int,
                      Type varchar(25),
                      Rate double(20,2),
                      Status varchar(20)
);

CREATE TABLE Client (
                        id varchar(100) NOT NULL,
                        name varchar(100) NOT NULL,
                        email varchar(100) DEFAULT NULL,
                        phone varchar(20) DEFAULT NULL,
                        address varchar(255) DEFAULT NULL,
                        check_in date DEFAULT NULL,
                        check_out date DEFAULT NULL,
                        PRIMARY KEY (id)
);


CREATE TABLE Package (
                         Package_id varchar(5) NOT NULL PRIMARY KEY,
                         Name varchar(25),
                         Type varchar(25),
                         Price double(20,2)
);

CREATE TABLE Tour (
                      Tour_id varchar(5) NOT NULL,
                      Name varchar(25) DEFAULT NULL,
                      Description varchar(25) DEFAULT NULL,
                      Location varchar(25) DEFAULT NULL,
                      PRIMARY KEY (Tour_id)
);


CREATE TABLE payment (
                         payId varchar(30) NOT NULL,
                         amount varchar(10) NOT NULL,
                         status varchar(10) DEFAULT NULL,
                         paidDate varchar(10) DEFAULT NULL,
                         method varchar(10) DEFAULT NULL,
                         PRIMARY KEY (payId)
);


CREATE TABLE Employee (
                          Employee_id varchar(5) NOT NULL PRIMARY KEY,
                          Name varchar(25),
                          Address varchar(50),
                          Salary double(20,2),
                          type ENUM('Driver', 'Guide', 'Others'),
                          availability ENUM('yes', 'no'),
                          Room_id varchar(5),
                          identityDetails varchar(100),
                          FOREIGN KEY (Room_id) REFERENCES Room(Room_id) ON DELETE CASCADE ON UPDATE CASCADE

);




CREATE TABLE Task (
                      Service_id varchar(15) NOT NULL PRIMARY KEY,
                      Name varchar(25),
                      Description varchar(25),
                      Employee_id varchar(5),
                      FOREIGN KEY (Employee_id) REFERENCES Employee(Employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Arrange (
                         Tour_id varchar(5),
                         identityDetails varchar(100) NOT NULL,
                         PRIMARY KEY (Tour_id, identityDetails),
                         FOREIGN KEY (Tour_id) REFERENCES Tour(Tour_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (identityDetails) REFERENCES Guest(identityDetails) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE Vehicle (
                         Vehicle_id varchar(15) NOT NULL PRIMARY KEY,
                         No_of_seats int,
                         Status varchar(25),
                         Employee_id varchar(5),
                         FOREIGN KEY (Employee_id) REFERENCES Employee(Employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Booking (
                         Booking_id varchar(30) NOT NULL PRIMARY KEY,
                         Package_id varchar(5),
                         id varchar(100),
                         bookingDate date,
                         payId varchar(30),
                         Room_id varchar(5),
                         FOREIGN KEY (Package_id) REFERENCES Package(Package_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (id) REFERENCES Client(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (payId) REFERENCES payment(payId) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (Room_id) REFERENCES Room(Room_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reservation (
                             id varchar(50) NOT NULL,
                             guest_name varchar(100) NOT NULL,
                             check_in_date date NOT NULL,
                             check_out_date date NOT NULL,
                             room_type varchar(50) DEFAULT NULL,
                             num_guests int DEFAULT NULL,
                             Room_id varchar(5) DEFAULT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (Room_id) REFERENCES Room(Room_id)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE
);




