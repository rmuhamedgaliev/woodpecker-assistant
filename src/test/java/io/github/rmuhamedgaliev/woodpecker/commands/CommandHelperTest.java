package io.github.rmuhamedgaliev.woodpecker.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandHelperTest {

    private CommandHelper commandHelper = new CommandHelper();

    @Test
    void getCommandMessageContentTest() {
        String content = commandHelper.getCommandMessageContent("/auth 1111111 1111");

        assertEquals("1111111 1111", content);
    }
}
