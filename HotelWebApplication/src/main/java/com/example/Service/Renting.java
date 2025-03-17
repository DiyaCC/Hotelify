package Service;

import java.sql.Timestamp;

public class Renting {

    private int employee_id;
    private int booking_id;
    private int customer_id;
    private int room_type_id;
    private int hotel_id;
    private int room_id;
    private Timestamp checkin_date;
    private Timestamp checkout_date;
    private String payment_method;
    private boolean cancelled;

    public Renting(int employee_id, int booking_id, int customer_id, int room_type_id, int hotel_id, int room_id, Timestamp checkin_date, Timestamp checkout_date, String payment_method, boolean cancelled) {
        this.employee_id = employee_id;
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.room_type_id = room_type_id;
        this.hotel_id = hotel_id;
        this.room_id = room_id;
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.payment_method = payment_method;
        this.cancelled = cancelled;
    }

    public void setEmployee_id(int employee_id) { this.employee_id = employee_id; }
    public int getEmployee_id() { return this.employee_id; }

    public void setBooking_id(int booking_id) { this.booking_id = booking_id; }
    public int getBooking_id() { return this.booking_id; }

    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }
    public int getCustomer_id() { return this.customer_id; }

    public void setRoom_type_id(int room_type_id) { this.room_type_id = room_type_id; }
    public int getRoom_type_id() { return this.room_type_id; }

    public void setHotel_id(int hotel_id) { this.hotel_id = hotel_id; }
    public int getHotel_id() { return this.hotel_id; }

    public void setRoom_id(int room_id) { this.room_id = room_id; }
    public int getRoom_id() { return this.room_id; }

    public void setCheckin_date(Timestamp checkin_date) { this.checkin_date = checkin_date; }
    public Timestamp getCheckin_date() { return this.checkin_date; }

    public void setCheckout_date(Timestamp checkout_date) { this.checkout_date = checkout_date; }
    public Timestamp getCheckout_date() { return this.checkout_date; }

    public void setPayment_method(String payment_method) { this.payment_method = payment_method; }
    public String getPayment_method() { return this.payment_method; }

    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
    public boolean getCancelled() { return this.cancelled; }


}
