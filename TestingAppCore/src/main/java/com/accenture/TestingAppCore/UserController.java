package com.accenture.TestingAppCore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

public class UserController {

    public static int questionNumberInTheTest;
    public static int numberOfCorrectAnswers;
    public  ArrayList questionAnswerParts = new ArrayList();
    public static String questionMessage;

    public int getQuestionNumberInTheTest() {
        return questionNumberInTheTest;
    }
    public void setQuestionNumberInTheTest(int questionNumberInTheTest) { this.questionNumberInTheTest = questionNumberInTheTest; }
    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }
    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) { this.numberOfCorrectAnswers = numberOfCorrectAnswers; }
    public ArrayList getTestQuestionId() {
        return Input.testQuestionId;
    }
    public ArrayList getQuestionAnswerParts() {
        return questionAnswerParts;
    }
    public void setQuestionAnswerParts(ArrayList questionAnswerParts) { this.questionAnswerParts = questionAnswerParts; }


    public String testPerformer(String userMessage) {
        String answer = null;
        ConnectionDB connectionDB = new ConnectionDB();
        if (questionNumberInTheTest == 0) {
            Test test = new Test(userMessage);
            for (String question : connectionDB.getTest(test).replace("вопросы теста:\n", "").split(",")) {
                Input.testQuestionId.add(question);
                Input.testQuestionIdSize++;

            }
        }

        String[] arrSplit;
        if (questionNumberInTheTest == 0) {
            questionMessage = connectionDB.getQuestion(Input.testQuestionId.get(questionNumberInTheTest).toString());
            questionNumberInTheTest++;
            arrSplit = questionMessage.split("\n\nтекст ответа:\n");
            return arrSplit[0];
        } else {
            arrSplit = questionMessage.split("\n\nтекст ответа:\n");
            answer = arrSplit[1];
            checkAnswer(userMessage, answer);
            if (Input.testQuestionIdSize == questionNumberInTheTest) {
                Input.TestingFlowStopper();
                Input.testQuestionId = new ArrayList();
                questionAnswerParts = new ArrayList();
                questionNumberInTheTest = 0;
                int AnVar = numberOfCorrectAnswers;
                numberOfCorrectAnswers = 0;
                Input.testQuestionIdSize = 0;
                return DialogueConstant.TEST_FINISH_MESSAGE_BOT +
                        "\nколличество правельных ответов: " +
                        AnVar;
            } else {
                questionMessage = connectionDB.getQuestion(Input.testQuestionId.get(questionNumberInTheTest).toString());
                arrSplit = questionMessage.split("\n\nтекст ответа:\n");
                questionNumberInTheTest++;
                return arrSplit[0];
            }
        }
    }

    public void checkAnswer(String userAnswer, String answer) {
        if (userAnswer.equals(answer)) {
            numberOfCorrectAnswers++;
        }
    }
}
