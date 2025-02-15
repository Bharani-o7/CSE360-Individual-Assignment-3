package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import databasePart1.*;

/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * It allows users to navigate to their respective pages based on their role or quit the application.
 */
public class WelcomeLoginPage {

    private final DatabaseHelper databaseHelper;

    public WelcomeLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage, User user) {

        VBox layout = new VBox(10);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Label welcomeLabel = new Label("Welcome, " + user.getUserName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Button to navigate to the user's respective page based on their role
        Button continueButton = new Button("Continue");

        continueButton.setOnAction(a -> {
            String role = user.getRole();
            System.out.println("User role: " + role);

            if (role.equals("admin")) {
                new AdminHomePage().show(primaryStage);
            } else {
                new QuestionsPage().show(primaryStage); // Redirect users directly to QuestionsPage
            }
        });

        // Button to quit the application
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(a -> {
            databaseHelper.closeConnection();
            Platform.exit(); // Exit the JavaFX application
        });

        // "Invite" button for admin to generate invitation codes
        if ("admin".equals(user.getRole())) {
            Button inviteButton = new Button("Generate Invitation Code");
            inviteButton.setOnAction(a -> {
                new InvitationPage().show(databaseHelper, primaryStage);
            });
            layout.getChildren().addAll(welcomeLabel, continueButton, inviteButton, quitButton);
        } else {
            layout.getChildren().addAll(welcomeLabel, continueButton, quitButton);
        }

        Scene welcomeScene = new Scene(layout, 800, 400);

        // Set the scene to primary stage
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Welcome Page");
    }
}
