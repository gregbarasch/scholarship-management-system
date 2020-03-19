package com.gregbarasch.scholarshipmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple POJO representing a student a student
 */
@Data
@AllArgsConstructor
public class Student {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;
    private boolean citizen;
    private ProgramType programType;
    private IncomeBracket incomeBracket;
    private float gpa;

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "\nAddress: " + "\n" + address + "\n\n" +
                "Citizenship: " + citizen + "\n" +
                "Program Type: " + programType + "\n" +
                "Income Bracket: " + incomeBracket + "\n" +
                "GPA: " + gpa + "\n";
    }
}
