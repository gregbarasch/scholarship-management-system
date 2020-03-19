package com.gregbarasch.scholarshipmanagementsystem.service;

import com.gregbarasch.scholarshipmanagementsystem.database.dao.ScholarshipDao;
import com.gregbarasch.scholarshipmanagementsystem.database.entity.ScholarshipEntity;
import com.gregbarasch.scholarshipmanagementsystem.io.ScholarshipDisplayHandler;
import com.gregbarasch.scholarshipmanagementsystem.model.IncomeBracket;
import com.gregbarasch.scholarshipmanagementsystem.model.ProgramType;
import com.gregbarasch.scholarshipmanagementsystem.model.Scholarship;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScholarshipService {

    private final ScholarshipDao scholarshipDao;
    private final ScholarshipDisplayHandler scholarshipDisplayHandler;

    @Autowired
    public ScholarshipService(ScholarshipDao scholarshipDao, ScholarshipDisplayHandler scholarshipDisplayHandler) {
        this.scholarshipDao = scholarshipDao;
        this.scholarshipDisplayHandler = scholarshipDisplayHandler;
    }

    public boolean addScholarship(Scholarship s) {
        return scholarshipDao.insert(new ScholarshipEntity(s));
    }

    public boolean removeScholarship(Scholarship s) {
        return scholarshipDao.delete(new ScholarshipEntity(s));
    }

    public Collection<Scholarship> getScholarships() {
        Collection<Scholarship> scholarships = new HashSet<>();
        Collection<ScholarshipEntity> scholarshipEntities = scholarshipDao.getAll();

        if (!scholarshipEntities.isEmpty()) {

            // loop over the entities and create Scholarship objects
            for (ScholarshipEntity scholarshipEntity : scholarshipEntities) {

                // accepted ProgramTypes/IncomeBrackets
                Set<ProgramType> acceptedProgramTypes = getAcceptedProgramTypes(scholarshipEntity);
                Set<IncomeBracket> acceptedIncomeBrackets = getAcceptedIncomeBrackets(scholarshipEntity);

                // create scholarship
                if (!acceptedProgramTypes.isEmpty() && !acceptedIncomeBrackets.isEmpty()) {
                    scholarships.add(new Scholarship(
                            scholarshipEntity.getName(),
                            scholarshipEntity.getDescription(),
                            scholarshipEntity.getPrice(),
                            scholarshipEntity.getMinGpa(),
                            scholarshipEntity.isCitizenshipRequired(),
                            acceptedProgramTypes,
                            acceptedIncomeBrackets
                    ));
                } else {
                    log.warn("We were unable to retrieve scholarship id: {} because acceptedProgramTypes and/or acceptedIncomeBrackets registered as null", scholarshipEntity.getId());
                }
            }
        }

        return scholarships;
    }

    public void displayAllScholarships() {
        scholarshipDisplayHandler.display(getScholarships());
    }

    private Set<ProgramType> getAcceptedProgramTypes(ScholarshipEntity scholarshipEntity) {
        String acceptedProgramTypesString = scholarshipEntity.getAcceptedProgramTypes();

        if (StringUtils.isNotBlank(acceptedProgramTypesString)) {
            return Arrays.stream(acceptedProgramTypesString.split(","))
                    .map(ProgramType::fromString)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }

    private Set<IncomeBracket> getAcceptedIncomeBrackets(ScholarshipEntity scholarshipEntity) {
        String acceptedIncomeBracketsString = scholarshipEntity.getAcceptedIncomeBrackets();

        if (StringUtils.isNotBlank(acceptedIncomeBracketsString)) {
            return Arrays.stream(acceptedIncomeBracketsString.split(","))
                    .map(IncomeBracket::fromString)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }
}
