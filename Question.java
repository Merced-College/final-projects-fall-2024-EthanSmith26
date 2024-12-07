package millionaireTriviaGame;

public class Question {
    private String Question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String Answer;

    // Constructor
    public Question(String Question, String optionA, String optionB, String optionC, String optionD, String Answer) {
        this.Question = Question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.Answer = Answer;
    }

    // Getters
    public String getQuestion() {
        return Question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getAnswer() {
        return Answer;
    }
}
