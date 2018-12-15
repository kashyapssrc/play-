package com.objectfrontier.training.java.services.pojo;

import java.sql.Timestamp;

public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String birth_date;
    private Timestamp created_date;
    private Address address;

    public Person() {
        super();
    }

    public Person(long id,
                  String firstName,
                  String lastName,
                  String email,
                  String birth_date,
                  Address address) {

        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth_date = birth_date;
        this.address = address;
    }

    public Person(String firstName,
                  String lastName,
                  String email,
                  String birth_date,
                  Address address) {

        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth_date = birth_date;
        this.address = address;
    }

    public Person(long id, String firstName, String lastName, String email, String birth_date) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth_date = birth_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", birth_date=" + birth_date + ", address=" + address + "]";
    }



}

