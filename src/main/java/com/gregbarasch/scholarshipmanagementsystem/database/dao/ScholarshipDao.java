package com.gregbarasch.scholarshipmanagementsystem.database.dao;

import com.gregbarasch.scholarshipmanagementsystem.database.entity.ScholarshipEntity;
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
public class ScholarshipDao implements Dao<ScholarshipEntity> {

    private final SystemDatabase systemDatabase;

    @Autowired
    public ScholarshipDao(SystemDatabase systemDatabase) {
        this.systemDatabase = systemDatabase;
    }

    @Override
    public ScholarshipEntity get(long id) {
        String sql = "" +
                "SELECT id, name, description, price, minGpa, citizenshipRequired, acceptedProgramTypes, acceptedIncomeBrackets\n" +
                "FROM scholarship\n" +
                "WHERE id = " + id +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)){

            if (resultSet.next()) {
                return new ScholarshipEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getFloat("price"),
                        resultSet.getFloat("minGpa"),
                        resultSet.getBoolean("citizenshipRequired"),
                        resultSet.getString("acceptedProgramTypes"),
                        resultSet.getString("acceptedIncomeBrackets")
                );
            }
        } catch (SQLException e) {
            log.error("We were unable to query the scholarship table. Something went wrong", e);
        }

        return null;
    }

    @Override
    public Collection<ScholarshipEntity> getAll() {
        Collection<ScholarshipEntity> entities = new ArrayList<>();

        String sql = "" +
                "SELECT id, name, description, price, minGpa, citizenshipRequired, acceptedProgramTypes, acceptedIncomeBrackets\n" +
                "FROM scholarship" +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)){
            while (resultSet.next()) {
                entities.add(new ScholarshipEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getFloat("price"),
                        resultSet.getFloat("minGpa"),
                        resultSet.getBoolean("citizenshipRequired"),
                        resultSet.getString("acceptedProgramTypes"),
                        resultSet.getString("acceptedIncomeBrackets")
                ));
            }
        } catch (SQLException e) {
            log.warn("We were unable to query the scholarship table. Something went wrong", e);
        }

        return entities;
    }

    @Override
    public boolean insert(ScholarshipEntity scholarshipEntity) {
        String sql = "" +
                "INSERT INTO scholarship (id, name, description, price, minGpa, citizenshipRequired, acceptedProgramTypes, acceptedIncomeBrackets)\n" +
                "VALUES(\n" +
                            scholarshipEntity.getId()                       + ",\n" +
                    "\""    + scholarshipEntity.getName()                   + "\",\n" +
                    "\""    + scholarshipEntity.getDescription()            + "\",\n" +
                            scholarshipEntity.getPrice()                    + ",\n" +
                            scholarshipEntity.getMinGpa()                   + ",\n" +
                            scholarshipEntity.isCitizenshipRequired()       + ",\n" +
                    "\""    + scholarshipEntity.getAcceptedProgramTypes()   + "\",\n" +
                    "\""    + scholarshipEntity.getAcceptedIncomeBrackets() + "\"\n" +
                ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the scholarship table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean insertOrUpdate(ScholarshipEntity scholarshipEntity) {
        String sql = "" +
                "INSERT OR REPLACE INTO scholarship (id, name, description, price, minGpa, citizenshipRequired, acceptedProgramTypes, acceptedIncomeBrackets)\n" +
                "VALUES(\n" +
                            scholarshipEntity.getId()                       + ",\n" +
                    "\""    + scholarshipEntity.getName()                   + "\",\n" +
                    "\""    + scholarshipEntity.getDescription()            + "\",\n" +
                            scholarshipEntity.getPrice()                    + ",\n" +
                            scholarshipEntity.getMinGpa()                   + ",\n" +
                            scholarshipEntity.isCitizenshipRequired()       + ",\n" +
                    "\""    + scholarshipEntity.getAcceptedProgramTypes()   + "\",\n" +
                    "\""    + scholarshipEntity.getAcceptedIncomeBrackets() + "\"\n" +
                ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the scholarship table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean update(ScholarshipEntity scholarshipEntity) {
        String sql = "" +
                "UPDATE scholarship\n" +
                "SET name = \""               + scholarshipEntity.getName()                   + "\",\n" +
                "description = \""            + scholarshipEntity.getDescription()            + "\",\n" +
                "price = "                    + scholarshipEntity.getPrice()                  + ",\n" +
                "minGpa = "                   + scholarshipEntity.getMinGpa()                 + ",\n" +
                "citizenShipRequired = "      + scholarshipEntity.isCitizenshipRequired()     + ",\n" +
                "acceptedProgramTypes = \""   + scholarshipEntity.getAcceptedProgramTypes()   + "\",\n" +
                "acceptedIncomeBrackets = \"" + scholarshipEntity.getAcceptedIncomeBrackets() + "\"\n" +
                "WHERE id = " + scholarshipEntity.getId() +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to update the scholarship table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(ScholarshipEntity scholarshipEntity) {
        String sql = "" +
                "DELETE FROM scholarship\n" +
                "WHERE id = " + scholarshipEntity.getId() +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to delete from scholarship table. Something went wrong", e);
            return false;
        }

        return true;
    }
}
