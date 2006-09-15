package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.aggregations.ComposedOf;

/**
 * 
 */
public class Customer extends ActiveRecord {

    @ComposedOf(mapping = {"address_street", "street", "address_city", "city", "address_country", "country"},
                allowNull = true)
    private Address address;

    @ComposedOf(mapping = {"balance", "amount"})
    private Money balance;

    @ComposedOf(allowNull = true)
    private GpsLocation gpsLocation;

    private String name;
    private String addressStreet;
    private String addressCity;
    private String addressCountry;

    // accessor methods

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void GpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

}
