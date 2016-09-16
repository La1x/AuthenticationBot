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
 * Created by La1x on 15.09.2016.
 */
public class AuthenticateCommand extends BotCommand{
    public AuthenticateCommand() {
        super("auth", "Пройти аутентификацию");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if(strings.length != 2) {
            SendMessage err = new SendMessage();
            err.setChatId(chat.getId().toString());
            err.setText("Для аутентификации отправьте мне \"/auth login password\".");

            try {
                absSender.sendMessage(err);
            } catch (TelegramApiException e) {}
            return ;
        }

        try {
            boolean authResult = AuthenticationUtil.checkPassword(strings[0], strings[1]);
            SendMessage resultMessage = new SendMessage();
            resultMessage.setChatId(chat.getId().toString());
            if (authResult == true) {
                resultMessage.setText("Аутентификация прошла успешно!\n" +
                        "Login: " + strings[0] + "\n" +
                        "Password: " + strings[1] + "\n" +
                        "Salt: " + AuthenticationUtil.getUserData(strings[0]).getSalt() + "\n" +
                        "Hash: " + AuthenticationUtil.getUserData(strings[0]).getHashPasswordSalt());
            } else {
                resultMessage.setText("Неправильный логин или пароль.");
            }
            absSender.sendMessage(resultMessage);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
