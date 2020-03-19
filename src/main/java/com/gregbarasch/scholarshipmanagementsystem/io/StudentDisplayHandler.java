package com.gregbarasch.scholarshipmanagementsystem.io;

import com.gregbarasch.scholarshipmanagementsystem.model.Student;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StudentDisplayHandler implements DisplayHandler<Student> {

    @Override
    public void display(Collection<Student> students) {
        int i = 1;
        for (Student student : students) {
            System.out.println("\tStudent #" + i + "\n" + student);
            ++i;
        }
    }

    @Override
    public void display(Student student) {
        System.out.println(student);
    }
}
