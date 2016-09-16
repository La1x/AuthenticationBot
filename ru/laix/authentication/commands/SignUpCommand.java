package ru.laix.authentication.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import ru.laix.authentication.AuthenticationUtil;

import java.security.NoSuchAlgorithmException;

/**
 * Created by La1x on 14.09.2016.
 */
public class SignUpCommand extends BotCommand {
    public SignUpCommand() {
        super("signup", "Зарегистрироваться");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length != 2) {
            SendMessage err = new SendMessage();
            err.setChatId(chat.getId().toString());
            err.setText("Для регистрации отправьте мне \"/signup login password\".");

            try {
                absSender.sendMessage(err);
            } catch (TelegramApiException e) {}
            return ;
        }

        try {
            SendMessage resultMessage = new SendMessage();
            resultMessage.setChatId(chat.getId().toString());
            if (AuthenticationUtil.addNewUser(strings[0], strings[1])) {
                resultMessage.setText("Пользователь " + strings[0] + " зарегистрирован!");
            } else {
                resultMessage.setText("Пользователь с таким логином уже существует!");
            }
            absSender.sendMessage(resultMessage);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
