package com.aetrion.activerecord.models;

/**
 * 
 */
public class Address {
    private String street;
    private String city;
    private String country;

    // accessor methods

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // other methods
    public boolean isCloseTo(Address otherAddress) {
        return city.equals(otherAddress.getCity()) && country.equals(otherAddress.country);
    }

    public boolean equals(Object otherAddress) {
        if (otherAddress instanceof Address) {
            Address other = (Address) otherAddress;
            return street.equals(other.getStreet()) && city.equals(other.getCity()) && country.equals(other.getCountry());
        }
        return false;
    }
}