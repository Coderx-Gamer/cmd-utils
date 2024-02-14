package org.cmdutils.command.commands;

import org.cmdutils.ModInfo;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class CmdUtilsCommand extends Command {
    public CmdUtilsCommand() {
        super("cmdutils", "Shows CMD Utils information.", "Reach out to authors for issues or help",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'cmdutils'.");
            return Commands.COMMAND_FAILURE;
        }

        logger.println("CMD-Utils " + ModInfo.VERSION + '\n');
        logger.println("License: " + ModInfo.LICENSE);
        logger.println("Source: " + ModInfo.SOURCE);
        logger.println("Authors:");

        for (String author : ModInfo.AUTHORS) {
            logger.println("- " + author);
        }

        return Commands.COMMAND_SUCCESS;
    }
}
