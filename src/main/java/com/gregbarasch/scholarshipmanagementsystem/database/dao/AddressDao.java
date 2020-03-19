package com.gregbarasch.scholarshipmanagementsystem.database.dao;

import com.gregbarasch.scholarshipmanagementsystem.database.entity.AddressEntity;
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
public class AddressDao implements Dao<AddressEntity> {

    private final SystemDatabase systemDatabase;

    @Autowired
    public AddressDao(SystemDatabase systemDatabase) {
        this.systemDatabase = systemDatabase;
    }

    @Override
    public AddressEntity get(long id) {
        String sql = "" +
                "SELECT id, street, city, zip, state\n" +
                "FROM address\n" +
                "WHERE id = " + id +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)){

            if (resultSet.next()) {
                return new AddressEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getInt("zip"),
                        resultSet.getString("state")
                );
            }

        } catch (SQLException e) {
            log.error("We were unable to query the address table. Something went wrong", e);
        }

        return null;
    }

    @Override
    public Collection<AddressEntity> getAll() {
        Collection<AddressEntity> entities = new ArrayList<>();

        String sql = "" +
                "SELECT id, street, city, zip, state\n" +
                "FROM address" +
                ";";

        try (ResultSet resultSet = systemDatabase.getConnection().createStatement().executeQuery(sql)){
            while (resultSet.next()) {
                entities.add(new AddressEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getInt("zip"),
                        resultSet.getString("state")
                ));
            }
        } catch (SQLException e) {
            log.error("We were unable to query the address table. Something went wrong", e);
        }

        return entities;
    }

    @Override
    public boolean insert(AddressEntity addressEntity) {
        String sql = "" +
                "INSERT INTO address (id, street, city, zip, state)\n" +
                "VALUES(\n" +
                            addressEntity.getId()       + ",\n" +
                    "\""    + addressEntity.getStreet() + "\",\n" +
                    "\""    + addressEntity.getCity()   + "\",\n" +
                            addressEntity.getZip()      + ",\n" +
                    "\""    + addressEntity.getState()  + "\"\n" +
                ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the address table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean insertOrUpdate(AddressEntity addressEntity) {
        String sql = "" +
                "INSERT OR REPLACE INTO address (id, street, city, zip, state)\n" +
                "VALUES(\n" +
                        addressEntity.getId()       + ",\n" +
                "\""    + addressEntity.getStreet() + "\",\n" +
                "\""    + addressEntity.getCity()   + "\",\n" +
                        addressEntity.getZip()      + ",\n" +
                "\""    + addressEntity.getState()  + "\"\n" +
                ");";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to insert into the address table. Something went wrong", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean update(AddressEntity addressEntity) {
        String sql = "" +
                "UPDATE address\n" +
                "SET street = \""   + addressEntity.getStreet() + "\",\n" +
                "city = \""         + addressEntity.getCity()   + "\",\n" +
                "zip = "            + addressEntity.getZip()    + ",\n" +
                "state = \""        + addressEntity.getState()  + "\"\n" +
                "WHERE id = "       + addressEntity.getId()     + "\n" +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to update the address table. Something went wrong\n", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(AddressEntity addressEntity) {
        String sql = "" +
                "DELETE FROM address\n" +
                "WHERE id = " + addressEntity.getId() +
                ";";

        try (Statement statement = systemDatabase.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to delete from address table. Something went wrong", e);
            return false;
        }

        return true;
    }
}
