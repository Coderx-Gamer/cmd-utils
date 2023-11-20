package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "Send chat message or command.");
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (client.getNetworkHandler() == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 1) {
            client.getNetworkHandler().sendChatMessage(args[0]);
            logger.info("Sent chat message.");
            return Commands.COMMAND_SUCCESS;
        }

        if (args.length == 2) {
            if (args[1].equals("cmd")) {
                client.getNetworkHandler().sendChatCommand(args[0]);
                logger.info("Sent chat command.");
                return Commands.COMMAND_SUCCESS;
            } else {
                logger.info("Invalid Arguments.");
                return Commands.COMMAND_FAILURE;
            }
        }

        logger.error("Invalid Arguments.");

        return Commands.COMMAND_FAILURE;
    }
}
