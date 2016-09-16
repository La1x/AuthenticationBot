package ru.laix.authentication.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by La1x on 14.09.2016.
 */
public class HelpCommand extends BotCommand{
    private final static String text = new String(
            "Бот является демонстрацией реализации механизма аутентификации.\n" +
            "Если Вы впервые используете бота, то для начала вам необходимо зарегистрироваться. " +
            "Для этого отправьте мне \"/signup login password\". Затем вы можете аутентифицироваться с помощью " +
            "команды \"/auth login password\".");

    public HelpCommand() {
        super("help","Вызов справки");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.setText(text);

        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {

        }
    }
}
