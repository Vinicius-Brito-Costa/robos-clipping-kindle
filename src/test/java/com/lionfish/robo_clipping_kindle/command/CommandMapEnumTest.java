package com.lionfish.robo_clipping_kindle.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandMapEnumTest {

    @Test
    void getCommandIsOK(){
        ICommand command = CommandMapEnum.getCommandClass("download");
        Assertions.assertNotNull(command);
        DownloadCommand castedCommand = (DownloadCommand) command;
        Assertions.assertNotNull(castedCommand);
    }
}
