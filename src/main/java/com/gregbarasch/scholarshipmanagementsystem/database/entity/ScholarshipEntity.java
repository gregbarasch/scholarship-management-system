package com.gregbarasch.scholarshipmanagementsystem.database.entity;

import com.gregbarasch.scholarshipmanagementsystem.model.IncomeBracket;
import com.gregbarasch.scholarshipmanagementsystem.model.ProgramType;
import com.gregbarasch.scholarshipmanagementsystem.model.Scholarship;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ScholarshipEntity {

    private final long id;
    private final String name;
    private final String description;
    private final float price;
    private final float minGpa;
    private final boolean citizenshipRequired;
    // csv representation of the sets
    private final String acceptedProgramTypes;
    private final String acceptedIncomeBrackets;

    public ScholarshipEntity(Scholarship scholarship) {
        this.name = scholarship.getName();
        this.description = scholarship.getDescription();
        this.price = scholarship.getPrice();
        this.minGpa = scholarship.getMinGpa();
        this.citizenshipRequired = scholarship.isCitizenshipRequired();

        Collection<IncomeBracket> incomeBrackets = scholarship.getAcceptedIncomeBrackets();
        this.acceptedIncomeBrackets = incomeBrackets.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        Collection<ProgramType> programTypes = scholarship.getAcceptedProgramTypes();
        this.acceptedProgramTypes = programTypes.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        // FIXME our interface says "long" but for our POC an int is fine, so allowing truncation
        this.id = name.hashCode();
    }
}
