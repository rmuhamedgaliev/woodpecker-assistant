package io.github.rmuhamedgaliev.woodpecker;

import com.google.gson.Gson;
import io.github.woodpeckeryt.youtracksdk.issue.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class HelloController extends TelegramLongPollingBot {

    @Autowired
    private YoutrackService youtrackService;

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();




//        /token perm:cm11aGFtZWRnYWxpZXY=.NDctMA==.rj70ZJ7eklBifZPuEJXeO1z9Ul5My0
        if (
            update.getMessage().getChat().isGroupChat() == false
            && message.startsWith("/token")
        ) {
            String token = message.split(" ")[1];

            List<Issue> issues = youtrackService.getYouTrackIssues(token);

            SendMessage snd = new SendMessage();
            snd.setChatId(update.getMessage().getChatId());
            snd.setText(new Gson().toJson(issues.get(0)));

            try {
                execute(snd);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getChat().isGroupChat() && message.contains("/token")) {

            String token = message.split(" ")[message.split(" ").length - 1];

            List<Issue> issues = youtrackService.getYouTrackIssues(token);

            SendMessage snd = new SendMessage();
            snd.setChatId(update.getMessage().getChatId());
            snd.setText(new Gson().toJson(issues.get(0)));

            try {
                execute(snd);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }

//        System.out.println(update);
    }

    @Override
    public String getBotUsername() {
        return "youtrack_assistant_bot";
    }

    @Override
    public String getBotToken() {
        return "954633967:AAE8ROwuCjs1LoFiVJOWFj2bnlsblW4v0kM";
    }
}
