package ru.laix.authentication;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.IOException;

/**
 * Created by La1x on 14.09.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        AuthenticationUtil.loadDataBase();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new AuthenticationBot());
        } catch (TelegramApiException e) {

        }
    }
}
