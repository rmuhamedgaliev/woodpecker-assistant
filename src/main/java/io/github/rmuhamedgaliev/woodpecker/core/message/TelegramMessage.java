package io.github.rmuhamedgaliev.woodpecker.core.message;

import io.github.rmuhamedgaliev.woodpecker.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramMessage implements Message {
    private final Long senderId;
    private final String senderName;
    private final String message;
    private final Long chatId;
    private final User user;

    public TelegramMessage(Integer senderId, String senderName, String message, Long chatId, User user) {
        this.senderId = Long.valueOf(senderId);
        this.senderName = senderName;
        this.message = message;
        this.chatId = chatId;
        this.user = user;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public Long getChatId() {
        return chatId;
    }

    public SendMessage sendResponse(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(this.chatId);
        sendMessage.setText(message);
        return sendMessage;
    }
}
