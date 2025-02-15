package application;

/**
 * Represents an answer linked to a question.
 */
public class Answer {
    private int id;
    private int questionId;
    private String text;

    public Answer(int id, int questionId, String text) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
    }

    public int getId() { return id; }
    public int getQuestionId() { return questionId; }
    public String getText() { return text; }

    public void setId(int id) { this.id = id; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public void setText(String text) { this.text = text; }

    @Override
    public String toString() {
        return "A" + id + " (Q" + questionId + "): " + text;
    }
}
