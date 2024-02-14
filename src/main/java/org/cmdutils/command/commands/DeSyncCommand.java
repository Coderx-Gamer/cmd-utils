package org.cmdutils.command.commands;

import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.cmdutils.terminal.logger.Logger;

public class DeSyncCommand extends Command {
    public DeSyncCommand() {
        super("desync", "Send a GUI close packet without closing your client-side GUI.", "Be connected to a server with a HandledScreen, like a chest, open",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'desync'.");
            return Commands.COMMAND_FAILURE;
        }

        int syncId;

        if (env == CommandEnvironment.IN_GAME && client.currentScreen instanceof InGameTerminalGui gui) {
            if (gui.previousScreenHandler == null) {
                logger.error("You have no HandledScreen (container) open.");
                return Commands.COMMAND_FAILURE;
            }

            syncId = gui.previousScreenHandler.syncId;
        } else {
            if (client.player == null) {
                logger.error("You are not connected to a server.");
                return Commands.COMMAND_FAILURE;
            }

            if (client.player.currentScreenHandler == null) {
                logger.error("You have no HandledScreen (container) open.");
                return Commands.COMMAND_FAILURE;
            }

            syncId = client.player.currentScreenHandler.syncId;
        }

        if (client.getNetworkHandler() == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        client.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(syncId));
        logger.info("De-sync successful.");

        return Commands.COMMAND_SUCCESS;
    }
}
