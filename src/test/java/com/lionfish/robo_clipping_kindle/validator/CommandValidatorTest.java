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
        CommandValidator commandValidator = new CommandValidator();
        Assertions.assertTrue(commandValidator.validate(CommandMapEnum.getCommandClass("internal-response")));
    }

    @Test
    void invalidCommand(){
        CommandValidator nullCommand = new CommandValidator();
        Assertions.assertFalse(nullCommand.validate(new Command(null, CommandType.EXPORT)));

        CommandValidator nullType = new CommandValidator();
        Assertions.assertFalse(nullType.validate(new Command(new DownloadJSONCommand(), null)));
    }
}
