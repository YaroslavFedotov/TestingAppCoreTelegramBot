package com.accenture.TestingAppCore;

public class Question {
    private String type;
    private String authorLogin;
    private String difficulty;
    private String questionText;

    public Question(String type, String authorLogin, String difficulty, String questionText) {
        this.type = type;
        this.authorLogin = authorLogin;
        this.difficulty = difficulty;
        this.questionText = questionText;
    }
    public Question() {};
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAuthorLogin() {
        return authorLogin;
    }
    public void setAuthorLogin(String authorLogin) { this.authorLogin = authorLogin; }
    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
}
