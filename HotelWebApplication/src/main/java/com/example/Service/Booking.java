package Service;

public class Booking {

    private int customer_id;
    private int hotel_id;
    private int room_type_id;
    private Timestamp confirmation_date;
    private Timestamp checkin_date;
    private Timestamp checkout_date;
    private boolean cancelled;

    public int getCustomer_id() { return this.customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public int getHotel_id() { return this.hotel_id; }
    public void setHotel_id(int hotel_id) { this.hotel_id = hotel_id; }

    public int getRoom_type_id() { return this.room_type_id; }
    public void setRoom_type_id(int room_type_id) { this.room_type_id = room_type_id; }

    public Timestamp getConfirmation_date() { return this.confirmation_date; }
    public void setConfirmation_date(Timestamp confirmation_date) { this.confirmation_date = confirmation_date; }

    public Timestamp getCheckin_date() { return this.checkin_date; }
    public void setCheckin_date(Timestamp checkin_date) { this.checkin_date = checkin_date; }

    public Timestamp getCheckout_date() { return this.checkout_date; }
    public void setCheckout_date(Timestamp checkout_date) { this.checkout_date = checkout_date; }

    public boolean getCancelled() { return this.cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
