package com.gregbarasch.scholarshipmanagementsystem.service;

import com.gregbarasch.scholarshipmanagementsystem.model.Scholarship;
import com.gregbarasch.scholarshipmanagementsystem.model.Student;
import com.gregbarasch.scholarshipmanagementsystem.io.CLIHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * This class is responsible for wrapping business logic, making system internals invisible to the client
 * Here we instantiate the DataManagers, prompt for user input, do data management, and handle errors
 */
@Service
@Slf4j
public class SystemFacade {

    private final ScholarshipService scholarshipService;
    private final StudentService studentService;

    @Autowired
    public SystemFacade(ScholarshipService scholarshipService, StudentService studentService) {
        this.scholarshipService = scholarshipService;
        this.studentService = studentService;
    }

    public void displayScholarships() {
        scholarshipService.displayAllScholarships();
    }

    public void displayStudents() {
        studentService.displayAllStudents();
    }

    public void addStudent() {
        Student student = CLIHandler.addStudentHandler();
        if (student != null) {
            if (!studentService.addStudent(student)) {
                log.warn("We could not add the student. This student may already exist. Please try again.\n\n");
            }
        }
    }

    public void deleteStudent() {
        Collection<Student> students = studentService.getStudents();
        if (!students.isEmpty()) {
            Student student = CLIHandler.studentSelectionHandler(students);
            if (student != null) {
                if (!studentService.removeStudent(student)) {
                    log.warn("We could not remove the student. This student may not exist. Please try again.\n\n");
                }
            }
        }
    }

    public void addScholarship() {
        Scholarship scholarship = CLIHandler.addScholarshipHandler();
        if (scholarship != null) {
            if (!scholarshipService.addScholarship(scholarship)) {
                log.warn("We could not add the scholarship. This scholarship may already exist. Please try again.\n\n");
            }
        }
    }

    public void deleteScholarship() {
        Collection<Scholarship> scholarships = scholarshipService.getScholarships();
        if (!scholarships.isEmpty()) {
            Scholarship scholarship = CLIHandler.scholarshipSelectionHandler(scholarships);
            if (scholarship != null) {
                if (!scholarshipService.removeScholarship(scholarship)) {
                    log.warn("We could not remove the scholarship. This scholarship may not exist. Please try again.\n\n");
                }
            }
        }
    }
}
