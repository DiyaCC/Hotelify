package com.example.HotelParts;
import java.util.*;

public class HotelChain {

    private int chainID;
    private String chainName;
    private int numberOfHotels;
    private ArrayList<Hotel> hotels;
    private int managerID;

    public HotelChain(int chainID, String chainName, int numberOfHotels, int managerID) {
        this.chainID = chainID;
        this.chainName = chainName;
        this.numberOfHotels = numberOfHotels;
        this.managerID = managerID;
        this.hotels = new ArrayList<>();

    }

    public void setChainID(int chainID) { this.chainID = chainID; }
    public int getChainID() { return chainID; }

    public void setChainName(String chainName) { this.chainName = chainName; }
    public String getChainName() { return chainName; }

    public void setNumberOfHotels(int num) { this.numberOfHotels = num; }
    public int getNumberOfHotels() { return numberOfHotels; }

    public void setManagerID(int managerID) { this.managerID = managerID; }
    public int getManagerID() { return managerID; }

    public void addHotel(Hotel hotel) { this.hotels.add(hotel); }
    public ArrayList<Hotel> getHotels() { return hotels; }
}
