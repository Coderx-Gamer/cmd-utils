package org.cmdutils.command.commands;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.text.Text;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class LeaveCommand extends Command {
    public LeaveCommand() {
        super("leave", "Leave the server.", "Just be on a server / in a world before doing this.",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'leave'.");
            return Commands.COMMAND_FAILURE;
        }

        if (client.getNetworkHandler() == null || client.world == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        client.world.disconnect();
        client.disconnect();
        client.setScreen(new DisconnectedScreen(null, Text.of("Disconnected"), Text.of("Disconnected by CMD-Utils")));

        return Commands.COMMAND_SUCCESS;
    }
}
