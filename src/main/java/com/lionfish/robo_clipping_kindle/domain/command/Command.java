package com.lionfish.robo_clipping_kindle.domain.command;

import com.lionfish.robo_clipping_kindle.command.ICommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

@Data
@AllArgsConstructor
@Generated
public class Command {

    private ICommand commandClass;
    private CommandType commandType;
}
