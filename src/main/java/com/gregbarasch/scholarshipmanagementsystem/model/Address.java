package com.gregbarasch.scholarshipmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A simple POJO representation of an address
 */
@Data
@AllArgsConstructor
public class Address {

    private String street;
    private String city;
    private int zip;
    private String state;

    @Override
    public String toString() {
        return street + "\n" +
                city + " " + state + " " + zip;
    }
}
