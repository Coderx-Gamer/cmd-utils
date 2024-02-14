package org.cmdutils.command.commands;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.InGameTerminalGui;
import org.cmdutils.terminal.logger.Logger;
import org.cmdutils.util.Utils;

public class ClickCommand extends Command {
    public ClickCommand() {
        super("click", "Send a ClickSlot packet.", "Syntax: \"click args0[SyncID(int) OR \"current\"] args1[Revision(int) OR \"current\"] args2[Slot (int)] args3[Button (int)] args4[SlotActionType (pickup, quick-move, swap, clone, throw, quick-craft, pickup-all)]\"\n Example: \"click current current 1 2 swap\" (untested example)",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 5) {
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
        Integer revision;
        Integer slot = Utils.toInteger(args[2]);
        Integer button = Utils.toInteger(args[3]);
        SlotActionType type = switch (args[4]) {
            case "pickup" -> SlotActionType.PICKUP;
            case "quick-move" -> SlotActionType.QUICK_MOVE;
            case "swap" -> SlotActionType.SWAP;
            case "clone" -> SlotActionType.CLONE;
            case "throw" -> SlotActionType.THROW;
            case "quick-craft" -> SlotActionType.QUICK_CRAFT;
            case "pickup-all" -> SlotActionType.PICKUP_ALL;
            default -> null;
        };

        if (slot == null) {
            logger.error("Invalid Slot.");
            return Commands.COMMAND_FAILURE;
        }

        if (button == null) {
            logger.error("Invalid Button.");
            return Commands.COMMAND_FAILURE;
        }

        if (type == null) {
            logger.error("Invalid Action Type.");
            return Commands.COMMAND_FAILURE;
        }

        if (args[0].equals("current")) {
            syncId = handler.syncId;
        } else {
            syncId = Utils.toInteger(args[0]);
        }

        if (args[1].equals("current")) {
            revision = handler.getRevision();
        } else {
            revision = Utils.toInteger(args[1]);
        }

        if (syncId == null) {
            logger.error("Invalid Sync ID.");
            return Commands.COMMAND_FAILURE;
        }

        if (revision == null) {
            logger.error("Invalid Revision.");
            return Commands.COMMAND_FAILURE;
        }

        client.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(syncId, revision, slot, button, type, ItemStack.EMPTY, new Int2ObjectArrayMap<>()));
        logger.info("Sent ClickSlot packet.");

        return Commands.COMMAND_SUCCESS;
    }
}
