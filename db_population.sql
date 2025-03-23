-- Insert sample data into Hotel_Chain (5 well-known chains)
INSERT INTO Hotel_Chain (chain_name, num_hotels) VALUES
('Marriott', 8),
('Hilton', 8),
('Hyatt', 14),
('Sheraton', 18),
('Holiday Inn', 17);

-- Insert sample data into Central_Office (Each chain has a central office)
INSERT INTO Central_Office (chain_id, street_number, street_name, city, state, zip, contact_email, contact_phone) VALUES
(1, '1001', 'Marriott Dr', 'Bethesda', 'MD', '20817', 'contact@marriott.com', '+1-301-555-1001'),
(2, '2002', 'Hilton Ave', 'McLean', 'VA', '22102', 'contact@hilton.com', '+1-703-555-2002'),
(3, '3003', 'Hyatt Blvd', 'Chicago', 'IL', '60601', 'contact@hyatt.com', '+1-312-555-3003'),
(4, '4004', 'Sheraton Way', 'Stamford', 'CT', '06902', 'contact@sheraton.com', '+1-203-555-4004'),
(5, '5005', 'Holiday Rd', 'Atlanta', 'GA', '30303', 'contact@holidayinn.com', '+1-404-555-5005');

-- Insert sample data into Hotel (Each chain has at least 8 hotels across 14 locations)
INSERT INTO Hotel (chain_id, hotel_name, street_number, street_name, city, state, zip, contact_email, star_rating, num_rooms) VALUES
(1, 'Marriott Downtown', '101', 'Broadway', 'New York', 'NY', '10002', 'contact1@marriott.com', 5, 10),
(1, 'Marriott Beachfront', '202', 'Ocean Dr', 'Miami', 'FL', '33139', 'contact2@marriott.com', 4, 10),
(1, 'Marriott City Center', '303', 'Market St', 'San Francisco', 'CA', '94103', 'contact3@marriott.com', 3, 0),
(1, 'Marriott Airport', '404', 'Airport Rd', 'Dallas', 'TX', '75261', 'contact4@marriott.com', 4, 0),
(1, 'Marriott Metro', '505', '5th Ave', 'Seattle', 'WA', '98101', 'contact5@marriott.com', 5, 0),
(1, 'Marriott Lakeside', '606', 'Lakeview Rd', 'Chicago', 'IL', '60616', 'contact6@marriott.com', 4, 0),
(1, 'Marriott Grand', '707', 'Sunset Blvd', 'Los Angeles', 'CA', '90028', 'contact7@marriott.com', 3, 0),
(1, 'Marriott West End', '808', 'Main St', 'Boston', 'MA', '02108', 'contact8@marriott.com', 5, 0),

(2, 'Hilton Plaza', '111', 'Wall St', 'New York', 'NY', '10005','contact1@hilton.com', 5, 0),
(2, 'Hilton Resort', '222', 'Beach Ave', 'Miami', 'FL', '33140', 'contact2@hilton.com', 4, 0),
(2, 'Hilton Tower', '333', 'High St', 'Chicago', 'IL', '60611', 'contact3@hilton.com', 3, 0),
(2, 'Hilton Park', '444', 'Maple St', 'Dallas', 'TX', '75201', 'contact4@hilton.com', 4, 0),
(2, 'Hilton Executive', '555', 'Capitol Blvd', 'Washington', 'DC', '20001', 'contact5@hilton.com', 5, 0),
(2, 'Hilton Midtown', '666', '8th Ave', 'New York', 'NY', '10018', 'contact6@hilton.com', 4, 0),
(2, 'Hilton Riverside', '777', 'River Rd', 'New Orleans', 'LA', '70130', 'contact7@hilton.com', 3, 0),
(2, 'Hilton Bayview', '888', 'Bay St', 'San Francisco', 'CA', '94109', 'contact8@hilton.com', 5, 0);

-- Insert sample data into Phone_Hotel 
INSERT INTO Phone_Hotel (hotel_id, phone_number) VALUES
(1, '+1-212-555-0101'),
(2, '+1-305-555-0202'),
(3, '+1-415-555-0303'),
(4, '+1-214-555-0404'),
(5, '+1-206-555-0505');

-- Insert sample data into Room_Type (Different types of rooms)
INSERT INTO Room_Type (description) VALUES
('Standard Room'),
('Deluxe Room'),
('Suite'),
('Presidential Suite'),
('Penthouse');

-- Insert sample data into Room (Each hotel has at least 5 rooms with different capacities)
INSERT INTO Room (hotel_id, room_number, room_type_id, price, capacity, sea_view, mountain_view) VALUES
(1, 101, 2, 150, 2, FALSE, FALSE),
(1, 102, 4, 200, 2, FALSE, FALSE),
(1, 103, 3, 175, 2, FALSE, TRUE),
(1, 104, 2, 150, 4, FALSE, TRUE),
(1, 105, 5, 250, 4, FALSE, TRUE),

(1, 201, 2, 180, 2, FALSE, FALSE),
(1, 202, 4, 220, 2, FALSE, FALSE),
(1, 203, 3, 190, 2, TRUE, TRUE),
(1, 204, 2, 180, 2, TRUE, TRUE),
(1, 205, 5, 270, 4, TRUE, TRUE),

(2, 301, 2, 140, 2, TRUE, FALSE),
(2, 302, 4, 190, 2, FALSE, FALSE),
(2, 303, 3, 160, 2, FALSE, FALSE),
(2, 304, 2, 140, 4, TRUE, FALSE),
(2, 305, 5, 260, 4, FALSE, FALSE),

(2, 401, 2, 130, 2, FALSE, FALSE),
(2, 402, 4, 180, 2, FALSE, FALSE),
(2, 403, 3, 150, 4, TRUE, FALSE),
(2, 404, 2, 130, 4, TRUE, FALSE),
(2, 405, 5, 250, 4, FALSE, FALSE);

-- Insert sample data into Damage (Room damages reported)
INSERT INTO Damage (hotel_id, room_id, description, reported_date, resolved, date_resolved) VALUES
(1, 1, 'Broken air conditioning', '2025-02-20', FALSE, NULL),
(2, 11, 'Leaking faucet', '2025-02-15', TRUE, '2025-02-18'),
(2, 12, 'Cracked window', '2025-03-01', FALSE, NULL);

-- Insert sample data into Extension (Different room extensions)
INSERT INTO Extension (type, description) VALUES
('Terrace', 'Extended ablcony terrace'),
('Bunkie', 'Addition bunkie bed'),
('Kitchenette', 'Small kitchen with appliances');

-- Insert sample data into Is_Extendable (Link room types to extensions)
INSERT INTO Is_Extendable (room_type_id, extension_id) VALUES
(2, 1),
(3, 2),
(4, 3);

-- Insert sample data into Amenity (Hotel amenities)
INSERT INTO Amenity (type, description) VALUES
('WiFi', NULL),
('TV', NULL),
('Air Conditioning', NULL),
('Fridge', NULL);

-- Insert sample data into Room_Amenity (Link amenities to room types)
INSERT INTO Room_Amenity (room_type_id, amenity_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4);

-- Insert sample data into Person (Basic person details)
INSERT INTO Person (SSN, first_name, last_name, street_number, street_name, city, state, zip) VALUES
(123456789, 'Alice', 'Johnson', '123', 'Main St', 'New York', 'NY', '10001'),
(234567890, 'Bob', 'Smith', '456', '2nd Ave', 'Los Angeles', 'CA', '90001'),
(345678901, 'Charlie', 'Brown', '789', 'Market St', 'Chicago', 'IL', '60601'),
(456789012, 'David', 'Lee', '101', 'Broadway', 'Miami', 'FL', '33139'),
(567890123, 'Emily', 'Davis', '202', 'Park Ave', 'San Francisco', 'CA', '94103');

-- Insert sample data into Customer (Customers linked to persons)
INSERT INTO Customer (SSN, registration_date) VALUES
(123456789, '2025-01-15'),
(234567890, '2025-02-10'),
(345678901, '2025-02-25');

-- Insert sample data into Employee (Employees assigned to hotels)
INSERT INTO Employee (SSN, hotel_id, salary) VALUES
(456789012, 1, 50000),
(567890123, 2, 55000);

-- Insert sample data into Employee_Role (Roles for employees)
INSERT INTO Employee_Role (employee_id, role) VALUES
(1, 'Receptionist'),
(2, 'Manager');

-- Insert sample data into Booking (Reservations made by customers)
INSERT INTO Booking (customer_id, hotel_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) VALUES
(1, 1, 2, '2025-03-01 12:00:00', '2025-03-10', '2025-03-15', FALSE),
(2, 2, 3, '2025-03-02 14:30:00', '2025-03-12', '2025-03-18', FALSE),
(3, 3, 4, '2025-03-03 16:00:00', '2025-03-14', '2025-03-20', TRUE);

-- Insert sample data into Renting (Actual room rentals)
INSERT INTO Renting (employee_id, booking_id, customer_id, room_type_id, hotel_id, room_id, checkin_date, checkout_date, payment_method, payment_date, cancelled) VALUES
(1, 1, 1, 2, 1, 1, '2025-03-10 15:00:00', '2025-03-15 11:00:00', 'Credit Card', '2025-03-10 15:05:00', FALSE),
(2, 2, 2, 3, 2, 11, '2025-03-12 14:00:00', '2025-03-18 10:00:00', 'PayPal', '2025-03-12 14:10:00', FALSE);

-- Insert sample data into Archive_Booking (Historical bookings)
INSERT INTO Archive_Booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled) VALUES
(3, 3, 4, '2025-03-03 16:00:00', '2025-03-14', '2025-03-20', TRUE);

-- Insert sample data into Archive_Renting (Historical rentals)
INSERT INTO Archive_Renting (renting_id, employee_id, booking_id, customer_id, room_type_id, room_id, checkin_date, checkout_date, cancelled) VALUES
(1, 1, 1, 1, 2, 1, '2025-03-10 15:00:00', '2025-03-15 11:00:00', FALSE);
