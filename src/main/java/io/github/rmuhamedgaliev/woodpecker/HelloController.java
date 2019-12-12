package io.github.rmuhamedgaliev.woodpecker;

import io.github.rmuhamedgaliev.woodpecker.commands.CommandHelper;
import io.github.rmuhamedgaliev.woodpecker.commands.YoutrackCommands;
import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackUserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
@Component
public class HelloController extends TelegramLongPollingBot {

    private final YoutrackCommands youtrackCommands;
    @Value("${app.telegram.token}")
    private String token;
    @Value("${app.telegram.name}")
    private String name;

    @Autowired
    public HelloController(DefaultBotOptions defaultBotOptions, YoutrackCommands youtrackCommands) {
        super(defaultBotOptions);
        this.youtrackCommands = youtrackCommands;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();

        try {

            String methodName = message.split(" ")[0].replaceAll("/", "");

            Method method = youtrackCommands.getClass().getMethod(methodName, Update.class);

            method.invoke(youtrackCommands, update);

        } catch (NoSuchMethodException e) {
            log.log(Level.WARNING, "Method not found " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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
