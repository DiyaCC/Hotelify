-- Create the database (if it doesn't already exist)
CREATE DATABASE hotels_db;

-- Connect to the database
\c hotels_db;

-- Create table: Hotel_Chain
CREATE TABLE Hotel_Chain (
    chain_id SERIAL,
    --
    chain_name VARCHAR(50) NOT NULL UNIQUE,
    num_hotels INT CHECK (num_hotels >= 0),
    --
    PRIMARY KEY (chain_id)
);

-- Create table: Central_Office
CREATE TABLE Central_Office (
    office_id SERIAL,
    --
    chain_id INT,
    street_number VARCHAR(10),
    street_name VARCHAR(50),
    city VARCHAR(50),
    state CHAR(2),
    zip CHAR(6),
    contact_email VARCHAR(50) UNIQUE NOT NULL CHECK (contact_email LIKE '%@%.%'),
    contact_phone VARCHAR(15) UNIQUE NOT NULL CHECK (contact_phone ~ '^[0-9+-]+$'),
    --
    PRIMARY KEY (office_id),
    FOREIGN KEY (chain_id) REFERENCES Hotel_Chain(chain_id) ON DELETE CASCADE
);

-- Create table: Hotel
CREATE TABLE Hotel (
    hotel_id SERIAL,
    --
    chain_id INT,
    hotel_name VARCHAR(50) NOT NULL,
    street_number VARCHAR(10),
    street_name VARCHAR(50),
    city VARCHAR(50),
    state CHAR(2),
    zip CHAR(6),
    contact_email VARCHAR(255) UNIQUE NOT NULL CHECK (contact_email LIKE '%@%.%'),
    star_rating INT CHECK (star_rating BETWEEN 1 AND 5),
    num_rooms INT CHECK (num_rooms >= 0),
    --
    PRIMARY KEY (hotel_id),
    FOREIGN KEY (chain_id) REFERENCES Hotel_Chain(chain_id) ON DELETE CASCADE
);

-- Create table: Phone_Hotel
CREATE TABLE Phone_Hotel (   
    hotel_id INT,
    phone_number VARCHAR(20) CHECK (phone_number ~ '^[0-9+-]+$'),
    --
    PRIMARY KEY (hotel_id, phone_number), 
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE
);

-- Create table: Room_Type
CREATE TABLE Room_Type (
    room_type_id SERIAL,
    --
    description TEXT,
    --
    PRIMARY KEY (room_type_id)    
);

-- Create table: Room
CREATE TABLE Room (
    hotel_id INT,   
    room_id SERIAL,
    --
    room_number INT NOT NULL CHECK (room_number > 1),
    room_type_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    capacity INT NOT NULL CHECK (capacity BETWEEN 1 AND 5),
    sea_view BOOLEAN NOT NULL DEFAULT FALSE,
    mountain_view BOOLEAN NOT NULL DEFAULT FALSE,
    --
    PRIMARY KEY (hotel_id, room_id),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE,
    FOREIGN KEY (room_type_id) REFERENCES Room_Type(room_type_id)
);

-- Create table: Damage
CREATE TABLE Damage (
    damage_id SERIAL,
    --
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    description TEXT NOT NULL,
    reported_date DATE NOT NULL DEFAULT CURRENT_DATE,
    resolved BOOLEAN NOT NULL DEFAULT FALSE,
    date_resolved DATE,
    --
    PRIMARY KEY (damage_id),
    FOREIGN KEY (hotel_id, room_id) REFERENCES Room(hotel_id, room_id) ON DELETE CASCADE
);

-- Create table: Extension
CREATE TABLE Extension (
    extension_id SERIAL,
    --
    type VARCHAR(50) NOT NULL,
    description TEXT,
    --
    PRIMARY KEY (extension_id)    
);

-- Create table: Is_Extendable
CREATE TABLE Is_Extendable (
    room_type_id INT NOT NULL,
    extension_id INT NOT NULL,
    --
    PRIMARY KEY (room_type_id, extension_id),
    FOREIGN KEY (room_type_id) REFERENCES Room_Type(room_type_id) ON DELETE CASCADE,
    FOREIGN KEY (extension_id) REFERENCES Extension(extension_id) ON DELETE CASCADE
);

-- Create table: Amenity
CREATE TABLE Amenity (
    amenity_id SERIAL,
    --
    type VARCHAR(50) NOT NULL,
    description TEXT,
    --
    PRIMARY KEY (amenity_id)
);

-- Create table: Room_Amenity
CREATE TABLE Room_Amenity (
    room_type_id INT NOT NULL,
    amenity_id INT NOT NULL,
    --
    PRIMARY KEY (room_type_id, amenity_id),
    FOREIGN KEY (room_type_id) REFERENCES Room_Type(room_type_id) ON DELETE CASCADE,
    FOREIGN KEY (amenity_id) REFERENCES Amenity(amenity_id) ON DELETE CASCADE
);

-- Create table: Person
CREATE TABLE Person (
    SSN INT,
    --
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    street_number VARCHAR(10),
    street_name VARCHAR(50),
    city VARCHAR(50),
    state CHAR(2),
    zip CHAR(6),
    --
    PRIMARY KEY (SSN)
);

-- Create table: Customer
CREATE TABLE Customer (
    customer_id SERIAL,
    --
    SSN INT NOT NULL,
    registration_date DATE DEFAULT CURRENT_DATE,
    --
    PRIMARY KEY (customer_id),
    FOREIGN KEY (SSN) REFERENCES Person(SSN) ON DELETE CASCADE
);

-- Create table: Employee
CREATE TABLE Employee (
    employee_id SERIAL,
    --
    SSN INT NOT NULL,
    hotel_id INT NOT NULL,
    salary DECIMAL(7,2) check (salary > 0 ),
    --
    PRIMARY KEY (employee_id),
    FOREIGN KEY (SSN) REFERENCES Person(SSN) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE
);

-- Create table: Employee_Role
CREATE TABLE Employee_Role (
    employee_id INT,
    role VARCHAR(50) NOT NULL,
    --
    PRIMARY KEY (employee_id, role),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

-- Create table: Booking
CREATE TABLE Booking (
    booking_id SERIAL,
    --
    customer_id INT NOT NULL,
    hotel_id INT NOT NULL,
    room_type_id INT NOT NULL,
    confirmation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    checkin_date DATE NOT NULL CHECK (checkin_date >= CURRENT_DATE),
    checkout_date DATE NOT NULL CHECK (checkout_date > checkin_date),
    cancelled BOOLEAN NOT NULL DEFAULT FALSE,
    --
    PRIMARY KEY (booking_id),
    FOREIGN KEY (hotel_id) REFERENCES Hotel(hotel_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (room_type_id) REFERENCES Room_Type(room_type_id) ON DELETE CASCADE
);

-- Create table: Renting
CREATE TABLE Renting (
    renting_id SERIAL,
    --
    employee_id INT NOT NULL,
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    room_type_id INT NOT NULL,
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    checkin_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    checkout_date TIMESTAMP NOT NULL,
    payment_method VARCHAR(50),
    payment_date TIMESTAMP,
    cancelled BOOLEAN NOT NULL DEFAULT FALSE,
    --
    PRIMARY KEY(renting_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE SET NULL,
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (room_type_id) REFERENCES Room_Type(room_type_id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id, room_id) REFERENCES Room(hotel_id, room_id) ON DELETE CASCADE
);

-- Create table: Archive_Booking
CREATE TABLE Archive_Booking (
    booking_id INT,
    --
    customer_id INT NOT NULL,
    room_type_id INT NOT NULL,
    confirmation_date TIMESTAMP NOT NULL,
    checkin_date DATE NOT NULL,
    checkout_date DATE NOT NULL,
    cancelled BOOLEAN NOT NULL,
    --
    PRIMARY KEY (booking_id)
);

-- Create table: Archive_Renting
CREATE TABLE Archive_Renting (
    renting_id INT,
    --
    employee_id INT NOT NULL,
    booking_id INT NOT NULL,
    customer_id INT NOT NULL,
    room_type_id INT NOT NULL,
    room_id INT NOT NULL,
    checkin_date TIMESTAMP NOT NULL,
    checkout_date TIMESTAMP NOT NULL,
    cancelled BOOLEAN NOT NULL,
    --
    PRIMARY KEY(renting_id)
);

-- Index for booking availability lookups
CREATE INDEX index_booking_hotel_room_dates
ON booking(hotel_id, room_type_id, checkin_date, checkout_date);

-- Index for renting overlap date checks
CREATE INDEX index_renting_room_dates
ON renting(room_id, checkin_date, checkout_date);

-- Index for room availability by hotel and type
CREATE INDEX index_room_hotel_type
ON room(hotel_id, room_type_id);

-- Indexes for customer-person join via SSN
CREATE INDEX index_customer_ssn ON customer(ssn);
CREATE INDEX index_person_ssn ON person(ssn);

