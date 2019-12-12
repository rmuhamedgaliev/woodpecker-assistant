package io.github.rmuhamedgaliev.woodpecker;

import io.github.rmuhamedgaliev.woodpecker.commands.CommandHelper;
import io.github.rmuhamedgaliev.woodpecker.commands.YoutrackCommands;
import io.github.rmuhamedgaliev.woodpecker.core.message.TelegramMessage;
import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.security.Auth;
import io.github.rmuhamedgaliev.woodpecker.service.YoutrackService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

@Log
@Component
public class TelegramController extends TelegramLongPollingBot {

    private final YoutrackCommands youtrackCommands;
    private final YoutrackService youtrackService;


    @Value("${app.telegram.token}")
    private String token;
    @Value("${app.telegram.name}")
    private String name;

    @Autowired
    public TelegramController(DefaultBotOptions defaultBotOptions, YoutrackCommands youtrackCommands, YoutrackService youtrackService) {
        super(defaultBotOptions);
            this.youtrackCommands = youtrackCommands;
        this.youtrackService = youtrackService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();

        try {

            String methodName = message.split(" ")[0].replaceAll("/", "");

            Method method = youtrackCommands.getClass().getMethod(methodName, TelegramMessage.class);

            youtrackService.checkTelegramUserHasYoutrackAccount(
                update.getMessage().getFrom().getId()
            );


            User user = null;
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }

            TelegramMessage telegramMessage = new TelegramMessage(
                update.getMessage().getFrom().getId(),
                update.getMessage().getFrom().getFirstName(),
                CommandHelper.getCommandMessageContent(update.getMessage().getText()),
                update.getMessage().getChatId(),
                user);

            if (method.isAnnotationPresent(Auth.class)) {
                method.invoke(youtrackCommands, telegramMessage);
            } else {
                method.invoke(youtrackCommands, telegramMessage);
            }

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
