package com.example.tanim.smsallcontact;

/**
 * Created by tanim on 9/4/2017.
 */

public class PhoneBook {
    private String name;
    private String phone;

    // Constructor for the Phonebook class
    public PhoneBook(String name, String phone) {
        super();
        this.name = name;
        this.phone = phone;
    }

    // Getter and setter methods for all the fields.
    // Though you would not be using the setters for this example,
    // it might be useful later.
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
