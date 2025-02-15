package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX UI for managing answers (CRUD operations).
 */
public class AnswersPage {
    private List<Answer> answers = new ArrayList<>();
    private int answerCounter = 1;
    private ListView<Answer> answerListView = new ListView<>();

    public void show(Stage primaryStage, List<Question> questionList) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label titleLabel = new Label("Manage Answers");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ComboBox<Question> questionDropdown = new ComboBox<>();
        questionDropdown.getItems().addAll(questionList);
        questionDropdown.setPromptText("Select a question");

        TextField answerInput = new TextField();
        answerInput.setPromptText("Enter answer...");
        answerInput.setMaxWidth(300);

        Button addButton = new Button("Add Answer");
        Button editButton = new Button("Edit Answer");
        Button deleteButton = new Button("Delete Answer");

        // Add answer
        addButton.setOnAction(e -> {
            Question selectedQuestion = questionDropdown.getValue();
            String text = answerInput.getText().trim();
            if (selectedQuestion != null && !text.isEmpty()) {
                Answer newAnswer = new Answer(answerCounter++, selectedQuestion.getId(), text);
                answers.add(newAnswer);
                answerListView.getItems().setAll(answers);
                answerInput.clear();
            } else {
                showAlert("Error", "Select a question and enter an answer!");
            }
        });

        // Edit selected answer
        editButton.setOnAction(e -> {
            Answer selected = answerListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                TextInputDialog dialog = new TextInputDialog(selected.getText());
                dialog.setTitle("Edit Answer");
                dialog.setHeaderText("Modify the selected answer:");
                dialog.setContentText("New Answer:");

                dialog.showAndWait().ifPresent(newText -> {
                    if (!newText.trim().isEmpty()) {
                        selected.setText(newText.trim());
                        answerListView.refresh();
                    } else {
                        showAlert("Error", "Answer cannot be empty!");
                    }
                });
            } else {
                showAlert("Error", "No answer selected!");
            }
        });

        // Delete selected answer
        deleteButton.setOnAction(e -> {
            Answer selected = answerListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this answer?",
                        ButtonType.YES, ButtonType.NO);
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        answers.remove(selected);
                        answerListView.getItems().setAll(answers);
                    }
                });
            } else {
                showAlert("Error", "No answer selected!");
            }
        });

        layout.getChildren().addAll(titleLabel, questionDropdown, answerInput, addButton, editButton, deleteButton, answerListView);
        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.setTitle("Answers");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
