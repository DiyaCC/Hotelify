package com.example.Users;

public class Person {

    private int SSN;
    private String firstName;
    private String lastName;
    private int streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String ZIP;

    public Person(int SSN, String firstName, String lastName, int streetNumber, String streetName, String city, String state, String ZIP) {
        this.SSN = SSN;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.ZIP = ZIP;
    }

    public void setSSN(int SSN) { this.SSN = SSN; }
    public int getSSN() { return SSN; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getLastName() { return lastName; }

    public void setStreetNumber(int streetNumber) { this.streetNumber = streetNumber; }
    public int getStreetNumber() { return streetNumber; }

    public void setStreetName(String streetName) { this.streetName = streetName; }
    public String getStreetName() { return streetName; }

    public void setCity(String city) { this.city = city; }
    public String getCity() { return city; }

    public void setState(String state) { this.state = state; }
    public String getState() { return state; }

    public void setZIP(String ZIP) { this.ZIP = ZIP; }
    public String getZIP() { return ZIP; }


}
