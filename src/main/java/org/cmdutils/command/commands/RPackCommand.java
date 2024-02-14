package org.cmdutils.command.commands;

import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

import java.util.UUID;

public class RPackCommand extends Command {
    public RPackCommand() {
        super("rpack", "Resource pack bypass tool.", "Syntax: \"rpack args0[accepted/loaded/declined/failed]\" ",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 1) {
            logger.error("Invalid Arguments.");
            return Commands.COMMAND_FAILURE;
        }

        if (client.getNetworkHandler() == null) {
            logger.error("You are not connected to a server.");
            return Commands.COMMAND_FAILURE;
        }

        ResourcePackStatusC2SPacket.Status status = switch (args[0]) {
            case "accepted" -> ResourcePackStatusC2SPacket.Status.ACCEPTED;
            case "loaded" -> ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED;
            case "declined" -> ResourcePackStatusC2SPacket.Status.DECLINED;
            case "failed" -> ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD;
            default -> null;
        };

        if (status == null) {
            logger.error("Invalid resource pack status.");
            return Commands.COMMAND_FAILURE;
        }

        client.getNetworkHandler().sendPacket(new ResourcePackStatusC2SPacket(client.getSession().getUuidOrNull(), status));

        logger.info("Sent resource pack status.");

        return Commands.COMMAND_SUCCESS;
    }
}
