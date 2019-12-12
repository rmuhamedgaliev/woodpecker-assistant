package io.github.rmuhamedgaliev.woodpecker.commands;

import io.github.rmuhamedgaliev.woodpecker.core.message.TelegramMessage;
import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackUserRepository;
import io.github.rmuhamedgaliev.woodpecker.security.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    public void auth(TelegramMessage telegramMessage) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(telegramMessage.getChatId());

        Optional<User> userFromDB = userRepository.findById(telegramMessage.getSenderId());

        if (userFromDB.isPresent()) {
            snd.setText("Do you want change token? please use /updAuth");
        } else {
            User user = new User(telegramMessage.getSenderId(), telegramMessage.getSenderName(), token);
            user = this.userRepository.save(user);
            io.github.woodpeckeryt.youtracksdk.user.User youtrackUser = this.youtrackUserRepository.getMe();
            snd.setText("Success auth YouTrack user with login " + youtrackUser.getLogin());
        }

        execute(snd);
    }

    @Auth
    public void updAuth(TelegramMessage telegramMessage) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(telegramMessage.getChatId());

        User userFromDB = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userFromDB.setId(telegramMessage.getSenderId());
        userFromDB.setName(telegramMessage.getSenderName());
        userFromDB.setYoutrackToken(token);

        this.userRepository.save(userFromDB);
        io.github.woodpeckeryt.youtracksdk.user.User youtrackUser = this.youtrackUserRepository.getMe();
        snd.setText("Success auth YouTrack user with login " + youtrackUser.getLogin());
        execute(snd);
    }

    @Auth
    public void newIssue(TelegramMessage telegramMessage) throws TelegramApiException {

        execute(telegramMessage.sendResponse(telegramMessage.getMessage()));
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
