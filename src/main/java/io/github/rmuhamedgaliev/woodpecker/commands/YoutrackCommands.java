package io.github.rmuhamedgaliev.woodpecker.commands;

import io.github.rmuhamedgaliev.woodpecker.core.message.TelegramMessage;
import io.github.rmuhamedgaliev.woodpecker.model.User;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackIssueRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackUserRepository;
import io.github.rmuhamedgaliev.woodpecker.security.Auth;
import io.github.woodpeckeryt.youtracksdk.issue.Issue;
import io.github.woodpeckeryt.youtracksdk.issue.dto.IssueCreateCustomFieldDTO;
import io.github.woodpeckeryt.youtracksdk.issue.dto.IssueCreateDTO;
import io.github.woodpeckeryt.youtracksdk.issue.dto.IssueCreateProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YoutrackCommands extends DefaultAbsSender {

    private final UserRepository userRepository;

    @Value("${app.telegram.token}")
    private String token;

    @Value("${app.youtrack.infra-project}")
    private String projectId;

    @Autowired
    private YoutrackUserRepository youtrackUserRepository;
    @Autowired
    private YoutrackIssueRepository youtrackIssueRepository;

    @Autowired
    public YoutrackCommands(DefaultBotOptions options, UserRepository userRepository) {
        super(options);
        this.userRepository = userRepository;
    }

    public void auth(TelegramMessage telegramMessage) throws TelegramApiException {

        SendMessage snd = new SendMessage();
        snd.setChatId(telegramMessage.getChatId().toString());

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
        snd.setChatId(telegramMessage.getChatId().toString());

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
    public void infra(TelegramMessage telegramMessage) throws TelegramApiException {

        IssueCreateProjectDTO issueCreateProjectDTO = new IssueCreateProjectDTO(projectId);

        List<IssueCreateCustomFieldDTO> customFieldDTOS = new ArrayList<>();
        customFieldDTOS.add(
            new IssueCreateCustomFieldDTO(
                "SimpleIssueCustomField",
                "ChatId",
                telegramMessage.getChatId()
            )

        );

        IssueCreateDTO issueCreateDTO = new IssueCreateDTO(
            "summary",
            "description",
            issueCreateProjectDTO,
            customFieldDTOS
        );

        Issue issue = youtrackIssueRepository.createIssue(issueCreateDTO);

        System.out.println("11");

        execute(telegramMessage.sendResponse(issue.getSummary()));
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
