package com.example.Users;

public class Customer extends Person{

    private int CustomerID;

    public Customer(int CustomerID,
                    int SSN, String firstName,
                    String lastName,
                    int streetNumber,
                    String streetName,
                    String city,
                    String state,
                    String ZIP) {

        super(SSN, firstName, lastName, streetNumber, streetName, city, state, ZIP);
        this.CustomerID = CustomerID; }


    public void setCustomerID(int CustomerID) { this.CustomerID = CustomerID; }
    public int getCustomerID() { return CustomerID; }
}
