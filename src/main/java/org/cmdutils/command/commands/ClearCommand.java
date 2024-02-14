package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "Clear the log.", "No arguments", null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (logger.canClear()) {
            logger.clear();
            return Commands.COMMAND_SUCCESS;
        } else {
            logger.error("Clearing Not Supported.");
            return Commands.COMMAND_FAILURE;
        }
    }
}
