package com.gregbarasch.scholarshipmanagementsystem;

import com.gregbarasch.scholarshipmanagementsystem.config.SpringConfig;
import com.gregbarasch.scholarshipmanagementsystem.service.SystemFacade;
import com.gregbarasch.scholarshipmanagementsystem.io.CLIHandler;
import lombok.Cleanup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.gregbarasch.scholarshipmanagementsystem.io.CLIHandler.Selection;

// FIXME log statements should probably not go to console since UI is also console
public class Main {

    public static void main(String[] args) {

        @Cleanup
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SystemFacade systemFacade = context.getBean(SystemFacade.class);

        // Infinite loop until QUIT
        boolean run = true;
        while (run) {

            Selection selection = CLIHandler.mainMenu();
            switch (selection) {
                case DISPLAY_SCHOLARSHIPS:
                    systemFacade.displayScholarships();
                    break;
                case DISPLAY_STUDENTS:
                    systemFacade.displayStudents();
                    break;
                case ADD_STUDENT:
                    systemFacade.addStudent();
                    break;
                case DELETE_STUDENT:
                    systemFacade.deleteStudent();
                    break;
                case ADD_SCHOLARSHIP:
                    systemFacade.addScholarship();
                    break;
                case DELETE_SCHOLARSHIP:
                    systemFacade.deleteScholarship();
                    break;
                case QUIT:
                    run = false;
                    break;
            }
        }
    }
}
