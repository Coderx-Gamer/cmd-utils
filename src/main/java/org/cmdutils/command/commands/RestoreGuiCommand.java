package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class RestoreGuiCommand extends Command {
    public RestoreGuiCommand() {
        super("restoregui", "Restore your saved GUI.", "No arguments, theres also a keybind for this, make sure you saved a gui first!",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'restoregui'.");
            return Commands.COMMAND_FAILURE;
        }

        if (SaveGuiCommand.savedScreen == null && SaveGuiCommand.savedScreenHandler == null) {
            logger.error("No GUI to restore.");
            return Commands.COMMAND_FAILURE;
        }

        client.setScreen(SaveGuiCommand.savedScreen);
        if (client.player != null) {
            client.player.currentScreenHandler = SaveGuiCommand.savedScreenHandler;
        }

        return Commands.COMMAND_SUCCESS;
    }
}
