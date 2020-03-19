package com.gregbarasch.scholarshipmanagementsystem.io;

import com.gregbarasch.scholarshipmanagementsystem.model.Address;
import com.gregbarasch.scholarshipmanagementsystem.model.IncomeBracket;
import com.gregbarasch.scholarshipmanagementsystem.model.ProgramType;
import com.gregbarasch.scholarshipmanagementsystem.model.Scholarship;
import com.gregbarasch.scholarshipmanagementsystem.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * This class is used to display menus, collect user input, validate user input, and create necessary objects for the caller
 * This is the main utility class that the user interfaces with
 *
 * TODO add number format validation
  */
public class CLIHandler {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String INCOME_BRACKET_PATTERN = "[lmh]";
    private static final String PROGRAM_TYPE_PATTERN = "[ug]";
    private static final String DONE_PATTERN = "d";

    public enum Selection {
        DISPLAY_SCHOLARSHIPS,
        DISPLAY_STUDENTS,
        ADD_STUDENT,
        DELETE_STUDENT,
        ADD_SCHOLARSHIP,
        DELETE_SCHOLARSHIP,
        // REGISTER_STUDENT_FOR_SCHOLARSHIP,
        // DISPLAY_SCHOLARSHIPS_FOR_STUDENT,
        QUIT,
        NONE
    }

    public static Selection mainMenu() {

        System.out.println("\nPlease choose a number\n\t" +
                Selection.DISPLAY_SCHOLARSHIPS.ordinal()             + " - display scholarships\n\t" +
                Selection.DISPLAY_STUDENTS.ordinal()                 + " - display students\n\t" +
                Selection.ADD_STUDENT.ordinal()                      + " - add a student\n\t" +
                Selection.DELETE_STUDENT.ordinal()                   + " - delete a student\n\t" +
                Selection.ADD_SCHOLARSHIP.ordinal()                  + " - add a scholarship\n\t" +
                Selection.DELETE_SCHOLARSHIP.ordinal()               + " - delete a scholarship\n\t" +
                // Selection.REGISTER_STUDENT_FOR_SCHOLARSHIP.ordinal() + " - register a student for scholarships\n\t" +
                // Selection.DISPLAY_SCHOLARSHIPS_FOR_STUDENT.ordinal() + " - display scholarships for student\n\t" +
                Selection.QUIT.ordinal()                             + " - quit\n"
        );

        try {
            int selection = scanner.nextInt();
            scanner.nextLine();

            Selection[] selections = Selection.values();
            if (selections.length > selection) {
                return Selection.values()[selection];
            }

        } catch (NoSuchElementException e) {
            try { scanner.nextLine(); } catch (NoSuchElementException ignore) {}
            System.out.println("Wrong value entered! Please try again. \n");
        }

        return Selection.NONE;
    }

    public static Student addStudentHandler() {

        try {
            // Read input START
            System.out.println("\nPlease enter the students first name");
            String firstName = scanner.nextLine();

            System.out.println("\nPlease enter the students last name");
            String lastName = scanner.nextLine();

            System.out.println("\nPlease enter the students email address");
            String emailAddress = scanner.nextLine();

            System.out.println("\nPlease enter the students address - street");
            String street = scanner.nextLine();

            System.out.println("\nPlease enter the students address - city");
            String city = scanner.nextLine();

            System.out.println("\nPlease enter the students address - state");
            String state = scanner.nextLine();

            System.out.println("\nPlease enter the students address - zip");
            int zip = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\nIs the student a citizen? true/false");
            boolean citizen = scanner.nextBoolean();
            scanner.nextLine();

            System.out.println("\nWhat program is this student? u/g (Undergraduate Graduate)");
            String programTypeString = scanner.next(PROGRAM_TYPE_PATTERN);

            System.out.println("\nWhat income bracket is this student? l/m/h (Low Medium High)");
            String incomeBracketString = scanner.next(INCOME_BRACKET_PATTERN);

            System.out.println("\nWhat is the students GPA?");
            float gpa = scanner.nextFloat();
            scanner.nextLine();
            // Read input END

            System.out.println("\n");

            // Create necessary objects and return student
            Address address = new Address(street, city, zip, state);
            ProgramType programType = ProgramType.fromString(programTypeString);
            IncomeBracket incomeBracket = IncomeBracket.fromString(incomeBracketString);

            return new Student(firstName, lastName, emailAddress, address, citizen, programType, incomeBracket, gpa);

        } catch (NoSuchElementException e) {
            try { scanner.nextLine(); } catch (NoSuchElementException ignore) {}
            System.out.println("\n\nWrong value entered! Please try again.\n");
            return null;
        }
    }

    public static Scholarship addScholarshipHandler() {

        try {
            System.out.println("\nWhat is the name of the scholarship?");
            String name = scanner.nextLine();

            System.out.println("\nPlease enter a short description of the scholarship.");
            String description = scanner.nextLine();

            System.out.println("\nPlease enter the price of the scholarship.");
            float price = scanner.nextFloat();
            scanner.nextLine();

            System.out.println("\nWhat is the minimum GPA of the scholarship?");
            float minGpa = scanner.nextFloat();
            scanner.nextLine();

            System.out.println("\nIs citizenship requred? true/false");
            boolean citizenshipRequred = scanner.nextBoolean();

            // Read program types
            System.out.println("\nPlease enter the accepted program types u/g (Undergraduate Graduate), one on each line. Enter the \"d\" key once complete.");
            Set<ProgramType> acceptedProgramTypes = new HashSet<>();
            while (scanner.hasNext(PROGRAM_TYPE_PATTERN)) {
                ProgramType programType = ProgramType.fromString(scanner.next(PROGRAM_TYPE_PATTERN));
                scanner.nextLine();
                acceptedProgramTypes.add(programType);
            }
            scanner.next(DONE_PATTERN);
            scanner.nextLine();

            // Read income brackets
            System.out.println("\nPlease enter the accepted income brackets l/m/h (Low Medium High), one on each line. Enter the \"d\" key once complete.");
            Set<IncomeBracket> acceptedIncomeBrackets = new HashSet<>();
            while (scanner.hasNext(INCOME_BRACKET_PATTERN)) {
                IncomeBracket incomeBracket = IncomeBracket.fromString(scanner.next(INCOME_BRACKET_PATTERN));
                scanner.nextLine();
                acceptedIncomeBrackets.add(incomeBracket);
            }
            scanner.next(DONE_PATTERN);
            scanner.nextLine();

            // return scholarship
            System.out.println("\n");
            return new Scholarship(name, description, price, minGpa, citizenshipRequred, acceptedProgramTypes, acceptedIncomeBrackets);

        } catch (NoSuchElementException e) {
            try { scanner.nextLine(); } catch (NoSuchElementException ignore) {}
            System.out.println("\n\nWrong value entered! Please try again.\n");
            return null;
        }
    }

    public static Student studentSelectionHandler(Collection<Student> students) {
        if (students.isEmpty()) return null;

        // While displaying, store a local map of student # to student object
        Map<Integer, Student> studentNumberMap = new HashMap<>();
        int i = 1;
        for (Student student : students) {
            System.out.println("\tStudent #" + i + "\n" + student);
            studentNumberMap.put(i, student);
            ++i;
        }

        // Then return their selected #
        System.out.println("\nWhich student # would you like to select?");
        try {
            int studentNumber = scanner.nextInt();
            return studentNumberMap.get(studentNumber);

        } catch (NoSuchElementException e) {
            try { scanner.nextLine(); } catch (NoSuchElementException ignore) {}
            System.out.println("\n\nWrong value entered! Please try again.\n");
            return null;
        }
    }

    public static Scholarship scholarshipSelectionHandler(Collection<Scholarship> scholarships) {
        if (scholarships.isEmpty()) return null;

        // While displaying, store a local map of scholarship # to scholarship object
        Map<Integer, Scholarship> scholarshipNumberMap = new HashMap<>();
        int i = 1;
        for (Scholarship scholarship : scholarships) {
            System.out.println("\tScholarship #" + i + "\n" + scholarship);
            scholarshipNumberMap.put(i, scholarship);
            ++i;
        }

        // Then return their selected #
        System.out.println("\nWhich scholarship # would you like to select?");
        try {
            int scholarshipNumber = scanner.nextInt();
            return scholarshipNumberMap.get(scholarshipNumber);

        } catch (NoSuchElementException e) {
            try { scanner.nextLine(); } catch (NoSuchElementException ignore) {}
            System.out.println("\n\nWrong value entered! Please try again.\n");
            return null;
        }
    }
}
