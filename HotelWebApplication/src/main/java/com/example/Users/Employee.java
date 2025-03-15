package com.example.Users;
import java.util.*;

public class Employee extends Person {

    private int employeeID;
    private ArrayList<String> employeeRoles; // make an enum for employee roles?
    private float salary;
    private int hotelID; // do we want to make this an enum?

    public Employee(int employeeID,
                    float salary,
                    int hotelID,
                    int SSN,
                    String firstName,
                    String lastName,
                    int streetNumber,
                    String streetName,
                    String city,
                    String state,
                    String ZIP)

    { super(SSN, firstName, lastName, streetNumber, streetName, city, state, ZIP);
    this.employeeID = employeeID;
    this.salary = salary;
    this.hotelID = hotelID;
    employeeRoles = new ArrayList<>();}


    public void setEmployeeId(int employeeId) { this.employeeID = employeeId; }
    public int getEmployeeId() { return this.employeeID; }

    public void addEmployeeRole(String employeeRole) { this.employeeRoles.add(employeeRole); }
    public ArrayList<String> getEmployeeRole() { return this.employeeRoles; }

    public void setSalary(float salary) { this.salary = salary; }
    public float getSalary() { return this.salary; }

    public void setHotelID(int hotelID) { this.hotelID = hotelID; }
    public int getHotelID() { return this.hotelID; }

}
