package org.cmdutils.command.commands;

import com.google.gson.JsonSerializationContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.cmdutils.terminal.logger.Logger;

public class MenuInfoCommand extends Command {
    public MenuInfoCommand() {
        // todo: get MenuInfo to actually do something
        super("menuinfo", "Shows details of your current screen.", "honestly i never got this to work, gl mate!",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'menuinfo'.");
            return Commands.COMMAND_FAILURE;
        }

        Screen screen = null;
        ScreenHandler handler = null;

        if (env == CommandEnvironment.IN_GAME && client.currentScreen instanceof InGameTerminalGui gui) {
            screen = gui.previousScreen;
            handler = gui.previousScreenHandler;
        }

        if (env == CommandEnvironment.SWING) {
            screen = client.currentScreen;
            if (client.player != null) {
                handler = client.player.currentScreenHandler;
            }
        }

        if (screen == null) {
            logger.warn("Screen is null.\n");
        } else {
            logger.info("Screen\n");
            logger.info("Title JSON: " + Text.Serialization.toJsonString(screen.getTitle()));
            logger.info("Width: " + screen.width);
            logger.info("Height: " + screen.height + '\n');
        }

        if (handler == null) {
            logger.warn("ScreenHandler is null.\n");
        } else {
            logger.info("ScreenHandler\n");
            logger.info("Sync ID: " + handler.syncId);
            logger.info("Revision: " + handler.getRevision());
            logger.info("Slots: " + handler.slots.size() + '\n');
        }

        return Commands.COMMAND_SUCCESS;
    }
}
