/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.rmuhamedgaliev.woodpecker;

import io.github.rmuhamedgaliev.woodpecker.commands.YoutrackCommands;
import io.github.rmuhamedgaliev.woodpecker.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.PostConstruct;

@Log
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

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


        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("127.0.0.1");
        botOptions.setProxyPort(1080);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        return botOptions;
    }
}
