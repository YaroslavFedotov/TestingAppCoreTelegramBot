package com.accenture.TestingAppCore;
import java.util.ArrayList;

public class UserController {

    public static int questionNumberInTheTest;
    public static int numberOfCorrectAnswers;
    public static ArrayList questionAnswerParts = new ArrayList();
    public static String questionMessage;
    public static ArrayList testQuestionId = new ArrayList();
    public static int testQuestionIdSize;

    public static String testPerformer(String userMessage) {
        String answer;
        ConnectionDB connectionDB = new ConnectionDB();
        if (questionNumberInTheTest == 0) {
            Test test = new Test(userMessage);
            for (String question : connectionDB.getTest(test).replace(DialogueConstant.REGULAR_EXPRESSION_QUESTION, "").split(",")) {
                testQuestionId.add(question);
                testQuestionIdSize++;

            }
        }
        String[] arrSplit;
        if (questionNumberInTheTest == 0) {
            questionMessage = connectionDB.getQuestion(testQuestionId.get(questionNumberInTheTest).toString());
            questionNumberInTheTest++;
            arrSplit = questionMessage.split(DialogueConstant.REGULAR_EXPRESSION_ANSWER);
            return arrSplit[0];
        } else {
            arrSplit = questionMessage.split(DialogueConstant.REGULAR_EXPRESSION_ANSWER);
            answer = arrSplit[1];
            checkAnswer(userMessage, answer);
            if (testQuestionIdSize == questionNumberInTheTest) {
                Input.TestingFlowStopper();
                testQuestionId = new ArrayList();
                questionAnswerParts = new ArrayList();
                questionNumberInTheTest = 0;
                int AnVar = numberOfCorrectAnswers;
                numberOfCorrectAnswers = 0;
                testQuestionIdSize = 0;
                return DialogueConstant.TEST_FINISH_MESSAGE_BOT +
                        DialogueConstant.NUMBER_CORRECT_ANSWERS +
                        AnVar;
            } else {
                questionMessage = connectionDB.getQuestion(testQuestionId.get(questionNumberInTheTest).toString());
                arrSplit = questionMessage.split(DialogueConstant.REGULAR_EXPRESSION_ANSWER);
                questionNumberInTheTest++;
                return arrSplit[0];
            }
        }
    }

    public static void checkAnswer(String userAnswer, String answer) {
        if (userAnswer.equals(answer)) {
            numberOfCorrectAnswers++;
        }
    }
}
