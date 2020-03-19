package com.gregbarasch.scholarshipmanagementsystem.database.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This program will interact with a single database
 * I have created this class to store static and/or centralized data/functions
 */
@Repository
@Slf4j
public class SystemDatabase implements DisposableBean {

    @SuppressWarnings("ConstantConditions")
    private static final String DATABASE_RESOURCE = "jdbc:sqlite:" +
            SystemDatabase.class.getClassLoader().getResource("data").getFile() +
            File.separator + "scholarship-management-system.db";

    private Connection connection;

    public SystemDatabase() throws SQLException {
        this.connection = DriverManager.getConnection(DATABASE_RESOURCE);
        bootstrap();
    }

    public Connection getConnection() {
        return connection;
    }

    private void bootstrap() {

        // Foreign keys
        String sql = "PRAGMA foreign_keys = ON;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to enable foreign key constraints. Something went wrong", e);
        }

        // address table
        sql = "" +
                "CREATE TABLE IF NOT EXISTS address (" +
                "   id integer PRIMARY KEY," +
                "   street text NOT NULL," +
                "   city text NOT NULL," +
                "   zip integer NOT NULL," +
                "   state text NOT NULL" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to create the address table. Something went wrong", e);
        }

        // student table
        sql = "" +
                "CREATE TABLE IF NOT EXISTS student (" +
                "   id integer PRIMARY KEY," +
                "   firstName text NOT NULL," +
                "   lastName text NOT NULL," +
                "   emailAddress text NOT NULL," +
                "   addressId integer NOT NULL," +
                "   citizen integer NOT NULL," +
                "   programType text NOT NULL," +
                "   incomeBracket text NOT NULL," +
                "   gpa real NOT NULL," +
                "FOREIGN KEY(addressId) REFERENCES address(id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to create the student table. Something went wrong", e);
        }

        // Scholarship table
        sql = "" +
                "CREATE TABLE IF NOT EXISTS scholarship (" +
                "   id integer PRIMARY KEY," +
                "   name text NOT NULL," +
                "   description text NOT NULL," +
                "   price real NOT NULL," +
                "   minGpa real NOT NULL," +
                "   citizenshipRequired integer NOT NULL," +
                "   acceptedProgramTypes text NOT NULL," +
                "   acceptedIncomeBrackets text NOT NULL" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("We were unable to create the scholarship table. Something went wrong", e);
        }
    }

    @Override
    public void destroy() throws SQLException {
        log.debug("Shutting down the SystemDatabase.");
        connection.close();
    }
}
