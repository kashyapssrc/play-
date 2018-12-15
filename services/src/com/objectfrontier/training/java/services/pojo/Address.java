package com.objectfrontier.training.java.services.pojo;

public class Address {

    private long id;
    private String street;
    private String city;
    private int postal_code;


    public Address() {
        super();
    }

    public Address(long id, String street, String city, int postal_code) {
        super();
        this.id = id;
        this.street = street;
        this.city = city;
        this.postal_code = postal_code;
    }

    public Address(String street, String city, int postal_code) {
        super();
        this.street = street;
        this.city = city;
        this.postal_code = postal_code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", street=" + street + ", city=" + city + ", postal_code=" + postal_code
                + "]";
    }

}

