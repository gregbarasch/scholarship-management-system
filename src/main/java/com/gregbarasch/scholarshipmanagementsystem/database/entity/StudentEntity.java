package com.gregbarasch.scholarshipmanagementsystem.database.entity;

import com.gregbarasch.scholarshipmanagementsystem.model.Address;
import com.gregbarasch.scholarshipmanagementsystem.model.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@RequiredArgsConstructor
public class StudentEntity {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final long addressId;
    private final boolean citizen;
    private final String programType;
    private final String incomeBracket;
    private final float gpa;

    public StudentEntity(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.emailAddress = student.getEmailAddress();

        Address address = student.getAddress();
        this.addressId = AddressEntity.getId(address);

        this.citizen = student.isCitizen();
        this.programType = student.getProgramType().name();
        this.incomeBracket = student.getIncomeBracket().name();
        this.gpa = student.getGpa();

        // FIXME our interface says "long" but for our POC an int is fine, so allowing truncation
        this.id = new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .append(emailAddress)
                .toHashCode();
    }
}
