package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.DownloadJSONCommand;
import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandValidatorTest {

    @Test
    void validCommand(){
        CommandValidator commandValidator = new CommandValidator(CommandMapEnum.getCommandClass("response"), CommandType.INTERNAL);
        Assertions.assertTrue(commandValidator.validate());
    }

    @Test
    void invalidCommand(){
        CommandValidator nullCommand = new CommandValidator(new Command(null, CommandType.REQUEST), CommandType.INTERNAL);
        Assertions.assertFalse(nullCommand.validate());

        CommandValidator nullType = new CommandValidator(new Command(new DownloadJSONCommand(), null), CommandType.INTERNAL);
        Assertions.assertFalse(nullType.validate());

        CommandValidator invalidType = new CommandValidator(new Command(new DownloadJSONCommand(), CommandType.REQUEST), CommandType.INTERNAL);
        Assertions.assertFalse(invalidType.validate());
    }
}
