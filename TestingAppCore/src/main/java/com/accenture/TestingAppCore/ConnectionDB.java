package com.accenture.TestingAppCore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
public class ConnectionDB extends ConfigDB {
    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassCastException, SQLException {
        String ConnectionString = "jdbc:h2:" + dbPath + "/" + dbName;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbConnection = DriverManager.getConnection(ConnectionString, dbUser, dbPass);
        return dbConnection;
    }
    public ResultSet getUser(User user) {
        ResultSet rs = null;
        String select = "SELECT * FROM " + ConstantBD.USERS_TABLE + " WHERE " +
                ConstantBD.USERS_LOGIN + " = ? AND " + ConstantBD.USERS_PASSWORD +
                " = ? AND " + ConstantBD.USERS_ADMIN_STATUS + "= ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setBoolean(3, user.getAdmin_status());
            rs = prSt.executeQuery();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }
    public void registerUser(User user) throws SQLException {
        ResultSet rs = getDbConnection().createStatement().executeQuery(
                "SELECT COUNT(*) AS Qty FROM " + ConstantBD.USERS_TABLE);
        rs.next();
        int CounterID = rs.getInt(1) + 1;
        String insert = "INSERT INTO " + ConstantBD.USERS_TABLE + "(" +
                ConstantBD.USERS_ID + "," + ConstantBD.USERS_LOGIN + "," +
                ConstantBD.USERS_NAME + "," + ConstantBD.USERS_PASSWORD + "," +
                ConstantBD.USERS_ADMIN_STATUS + ")" +
                "VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, CounterID);
            prSt.setString(2, user.getLogin());
            prSt.setString(3, user.getName());
            prSt.setString(4, user.getPassword());
            prSt.setBoolean(5, user.getAdmin_status());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addTest(Test test) throws SQLException {
        ResultSet rs = getDbConnection().createStatement().executeQuery(
                "SELECT COUNT(*) AS Qty FROM " + ConstantBD.TESTS_TABLE);
        rs.next();
        int CounterID = rs.getInt(1) + 1;
        String insert = "INSERT INTO " + ConstantBD.TESTS_TABLE + "(" +
                ConstantBD.TESTS_ID + "," + ConstantBD.TESTS_NAME + "," +
                ConstantBD.TESTS_QUESTIONS_LIST + ")" +
                "VALUES(?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, CounterID);
            prSt.setString(2, test.getName());
            prSt.setString(3, test.getQuestions_list());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public String getTest(Test test) {
        ResultSet rs = null;
        String result = null;
        String select = "SELECT * FROM " + ConstantBD.TESTS_TABLE + " WHERE " +
                ConstantBD.TESTS_NAME + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, test.getName());
            rs = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs.next();
            result = rs.getString(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void eraseTest(Test test) {
        String searchSequence = getTest(test);
        String erase = "DELETE FROM " + ConstantBD.TESTS_TABLE + " WHERE " +
                ConstantBD.TESTS_QUESTIONS_LIST + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(erase);
            prSt.setString(1, searchSequence);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addQuestion(Question question, Answer answer) throws SQLException {
        String answerText;
        switch (question.getType()) {
            case DialogueConstant.SINGLE_TYPE:
                answerText = answer.getSingle();
                break;
            case DialogueConstant.MULTIPLE_TYPE:
                answerText = answer.getMultiple();
                break;
            case DialogueConstant.OPEN_TYPE:
                answerText = answer.getOpen();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + question.getType());
        }
        ResultSet rs = getDbConnection().createStatement().executeQuery(
                "SELECT COUNT(*) AS Qty FROM " + ConstantBD.QUESTIONS_TABLE);
        rs.next();
        int CounterID = rs.getInt(1) + 1;
        String insert = "INSERT INTO " + ConstantBD.QUESTIONS_TABLE + "(" +
                ConstantBD.QUESTIONS_ID + "," + ConstantBD.QUESTIONS_DIFFICULTY  +
                "," + ConstantBD.QUESTIONS_TYPE + "," + ConstantBD.QUESTIONS_AUTHOR +
                "," + ConstantBD.QUESTIONS_TEXT + "," + ConstantBD.QUESTIONS_ANSWER_TEXT + ")" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, CounterID);
            prSt.setString(2, question.getDifficulty());
            prSt.setString(3, question.getType());
            prSt.setString(4, question.getAuthorLogin());
            prSt.setString(5, question.getQuestionText());
            prSt.setString(6, answerText);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public String getQuestion(String questionId) {
        ResultSet rs = null;
        String result = null;
        String select = "SELECT * FROM " + ConstantBD.QUESTIONS_TABLE + " WHERE " +
                ConstantBD.QUESTIONS_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, questionId);
            rs = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs.next();
            result = "сложность: " + rs.getString(2) + "\n\n" +
                    "тип: " + rs.getString(3) + "\n\n" + "автор: " +
                    rs.getString(4) + "\n\n\n" + "текст вопроса:\n" +
                    rs.getString(5) + "\n\n" + "текст ответа:\n" +
                    rs.getString(6);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public void eraseQuestion(String questionId) {
        String erase = "DELETE FROM " + ConstantBD.QUESTIONS_TABLE + " WHERE " + ConstantBD.QUESTIONS_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(erase);
            prSt.setString(1, questionId);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
