package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Shows a list of commands.", "Syntax: \"help args0[command] -- OPTIONAL", null);
    }


    //todo (mrbreak): help argument correction
    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0 && args.length != 1) {
            logger.error("Invalid Arguments, usage: 'help'.");
            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 0) {
            logger.println("List of commands:\n");

            for (Command command : Commands.COMMANDS) {
                logger.println("- " + command.getName() + ": " + command.getDescription());
            }
        } else {
            Command command = Commands.find(args[0]);
            if (command == null) {
                logger.error("Help: Unknown Command.");
                return Commands.COMMAND_FAILURE;
            }

            logger.info("Description: " + command.getDescription());
            logger.info("Help: " + command.getHelp());
        }

        return Commands.COMMAND_SUCCESS;
    }
}
