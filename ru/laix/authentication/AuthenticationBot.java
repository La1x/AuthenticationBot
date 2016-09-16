package ru.laix.authentication;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;

import ru.laix.authentication.commands.AuthenticateCommand;
import ru.laix.authentication.commands.HelpCommand;
import ru.laix.authentication.commands.SignUpCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by La1x on 14.09.2016.
 */
public class AuthenticationBot extends TelegramLongPollingCommandBot{
    public AuthenticationBot() {
//        register(new StartCommand());
        register(new HelpCommand());
        register(new SignUpCommand());
        register(new AuthenticateCommand());
//        register(new ExitCommand());
//        register(new Info());

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId().toString());
            commandUnknownMessage.setText("Unknown command.");
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {}

            if(Files.notExists(Paths.get("Logs.txt"))) {
                try {
                    Files.createFile(Paths.get("Logs.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String logStr = message.getDate() + " " +
                            message.getFrom().getFirstName() + " " +
                            message.getFrom().getLastName() + " " +
                            message.getFrom().getUserName() + " " +
                            message.getText() + "\n";
            try {
                Files.write(Paths.get("Logs.txt"), logStr.getBytes(), StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update upd) {
        if(upd.hasMessage()) {
            Message msg = upd.getMessage();
            if(msg.hasText()) {
                SendMessage sendMsgRequest = new SendMessage();
                sendMsgRequest.setChatId(msg.getChatId().toString());
                sendMsgRequest.setText("Unknown command(text).");
                try {
                    sendMessage(sendMsgRequest);
                } catch (TelegramApiException e) { }
                if(Files.notExists(Paths.get("Logs.txt"))) {
                    try {
                        Files.createFile(Paths.get("Logs.txt"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String logStr = msg.getDate() + " " +
                        msg.getFrom().getFirstName() + " " +
                        msg.getFrom().getLastName() + " " +
                        msg.getFrom().getUserName() + " " +
                        msg.getText() + " \n";
                try {
                    Files.write(Paths.get("Logs.txt"), logStr.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }
}
