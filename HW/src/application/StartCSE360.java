package application;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;

import databasePart1.DatabaseHelper;

public class StartCSE360 extends Application {

    private static final DatabaseHelper databaseHelper = new DatabaseHelper();

    public static void main(String[] args) {
        launch(args); // Start JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            databaseHelper.connectToDatabase(); // Connect to database

            if (databaseHelper.isDatabaseEmpty()) {
                // If the database is empty, prompt the first user to set up an admin account
                new FirstPage(databaseHelper).show(primaryStage);
            } else {
                // Otherwise, go to the login selection page
                new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles navigation after login.
     * Redirects users based on their role (admin or regular user).
     */
    public static void navigateToHomePage(Stage primaryStage, User user) {
        if ("admin".equals(user.getRole())) {
            new AdminHomePage().show(primaryStage); // Show admin home
        } else {
            new UserHomePage().show(primaryStage); // Show user home
        }
    }
}
