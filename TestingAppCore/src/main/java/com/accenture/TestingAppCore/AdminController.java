package com.accenture.TestingAppCore;

import java.sql.SQLException;

public class AdminController {
    private static Test test = new Test();
    private static Question question = new Question();
    private static Answer answer = new Answer();

    private static int progressСounter = 0;

    public static String createQuestion(String adminMessage) {

        if (progressСounter == 0) {
            progressСounter++;
            return DialogueConstant.QUESTION_CREATE_AUTHOR_MESSAGE_BOT;
        } else if (progressСounter == 1) {
            progressСounter++;
            question.setAuthorLogin(adminMessage);
            return DialogueConstant.QUESTION_CREATE_TYPE_MESSAGE_BOT;
        } else if (progressСounter == 2) {
            progressСounter++;
            question.setType(adminMessage);
            return DialogueConstant.QUESTION_CREATE_DIFFICULTY_MESSAGE_BOT;
        } else if (progressСounter == 3) {
            progressСounter++;
            question.setDifficulty(adminMessage);
            return DialogueConstant.QUESTION_CREATE_TEXT_MESSAGE_BOT;
        } else if (progressСounter == 4) {
            progressСounter++;
            question.setQuestionText(adminMessage);
            return DialogueConstant.QUESTION_CREATE_ANSWER_MESSAGE_BOT;
        } else if (progressСounter == 5) {
            progressСounter++;
            answer.setSingle(adminMessage);
            answer.setMultiple(adminMessage);
            return DialogueConstant.QUESTION_CREATE_OPEN_ANSWER_MESSAGE_BOT;
        } else {
            answer.setOpen(adminMessage);
            ConnectionDB connectionDB = new ConnectionDB();
            try {
                connectionDB.addQuestion(question, answer);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            question = new Question();
            answer = new Answer();
            progressСounter = 0;
            Input.modesOff();
            return DialogueConstant.QUESTION_CREATE_FINISH_MESSAGE_BOT;
        }
    }
    public static String openQuestion(String adminMessage) {
        if (progressСounter == 0) {
            progressСounter++;
            return DialogueConstant.QUESTION_OPEN_MESSAGE_BOT;
        } else {
            ConnectionDB connectionDB = new ConnectionDB();
            progressСounter = 0;
            Input.modesOff();
            return connectionDB.getQuestion(adminMessage);
        }
    }

   public static String deleteQuestion(String adminMessage) {
       if (progressСounter == 0) {
           progressСounter++;
           return DialogueConstant.QUESTION_DELETE_MESSAGE_BOT;
       } else {
           ConnectionDB connectionDB = new ConnectionDB();
           connectionDB.eraseQuestion(adminMessage);
           progressСounter = 0;
           Input.modesOff();
           return DialogueConstant.QUESTION_DELETE_СOMPLETED_MESSAGE_BOT;
       }
   }

   public static String createTest(String adminMessage) {
       if (progressСounter == 0) {
           progressСounter++;
           return DialogueConstant.TEST_CREATE_NAME_MESSAGE_BOT;
       } else if (progressСounter == 1) {
           progressСounter++;
           test.setName(adminMessage);
           return DialogueConstant.TEST_CREATE_TEXT_MESSAGE_BOT;
       } else {
           progressСounter++;
           test.setQuestions_list(adminMessage);
           try {
               ConnectionDB connectionDB = new ConnectionDB();
               connectionDB.addTest(test);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           progressСounter = 0;
           Input.modesOff();
           return DialogueConstant.TEST_CREATE_FINISH_MESSAGE_BOT;
       }
   }

   public static String openTest(String adminMessage) {
       if (progressСounter == 0) {
           progressСounter++;
           return DialogueConstant.TEST_OPEN_NAME_MESSAGE_BOT;
       } else {
           ConnectionDB connectionDB = new ConnectionDB();
           progressСounter = 0;
           Input.modesOff();
           test.setName(adminMessage);
           return "вопросы теста:\n" + connectionDB.getTest(test);
       }
   }

   public static String deleteTest(String adminMessage) {
       if (progressСounter == 0) {
           progressСounter++;
           return DialogueConstant.TEST_DELETE_MESSAGE_BOT;
       } else {
           ConnectionDB connectionDB = new ConnectionDB();
           progressСounter = 0;
           Input.modesOff();
           test.setName(adminMessage);
           connectionDB.eraseTest(test);
           return DialogueConstant.TEST_DELETE_СOMPLETED_MESSAGE_BOT;
       }
   }
}
