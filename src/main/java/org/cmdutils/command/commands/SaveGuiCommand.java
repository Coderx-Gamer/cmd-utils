package org.cmdutils.command.commands;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.cmdutils.terminal.logger.Logger;

public class SaveGuiCommand extends Command {
    public static Screen savedScreen = null;
    public static ScreenHandler savedScreenHandler = null;

    public SaveGuiCommand() {
        super("savegui", "Saves your GUI so it can be restored.", "Use the restore gui command or keybind with this.",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'savegui'.");
            return Commands.COMMAND_FAILURE;
        }

        if (env == CommandEnvironment.IN_GAME && client.currentScreen instanceof InGameTerminalGui gui) {
            savedScreen = gui.previousScreen;
            savedScreenHandler = gui.previousScreenHandler;
        }

        if (env == CommandEnvironment.SWING) {
            savedScreen = client.currentScreen;
            if (client.player != null) {
                savedScreenHandler = client.player.currentScreenHandler;
            }
        }

        if (savedScreen == null && savedScreenHandler == null) {
            logger.error("No GUI to save.");
            return Commands.COMMAND_FAILURE;
        }

        logger.info("Saved GUI.");

        return Commands.COMMAND_SUCCESS;
    }
}
