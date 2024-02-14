package org.cmdutils.command.commands;

import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.screen.ScreenHandler;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.cmdutils.terminal.logger.Logger;
import org.cmdutils.util.Utils;

public class ButtonCommand extends Command {
    public ButtonCommand() {
        super("button", "Send a ButtonClick packet.", "Syntax: \"button\" args0[SyncID / current] args1[button (int)]", null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 2) {
            logger.error("Invalid Arguments.");
            return Commands.COMMAND_FAILURE;
        }

        ScreenHandler handler;

        if (env == CommandEnvironment.IN_GAME && client.currentScreen instanceof InGameTerminalGui gui) {
            handler = gui.previousScreenHandler;
        } else {
            if (client.player != null && client.player.currentScreenHandler != null) {
                handler = client.player.currentScreenHandler;
            } else {
                handler = null;
            }
        }

        if (client.getNetworkHandler() == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        if (handler == null) {
            logger.error("You do not have a HandledScreen open.");
            return Commands.COMMAND_FAILURE;
        }

        Integer syncId;
        Integer button = Utils.toInteger(args[1]);

        if (args[0].equals("current")) {
            syncId = handler.syncId;
        } else {
            syncId = Utils.toInteger(args[0]);
        }

        if (syncId == null) {
            logger.error("Invalid Sync ID.");
            return Commands.COMMAND_FAILURE;
        }

        if (button == null) {
            logger.error("Invalid Button.");
            return Commands.COMMAND_FAILURE;
        }

        client.getNetworkHandler().sendPacket(new ButtonClickC2SPacket(syncId, button));
        logger.info("Sent ButtonClick packet.");

        return Commands.COMMAND_SUCCESS;
    }
}
