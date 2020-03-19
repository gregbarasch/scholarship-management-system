package com.gregbarasch.scholarshipmanagementsystem.database.entity;

import com.gregbarasch.scholarshipmanagementsystem.model.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@RequiredArgsConstructor
public class AddressEntity {

    private final long id;
    private final String street;
    private final String city;
    private final int zip;
    private final String state;

    public AddressEntity(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zip = address.getZip();
        this.state = address.getState();
        this.id = getId(address);
    }

    // FIXME our interface says "long" but for our POC an int is fine, so allowing truncation
    public static long getId(Address address) {
        return new HashCodeBuilder()
                .append(address.getStreet())
                .append(address.getCity())
                .append(address.getZip())
                .append(address.getState())
                .build();
    }
}
