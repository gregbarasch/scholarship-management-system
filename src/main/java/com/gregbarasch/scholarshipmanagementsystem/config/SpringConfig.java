package com.gregbarasch.scholarshipmanagementsystem.config;

import com.gregbarasch.scholarshipmanagementsystem.database.dao.SystemDatabase;
import com.gregbarasch.scholarshipmanagementsystem.service.ScholarshipService;
import com.gregbarasch.scholarshipmanagementsystem.service.StudentService;
import com.gregbarasch.scholarshipmanagementsystem.service.SystemFacade;
import com.gregbarasch.scholarshipmanagementsystem.database.dao.AddressDao;
import com.gregbarasch.scholarshipmanagementsystem.database.dao.ScholarshipDao;
import com.gregbarasch.scholarshipmanagementsystem.database.dao.StudentDao;
import com.gregbarasch.scholarshipmanagementsystem.io.ScholarshipDisplayHandler;
import com.gregbarasch.scholarshipmanagementsystem.io.StudentDisplayHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {"com.gregbarasch.scholarshipmanagementsystem"})
public class SpringConfig {

    @Bean
    public AddressDao addressDao() throws SQLException {
        return new AddressDao(systemDatabase());
    }

    @Bean
    public ScholarshipDao scholarshipDao() throws SQLException {
        return new ScholarshipDao(systemDatabase());
    }

    @Bean
    public StudentDao studentDao() throws SQLException {
        return new StudentDao(systemDatabase());
    }

    @Bean
    public ScholarshipDisplayHandler scholarshipDisplayHandler() {
        return new ScholarshipDisplayHandler();
    }

    @Bean
    public StudentDisplayHandler studentDisplayHandler() {
        return new StudentDisplayHandler();
    }

    @Bean
    public ScholarshipService scholarshipService() throws SQLException {
        return new ScholarshipService(scholarshipDao(), scholarshipDisplayHandler());
    }

    @Bean
    public StudentService studentService() throws SQLException {
        return new StudentService(studentDao(), addressDao(), studentDisplayHandler());
    }

    @Bean
    public SystemFacade systemFacade() throws SQLException {
        return new SystemFacade(scholarshipService(), studentService());
    }

    @Bean
    public SystemDatabase systemDatabase() throws SQLException {
        return new SystemDatabase();
    }
}
