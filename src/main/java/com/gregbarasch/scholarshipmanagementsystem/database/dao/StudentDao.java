package com.gregbarasch.scholarshipmanagementsystem.database.dao;

import com.gregbarasch.scholarshipmanagementsystem.database.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Slf4j
public class StudentDao implements Dao<StudentEntity> {

    private final SystemDatabase systemDatabase;

    @Autowired
    public StudentDao(SystemDatabase systemDatabase) {
        this.systemDatabase = systemDatabase;
    }

    @Override
    public StudentEntity get(long id) {
        String sql = "" +
                "SELECT id, firstName, lastName, emailAddress, addressId, citizen, programType, incomeBracket, gpa\n" +
                "FROM student\n" +
                "WHERE id = " + id +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                return new StudentEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("emailAddress"),
                        resultSet.getLong("addressId"),
                        resultSet.getBoolean("citizen"),
                        resultSet.getString("programType"),
                        resultSet.getString("incomeBracket"),
                        resultSet.getFloat("gpa")
                );
            }
        } catch (SQLException e) {
            log.error("We were unable to query the student table. Something went wrong", e);
        }

        return null;
    }

    @Override
    public Collection<StudentEntity> getAll() {
        Collection<StudentEntity> entities = new ArrayList<>();

        String sql = "" +
                "SELECT id, firstName, lastName, emailAddress, addressId, citizen, programType, incomeBracket, gpa\n" +
                "FROM student" +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                entities.add(new StudentEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("emailAddress"),
                        resultSet.getLong("addressId"),
                        resultSet.getBoolean("citizen"),
                        resultSet.getString("programType"),
                        resultSet.getString("incomeBracket"),
                        resultSet.getFloat("gpa")
                ));
            }
        } catch (SQLException e) {
            log.error("We were unable to query the student table. Something went wrong", e);
        }

        return entities;

    }

    @Override
    public boolean insert(StudentEntity studentEntity) {
        String sql = "" +
                "INSERT INTO student (id, firstName, lastName, emailAddress, addressId, citizen, programType, incomeBracket, gpa)\n" +
                "VALUES(\n" +
                            studentEntity.getId()               + ",\n" +
                    "\""    + studentEntity.getFirstName()      + "\",\n" +
                    "\""    + studentEntity.getLastName()       + "\",\n" +
                    "\""    + studentEntity.getEmailAddress()   + "\",\n" +
                    "\""    + studentEntity.getAddressId()      + "\",\n" +
                            studentEntity.isCitizen()           + ",\n" +
                    "\""    + studentEntity.getProgramType()    + "\",\n" +
                    "\""    + studentEntity.getIncomeBracket()  + "\",\n" +
                            studentEntity.getGpa()              + "\n" +
                ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the student table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean insertOrUpdate(StudentEntity studentEntity) {
        final String sql = "" +
                "INSERT OR REPLACE INTO student (id, firstName, lastName, emailAddress, addressId, citizen, programType, incomeBracket, gpa)\n" +
                "VALUES(\n" +
                            studentEntity.getId()               + ",\n" +
                    "\""    + studentEntity.getFirstName()      + "\",\n" +
                    "\""    + studentEntity.getLastName()       + "\",\n" +
                    "\""    + studentEntity.getEmailAddress()   + "\",\n" +
                    "\""    + studentEntity.getAddressId()      + "\",\n" +
                            studentEntity.isCitizen()           + ",\n" +
                    "\""    + studentEntity.getProgramType()    + "\",\n" +
                    "\""    + studentEntity.getIncomeBracket()  + "\",\n" +
                            studentEntity.getGpa()              + "\n" +
                    ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the student table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean update(StudentEntity studentEntity) {
        String sql = "" +
                "UPDATE student\n" +
                "SET firstName = \""    + studentEntity.getFirstName()      + "\",\n" +
                "lastName = \""         + studentEntity.getLastName()       + "\",\n" +
                "emailAddress = \""     + studentEntity.getEmailAddress()   + "\",\n" +
                "addressId = "          + studentEntity.getAddressId()      + ",\n" +
                "citizen = "            + studentEntity.isCitizen()         + ",\n" +
                "programType \"= "      + studentEntity.getProgramType()    + "\",\n" +
                "incomeBracket = \""    + studentEntity.getIncomeBracket()  + "\",\n" +
                "gpa = "                + studentEntity.getGpa()            + "\n" +
                "WHERE id = " + studentEntity.getId() +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to update the student table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(StudentEntity studentEntity) {
        String sql = "" +
                "DELETE FROM student\n" +
                "WHERE id = " + studentEntity.getId() +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to delete from student table. Something went wrong", e);
            return false;
        }

        return true;
    }
}
