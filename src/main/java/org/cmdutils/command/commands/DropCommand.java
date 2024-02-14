package org.cmdutils.command.commands;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class DropCommand extends Command {
    public DropCommand() {
        super("drop", "Drop an item in your hotbar.", "Syntax: \"drop args0[\"all\"] -- OPTIONAL",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (!(client.getNetworkHandler() != null && client.player != null)) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 0) {
            client.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            logger.info("Sent PlayerAction packet.");
            return Commands.COMMAND_SUCCESS;
        }

        if (args.length == 1) {
            if (!args[0].equals("all")) {
                logger.error("Invalid Arguments.");
                return Commands.COMMAND_FAILURE;
            }

            client.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
            logger.info("Sent PlayerAction packet.");
            return Commands.COMMAND_SUCCESS;
        }

        logger.error("Invalid Arguments.");

        return Commands.COMMAND_FAILURE;
    }
}
