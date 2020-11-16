package com.accenture.TestingAppCore;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TestingAppCoreApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi botApi = new TelegramBotsApi();
		try {
			botApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}










