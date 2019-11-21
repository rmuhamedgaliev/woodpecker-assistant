package io.github.rmuhamedgaliev.woodpecker.commands;

public final class CommandHelper {

    public static String getCommandMessageContent(String message) {
        int firstSpaceIndex = message.indexOf(' ') + 1;
        return message.substring(firstSpaceIndex).strip();
    }
}
