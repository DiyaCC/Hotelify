---------- Prevent invalid booking dates ----------
CREATE OR REPLACE FUNCTION prevent_invalid_booking_dates() 
RETURNS TRIGGER AS $$ 
    BEGIN 
        IF NEW.checkin_date < CURRENT_DATE OR NEW.checkout_date < CURRENT_DATE THEN 
            RAISE EXCEPTION 'Booking dates are invalid.'; 
        END IF; 
    RETURN NEW; 
END; 
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_booking_dates 
    BEFORE UPDATE ON booking 
        FOR EACH ROW 
        EXECUTE FUNCTION prevent_invalid_booking_dates();


---------- Archive and delete trigger on renting ----------
CREATE OR REPLACE FUNCTION archive_and_cleanup_rental()
RETURNS TRIGGER AS $$
    BEGIN
        INSERT INTO archive_renting (renting_id, employee_id, booking_id, customer_id, room_type_id, room_id, checkin_date, checkout_date, cancelled)
        SELECT OLD.renting_id, OLD.employee_id, OLD.booking_id, OLD.customer_id, OLD.room_type_id, OLD.room_id, OLD.checkin_date, OLD.checkout_date, OLD.cancelled;
        RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_archive_rental_and_delete_booking
    BEFORE DELETE ON renting
        FOR EACH ROW
        EXECUTE FUNCTION archive_and_cleanup_rental();


---------- Archive trigger on booking ----------
CREATE OR REPLACE FUNCTION archive_booking_on_delete()
RETURNS TRIGGER AS $$
    BEGIN
        INSERT INTO archive_booking (booking_id, customer_id, room_type_id, confirmation_date, checkin_date, checkout_date, cancelled)
        SELECT OLD.booking_id, OLD.customer_id, OLD.room_type_id, OLD.confirmation_date, OLD.checkin_date, OLD.checkout_date, OLD.cancelled;
        RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_archive_booking_on_delete
    BEFORE DELETE ON booking
        FOR EACH ROW
        EXECUTE FUNCTION archive_booking_on_delete();














