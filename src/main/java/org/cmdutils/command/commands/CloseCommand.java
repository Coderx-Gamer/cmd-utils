package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class CloseCommand extends Command {
    public CloseCommand() {
        super("close", "Close current GUI without sending a GUI close packet.", "Use this to keep stuff open server-side",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'close'.");
            return Commands.COMMAND_FAILURE;
        }

        client.setScreen(null);

        return Commands.COMMAND_SUCCESS;
    }
}
