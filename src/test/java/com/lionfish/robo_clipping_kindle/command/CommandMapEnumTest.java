package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandMapEnumTest {

    @Test
    void getDownloadJsonCommandIsOK(){
        Command command = CommandMapEnum.getCommandClass("download-json");
        Assertions.assertNotNull(command);
        CommandType type = command.getCommandType();
        Assertions.assertNotNull(type);
        Assertions.assertEquals(CommandType.DOWNLOAD, type);
        DownloadJSONCommand castedCommand = (DownloadJSONCommand) command.getCommandClass();
        Assertions.assertNotNull(castedCommand);
    }

    @Test
    void getDownloadDocxCommandIsOK(){
        Command command = CommandMapEnum.getCommandClass("download-docx");
        Assertions.assertNotNull(command);
        CommandType type = command.getCommandType();
        Assertions.assertNotNull(type);
        Assertions.assertEquals(CommandType.DOWNLOAD, type);
        DownloadDocxCommand castedCommand = (DownloadDocxCommand) command.getCommandClass();
        Assertions.assertNotNull(castedCommand);
    }

    @Test
    void getResponseCommandIsOK(){
        Command command = CommandMapEnum.getCommandClass("internal-response");
        Assertions.assertNotNull(command);
        CommandType type = command.getCommandType();
        Assertions.assertNotNull(type);
        Assertions.assertEquals(CommandType.INTERNAL, type);
        ResponseCommand castedCommand = (ResponseCommand) command.getCommandClass();
        Assertions.assertNotNull(castedCommand);
    }

    @Test
    void getCommandIsNotOK(){
        Command command = CommandMapEnum.getCommandClass("invalidCommand");
        Assertions.assertNull(command);
    }
}
