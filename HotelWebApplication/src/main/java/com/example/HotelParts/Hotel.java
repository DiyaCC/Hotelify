package com.example.HotelParts;

public class Hotel {

    private HotelChain hotelChain;
    private int hotelID;
    private String hotelName;
    private int hotelStreetNumber;
    private String hotelStreetName;
    private String hotelCity;
    private String hotelState;
    private String hotelZIP;
    private int numberOfRooms;
    private String hotelEmail;
    private int hotelStarRating;



    public Hotel( HotelChain hotelChain, int hotelID, String hotelName, int HotelStreetNumber,
                  String hotelStreetName, String hotelCity, String hotelState,
                  String hotelZIP, int numberOfRooms, String hotelEmail,
                  int hotelStarRating) {

        this.hotelChain = hotelChain;
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelStreetNumber = HotelStreetNumber;
        this.hotelStreetName = hotelStreetName;
        this.hotelCity = hotelCity;
        this.hotelState = hotelState;
        this.hotelZIP = hotelZIP;
        this.numberOfRooms = numberOfRooms;
        this.hotelEmail = hotelEmail;
        this.hotelStarRating = hotelStarRating; }




    public void setHotelChain(HotelChain hotelChain) { this.hotelChain = hotelChain; }
    public HotelChain getHotelChain() { return hotelChain; }

    public void setHotelID(int hotelID) { this.hotelID = hotelID; }
    public int getHotelID() { return hotelID; }

    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getHotelName() { return hotelName; }

    public void setStreetNumber(int hotelStreetNumber) { this.hotelStreetNumber = hotelStreetNumber; }
    public int getStreetNumber() { return hotelStreetNumber; }

    public void setStreetName(String hotelStreetName) { this.hotelStreetName = hotelStreetName; }
    public String getStreetName() { return hotelStreetName; }

    public void setCity(String hotelCity) { this.hotelCity = hotelCity; }
    public String getCity() { return hotelCity; }

    public void setState(String hotelState) { this.hotelState = hotelState; }
    public String getState() { return hotelState; }

    public void setZIP( String hotelZIP ) { this.hotelZIP = hotelZIP; }
    public String getZIP() { return hotelZIP; }

    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }
    public int getNumberOfRooms() { return numberOfRooms; }

    public void setEmail(String hotelEmail) { this.hotelEmail = hotelEmail; }
    public String getEmail() { return hotelEmail; }

    public void setStarRating(int hotelStarRating) {
        if (hotelStarRating < 0 || hotelStarRating > 5) {
            throw new IllegalArgumentException("Star Rating must be between 0 and 5"); }
        this.hotelStarRating = hotelStarRating;}

    public int getStarRating() { return hotelStarRating; }
}
