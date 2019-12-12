package io.github.rmuhamedgaliev.woodpecker.commands;

import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

public class YoutrackCommands extends DefaultAbsSender {

    private final UserRepository userRepository;
    @Value("${app.telegram.token}")
    private String token;
    @Autowired
    private YoutrackUserRepository youtrackUserRepository;

    @Autowired
    public YoutrackCommands(DefaultBotOptions options, UserRepository userRepository) {
        super(options);
        this.userRepository = userRepository;
    }

    public void auth(Update update) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(update.getMessage().getChatId());

        Long id = Long.valueOf(update.getMessage().getFrom().getId());
        String name = update.getMessage().getFrom().getFirstName();
        String token = CommandHelper.getCommandMessageContent(update.getMessage().getText());

        Optional<User> userFromDB = userRepository.findById(id);

        if (userFromDB.isPresent()) {
            snd.setText("Do you want change token? please use /updAuth");
        } else {
            User user = new User(id, name, token);
            user = this.userRepository.save(user);
            io.github.woodpeckeryt.youtracksdk.user.User youtrackUser = this.youtrackUserRepository.getMe();
            snd.setText("Success auth YouTrack user with login " + youtrackUser.getLogin());
        }

        execute(snd);
    }

    public void updAuth(Update update) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(update.getMessage().getChatId());

        Long id = Long.valueOf(update.getMessage().getFrom().getId());
        String name = update.getMessage().getFrom().getFirstName();
        String token = CommandHelper.getCommandMessageContent(update.getMessage().getText());

        Optional<User> userFromDB = userRepository.findById(id);

        if (userFromDB.isPresent()) {
            userFromDB.get().setId(id);
            userFromDB.get().setName(name);
            userFromDB.get().setYoutrackToken(token);

            this.userRepository.save(userFromDB.get());
            userFromDB = userRepository.findById(id);
            io.github.woodpeckeryt.youtracksdk.user.User youtrackUser = this.youtrackUserRepository.getMe();
            snd.setText("Success auth YouTrack user with login " + youtrackUser.getLogin());
        } else {
            snd.setText("Do you want change token? please use /updAuth");
        }

        execute(snd);
    }

    public void newIssue(Update update) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(update.getMessage().getChatId());

        Long id = Long.valueOf(update.getMessage().getFrom().getId());
        String name = update.getMessage().getFrom().getFirstName();
        String token = CommandHelper.getCommandMessageContent(update.getMessage().getText());

        Optional<User> userFromDB = userRepository.findById(id);

        if (userFromDB.isPresent()) {
            userFromDB.get().setId(id);
            userFromDB.get().setName(name);
            userFromDB.get().setYoutrackToken(token);

            this.userRepository.save(userFromDB.get());
            userFromDB = userRepository.findById(id);
            io.github.woodpeckeryt.youtracksdk.user.User youtrackUser = this.youtrackUserRepository.getMe();
            snd.setText("Success auth YouTrack user with login " + youtrackUser.getLogin());
        } else {
            snd.setText("Do you want change token? please use /updAuth");
        }

        execute(snd);
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
