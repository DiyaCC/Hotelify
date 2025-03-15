package com.example.HotelParts;

public class CentralOffice {

    private int OfficeID;
    private String OfficeName;
    private int OfficeStreetNumber;
    private String OfficeStreetName;
    private String OfficeCity;
    private String OfficeState;
    private String OfficeZIP;
    private String contactEmail;
    private int OfficePhoneNumber;


    public CentralOffice(int ID, String officeName, int streetNumber, String streetName, String city, String state, String zip, String email, int phoneNumber) {
        this.OfficeID = ID;
        this.OfficeName = officeName;
        this.OfficeStreetNumber = streetNumber;
        this.OfficeStreetName = streetName;
        this.OfficeCity = city;
        this.OfficeState = state;
        this.OfficeZIP = zip;
        this.contactEmail = email;
        OfficePhoneNumber = phoneNumber;
    }

    public void setOfficeID(int officeID) { this.OfficeID = officeID; }
    public int getOfficeID() { return OfficeID; }

    public void setName(String officeName) { this.OfficeName = officeName; }
    public String getName() { return OfficeName; }

    public void setStreetNumber(int streetNumber) { this.OfficeStreetNumber = streetNumber; }
    public int getStreetNumber() { return OfficeStreetNumber; }

    public void setStreetName(String streetName) { this.OfficeStreetName = streetName; }
    public String getStreetName() { return OfficeStreetName; }

    public void setCity(String city) { this.OfficeCity = city; }
    public String getCity() { return OfficeCity; }

    public void setState(String state) { this.OfficeState = state; }
    public String getState() { return OfficeState; }

    public void setZIP(String zip) { this.OfficeZIP = zip; }
    public String getZIP() { return OfficeZIP; }

    public void setContactEmail(String email) { this.contactEmail = email; }
    public String getContactEmail() { return contactEmail; }

    public void setPhoneNumber(int phoneNumber) { this.OfficePhoneNumber = phoneNumber; }
    public int getPhoneNumber() { return OfficePhoneNumber; }


}
