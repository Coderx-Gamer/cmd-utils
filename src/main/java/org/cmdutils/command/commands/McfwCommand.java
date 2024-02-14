package org.cmdutils.command.commands;

import net.minecraft.network.packet.Packet;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;
import org.cmdutils.util.ClassMap;
import org.cmdutils.util.Mcfw;
import org.cmdutils.util.McfwFilterType;

public class McfwCommand extends Command {
    public McfwCommand() {
        super("mcfw", "Minecraft Firewall packet filter.", "Syntax: \"mcfw args0[quiet-disable, drops packets / status (returns if on or off) / list (lists packets filtered) / delayed (lists amount of delayed packets) / clear (clears packets) / clear-delayed (guess) / block/delay/remove] args1[if args0 was block delay or remove, then this is the type of packet (packet?)]\"",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length == 1) {
            if (args[0].equals("enable")) {
                Mcfw.enabled = true;
                logger.info("Enabled MCFW.");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("disable")) {
                if (client.getNetworkHandler() == null) {
                    logger.error("You are not connected to a server, use quiet-disable instead.");
                    return Commands.COMMAND_FAILURE;
                }
                Mcfw.enabled = false;
                for (Packet<?> packet : Mcfw.delayedPackets) {
                    client.getNetworkHandler().sendPacket(packet);
                }

                logger.info("Disabled MCFW and sent " + Mcfw.delayedPackets.size() + " packets.");
                Mcfw.delayedPackets.clear();
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("quiet-disable")) {
                Mcfw.enabled = false;
                logger.info("Disabled MCFW without sending delayed packets.");
                Mcfw.delayedPackets.clear();
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("status")) {
                logger.info("MCFW is " + (Mcfw.enabled ? "enabled" : "disabled") + ".");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("list")) {
                if (Mcfw.filteredPackets.isEmpty()) {
                    logger.info("There are no filtered packets.");
                    return Commands.COMMAND_SUCCESS;
                }

                logger.println("MCFW Filter:");
                for (String packet : Mcfw.filteredPackets.keySet()) {
                    logger.println("- " + packet + ": " + Mcfw.filteredPackets.get(packet).toString());
                }
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("delayed")) {
                if (Mcfw.enabled) {
                    logger.info("You have " + Mcfw.delayedPackets.size() + " delayed packets.");
                    return Commands.COMMAND_SUCCESS;
                } else {
                    logger.error("MCFW is not enabled.");
                    return Commands.COMMAND_FAILURE;
                }
            }

            if (args[0].equals("clear")) {
                Mcfw.filteredPackets.clear();
                logger.info("Cleared packet filter.");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[0].equals("clear-delayed")) {
                if (Mcfw.enabled) {
                    Mcfw.delayedPackets.clear();
                    logger.info("Cleared delayed packets.");
                    return Commands.COMMAND_SUCCESS;
                } else {
                    logger.error("MCFW is not enabled.");
                    return Commands.COMMAND_FAILURE;
                }
            }

            logger.error("Invalid MCFW Operation.");

            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 2) {
            if (args[0].equals("block")) {
                if (ClassMap.PACKET_MAP.containsValue(args[1]) || args[1].equals("all")) {
                    Mcfw.filteredPackets.put(args[1], McfwFilterType.BLOCKED);
                    logger.info("Added " + args[1] + " to block list.");
                    return Commands.COMMAND_SUCCESS;
                } else {
                    logger.error("Invalid MCFW Packet.");
                    return Commands.COMMAND_FAILURE;
                }
            }

            if (args[0].equals("delay")) {
                if (ClassMap.PACKET_MAP.containsValue(args[1]) || args[1].equals("all")) {
                    Mcfw.filteredPackets.put(args[1], McfwFilterType.DELAYED);
                    logger.info("Added " + args[1] + " to delay list.");
                    return Commands.COMMAND_SUCCESS;
                } else {
                    logger.error("Invalid MCFW Packet.");
                    return Commands.COMMAND_FAILURE;
                }
            }

            if (args[0].equals("remove")) {
                McfwFilterType type = Mcfw.filteredPackets.remove(args[1]);
                if (type == null) {
                    logger.error("Packet Not In Filter.");
                    return Commands.COMMAND_FAILURE;
                }

                logger.info("Removed " + args[1] + " from " + (type == McfwFilterType.BLOCKED ? "block" : "delay") + " list.");
                return Commands.COMMAND_SUCCESS;
            }

            logger.error("Invalid MCFW Operation.");

            return Commands.COMMAND_FAILURE;
        }

        logger.error("Invalid Arguments.");

        return Commands.COMMAND_FAILURE;
    }
}
