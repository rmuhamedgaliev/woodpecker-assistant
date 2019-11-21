package io.github.rmuhamedgaliev.woodpecker;

import io.github.rmuhamedgaliev.woodpecker.commands.YoutrackCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class HelloController extends TelegramLongPollingBot {

    @Value("${app.telegram.token}")
    private String token;

    @Value("${app.telegram.name}")
    private String name;

    private final YoutrackCommands youtrackCommands;

    @Autowired
    public HelloController(DefaultBotOptions defaultBotOptions, YoutrackCommands youtrackCommands) {
        super(defaultBotOptions);
        this.youtrackCommands = youtrackCommands;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();

        String[] msg = message.split(" ");

        String arg = msg[1 % msg.length];

//        /token perm:cm11aGFtZWRnYWxpZXY=.NDctMA==.rj70ZJ7eklBifZPuEJXeO1z9Ul5My0
        if (
            update.getMessage().getChat().isGroupChat() == false
            && message.startsWith("/auth")) {

            try {

                String token = message.split(" ")[1];

                Method method = youtrackCommands.getClass().getMethod("auth", Update.class);
                method.invoke(youtrackCommands, update);

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
