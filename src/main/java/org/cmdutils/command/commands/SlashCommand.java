package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class SlashCommand extends Command {
    public SlashCommand() {
        super("/", "Sends a command to the server: \"/ kill\" (with space)", "Syntax \"/ args0[command]\n Example: \"/ kill\" or \" / \"kill @e[type=item]\" \"",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (client.getNetworkHandler() == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 1) {
            client.getNetworkHandler().sendCommand(args[0]);
            logger.info("Sent command.");
            return Commands.COMMAND_SUCCESS;
        }

        logger.error("Invalid Arguments.");

        return Commands.COMMAND_FAILURE;
    }
}
