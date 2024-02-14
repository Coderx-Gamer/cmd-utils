package org.cmdutils.command.commands;

import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;
import org.cmdutils.util.Utils;

public class TradeCommand extends Command {
    public TradeCommand() {
        super("trade", "Select villager trade by ID, via SelectMerchantTrade packet.", "Syntax: \"trade args0[tradeId (int)]\"\n ",null);
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

        Integer tradeId = Utils.toInteger(args[0]);

        if (tradeId == null) {
            logger.error("Invalid Trade ID.");
            return Commands.COMMAND_FAILURE;
        }

        client.getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(tradeId));
        logger.info("Sent SelectMerchantTrade packet.");

        return Commands.COMMAND_SUCCESS;
    }
}
