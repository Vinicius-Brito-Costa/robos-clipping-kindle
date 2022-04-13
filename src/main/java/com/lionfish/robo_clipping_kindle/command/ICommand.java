package com.lionfish.robo_clipping_kindle.command;
/**
 * With this it will be possible to communicate with any form of integration with a uniform interface
 */
public interface ICommand {
    Object execute(Object object);
}
