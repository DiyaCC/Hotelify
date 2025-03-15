package com.example.HotelParts;

public class Room {

    private Hotel hotel;
    private int roomNumber;
    private float price;
    private int capacity;
    private boolean seaView;
    private boolean mountainView;
    private int typeID;

    public Room(Hotel hotel, int roomNumber, float price, int capacity, int typeID) {
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.price = price;
        this.capacity = capacity;
        this.typeID = typeID;
    }

    public void setHotel(Hotel hotel) { this.hotel = hotel; }
    public Hotel getHotel() { return hotel; }

    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public int getRoomNumber() { return roomNumber; }

    public void setPrice(float price) { this.price = price; }
    public float getPrice() { return price; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getCapacity() { return capacity; }

    public void setSeaView(boolean isSeaView) { this.seaView = isSeaView; }
    public boolean isSeaView() { return seaView; }

    public void setMountainView(boolean isMountainView) {this.mountainView = isMountainView;}
    public boolean isMountainView() { return mountainView; }

    public void setTypeID(int typeID) { this.typeID = typeID; }
    public int getTypeID() { return typeID; }




}
