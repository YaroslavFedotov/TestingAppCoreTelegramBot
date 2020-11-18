package com.accenture.TestingAppCore;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{
    private static final String TOKEN = "1358348087:AAH50PwSsTalSN6y4iPbAMoyhNmkgRwJf5E";
    private static final String USERNAME = "TestingProgectBot";
    private static String finalMessage;

    public Bot() { }

    public String getBotToken() {return TOKEN;}
    public String getBotUsername() {return USERNAME;}

    public void onUpdateReceived(Update update){
        if (update.getMessage() != null && update.getMessage().hasText()){
            finalMessage = Input.processingUserInput(update.getMessage().getText());
        } else {
            finalMessage = DialogueConstant.MISTAKE_TYPE_MESSAGE_BOT;
        }
        String chat_id = update.getMessage().getChatId().toString();
        try {
            execute(new SendMessage(chat_id, finalMessage));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
