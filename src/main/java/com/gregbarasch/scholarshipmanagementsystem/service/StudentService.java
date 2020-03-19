package com.gregbarasch.scholarshipmanagementsystem.service;

import com.gregbarasch.scholarshipmanagementsystem.database.dao.AddressDao;
import com.gregbarasch.scholarshipmanagementsystem.database.entity.AddressEntity;
import com.gregbarasch.scholarshipmanagementsystem.database.entity.StudentEntity;
import com.gregbarasch.scholarshipmanagementsystem.model.Address;
import com.gregbarasch.scholarshipmanagementsystem.model.IncomeBracket;
import com.gregbarasch.scholarshipmanagementsystem.model.ProgramType;
import com.gregbarasch.scholarshipmanagementsystem.model.Student;
import com.gregbarasch.scholarshipmanagementsystem.database.dao.StudentDao;
import com.gregbarasch.scholarshipmanagementsystem.io.StudentDisplayHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@Slf4j
public class StudentService {

    private final StudentDao studentDao;
    private final AddressDao addressDao; // FIXME note, that currently we do not delete addresses
    private final StudentDisplayHandler studentDisplayHandler;

    @Autowired
    public StudentService(StudentDao studentDao, AddressDao addressDao, StudentDisplayHandler studentDisplayHandler) {
        this.studentDao = studentDao;
        this.addressDao = addressDao;
        this.studentDisplayHandler = studentDisplayHandler;
    }

    public boolean addStudent(Student s) {
        if (addressDao.insertOrUpdate(new AddressEntity(s.getAddress()))) {
            return studentDao.insert(new StudentEntity(s));
        }

        return false;
    }

    public boolean removeStudent(Student s) {
        return studentDao.delete(new StudentEntity(s));
    }

    public Collection<Student> getStudents() {
        Collection<Student> students = new HashSet<>();
        Collection<StudentEntity> studentEntities = studentDao.getAll();

        if (studentEntities != null) {

            // loop over the entities and create Student objects
            for (StudentEntity studentEntity : studentEntities) {

                // address
                Address address = null;
                AddressEntity addressEntity = addressDao.get(studentEntity.getAddressId());
                if (addressEntity != null) {
                    address = new Address(
                            addressEntity.getStreet(),
                            addressEntity.getCity(),
                            addressEntity.getZip(),
                            addressEntity.getState()
                    );
                }

                // programType and income bracket
                ProgramType programType = ProgramType.fromString(studentEntity.getProgramType());
                IncomeBracket incomeBracket = IncomeBracket.fromString(studentEntity.getIncomeBracket());

                // Student
                if (address != null && programType != null && incomeBracket != null) {
                    students.add(new Student(
                            studentEntity.getFirstName(),
                            studentEntity.getLastName(),
                            studentEntity.getEmailAddress(),
                            address,
                            studentEntity.isCitizen(),
                            programType,
                            incomeBracket,
                            studentEntity.getGpa()
                    ));
                } else {
                    log.warn("We were unable to retrieve student id: {} because address, programType, or incomeBracket registered as null", studentEntity.getId());
                }
            }
        }

        return students;
    }

    public void displayAllStudents() {
        studentDisplayHandler.display(getStudents());
    }
}
