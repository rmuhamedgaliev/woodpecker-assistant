/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.rmuhamedgaliev.woodpecker;

import io.github.rmuhamedgaliev.woodpecker.commands.YoutrackCommands;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import io.github.rmuhamedgaliev.woodpecker.repository.YoutrackIssueRepository;
import io.github.woodpeckeryt.youtracksdk.entities.YouTrack;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.PostConstruct;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Log
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.youtrack.host}")
    private String youtrackHost;

    @Value("${app.youtrack.token}")
    private String youtrackToken;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private YoutrackIssueRepository youtrackIssueRepository;

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public YoutrackCommands youtrackCommands() {
        return new YoutrackCommands(defaultBotOptions(), userRepository);
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Citronium", "CitroniumTheBest".toCharArray());
            }
        });

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("psrv5.citronium.com");
        botOptions.setProxyPort(1080);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        return botOptions;

    }

    @Bean
    public YouTrack youTrack() {
        return new YouTrack(youtrackToken, youtrackHost);
    }



}
