package com.gregbarasch.scholarshipmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

/**
 * Simple POJO that represents a Scholarship which students are able to apply for
 *
 * A scholarship has the ability to determine if it will accept a student or not, but does not maintain a list of students
 */
@Data
@AllArgsConstructor
public class Scholarship {

    private String name;
    private String description;
    private float price;
    private float minGpa;
    private boolean citizenshipRequired;
    private Set<ProgramType> acceptedProgramTypes;
    private Set<IncomeBracket> acceptedIncomeBrackets;

    /**
     *
     * @param student
     * @return true/false if the student qualifies for the scholarship
     */
    public boolean accepts(Student student) {

        // Check gpa
        if (student.getGpa() < minGpa) {
            return false;
        }

        // Check citizenship
        if (citizenshipRequired && !student.isCitizen()) {
            return false;
        }

        // Check program type
        if (!acceptedProgramTypes.contains(student.getProgramType())) {
            return false;
        }

        // Check income bracket
        if (!acceptedIncomeBrackets.contains(student.getIncomeBracket())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder()
                .append("Scholarship Name: ").append(name).append("\n")
                .append("Description: ").append(description).append("\n")
                .append("Price: $").append(price).append("\n")
                .append("Minimum GPA: ").append(minGpa).append("\n")
                .append("Citizenship Required: ").append(citizenshipRequired).append("\n");

        // Program types
        stringBuilder.append("Accepted Program Types: ");
        for (ProgramType pt : acceptedProgramTypes) {
            stringBuilder.append("<").append(pt).append("> ");
        }
        stringBuilder.append("\n");

        // Income brackets
        stringBuilder.append("Accepted Income Brackets: ");
        for (IncomeBracket ib : acceptedIncomeBrackets) {
            stringBuilder.append("<").append(ib).append("> ");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
