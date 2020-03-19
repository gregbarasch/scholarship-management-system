package com.gregbarasch.scholarshipmanagementsystem.io;

import com.gregbarasch.scholarshipmanagementsystem.model.Scholarship;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ScholarshipDisplayHandler implements DisplayHandler<Scholarship> {

    @Override
    public void display(Collection<Scholarship> scholarships) {
        int i = 1;
        for (Scholarship scholarship : scholarships) {
            System.out.println("\tScholarshp #" + i + "\n" + scholarship);
            ++i;
        }
    }

    @Override
    public void display(Scholarship scholarship) {
        System.out.println(scholarship);
    }
}
