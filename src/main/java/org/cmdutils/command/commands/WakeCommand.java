package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class WakeCommand extends Command {
    public WakeCommand() {
        super("wake", "Wake up from a bed client-side.", "Im waking up, I feel it in my bones, enough to make my system blow... But seriously, no arguments",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'wake'.");
            return Commands.COMMAND_FAILURE;
        }

        if (client.player == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        if (client.player.isSleeping()) {
            client.player.wakeUp();
            logger.info("Woke up player (client-side.)");
        } else {
            logger.error("You are not sleeping.");
            return Commands.COMMAND_FAILURE;
        }

        return Commands.COMMAND_SUCCESS;
    }
}
