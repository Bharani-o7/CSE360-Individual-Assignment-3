package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX UI for managing questions (CRUD operations).
 */
public class QuestionsPage {
    private List<Question> questions = new ArrayList<>();
    private int questionCounter = 1;
    private ListView<Question> questionList = new ListView<>();

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label titleLabel = new Label("Manage Questions");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField questionInput = new TextField();
        questionInput.setPromptText("Enter question...");
        questionInput.setMaxWidth(300);

        Button addButton = new Button("Add Question");
        Button editButton = new Button("Edit Question");
        Button deleteButton = new Button("Delete Question");

        // Add question
        addButton.setOnAction(e -> {
            String text = questionInput.getText().trim();
            if (!text.isEmpty()) {
                Question newQuestion = new Question(questionCounter++, text);
                questions.add(newQuestion);
                questionList.getItems().setAll(questions);
                questionInput.clear();
            } else {
                showAlert("Error", "Question cannot be empty!");
            }
        });

        // Edit selected question
        editButton.setOnAction(e -> {
            Question selected = questionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                TextInputDialog dialog = new TextInputDialog(selected.getText());
                dialog.setTitle("Edit Question");
                dialog.setHeaderText("Modify the selected question:");
                dialog.setContentText("New Question:");

                dialog.showAndWait().ifPresent(newText -> {
                    if (!newText.trim().isEmpty()) {
                        selected.setText(newText.trim());
                        questionList.refresh();
                    } else {
                        showAlert("Error", "Question cannot be empty!");
                    }
                });
            } else {
                showAlert("Error", "No question selected!");
            }
        });

        // Delete selected question
        deleteButton.setOnAction(e -> {
            Question selected = questionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this question?",
                        ButtonType.YES, ButtonType.NO);
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        questions.remove(selected);
                        questionList.getItems().setAll(questions);
                    }
                });
            } else {
                showAlert("Error", "No question selected!");
            }
        });

        Button manageAnswersButton = new Button("Manage Answers");
        manageAnswersButton.setOnAction(e -> {
            if (!questions.isEmpty()) {
                new AnswersPage().show(primaryStage, questions);
            } else {
                showAlert("Error", "No questions available! Add questions first.");
            }
        });

        layout.getChildren().addAll(titleLabel, questionInput, addButton, editButton, deleteButton, questionList, manageAnswersButton);
        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.setTitle("Questions");
        primaryStage.show();
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
