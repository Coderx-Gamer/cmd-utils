package org.cmdutils.command.commands;

import net.minecraft.client.session.Session;
import org.cmdutils.MainClient;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.mixin.accessor.SessionAccessor;
import org.cmdutils.terminal.logger.Logger;

import java.util.UUID;

public class SessionCommand extends Command {
    public SessionCommand() {
        // todo (mrbreak): rewrite this, its garbage
        super("session", "Session ID getter/setter.", "Okay I don't want to rant here, this is powerfull, but annoying to use, you will need to use this like 4 times to actually do anything. \n Syntax: \"session args0[get/set] IF SET args1[\"username\"/\"uuid\"/\"token\"/\"type\"] args2[value of previous switch argument, ex msa, \"MrBreakNFix\", etc]",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length < 1) {
            logger.error("Invalid Arguments.");
            return Commands.COMMAND_FAILURE;
        }

        if (args[0].equals("get")) {
            SessionAccessor session = ((SessionAccessor) client.getSession());
            logger.info("Username: " + session.getUsername());
            logger.info("UUID: " + session.getUuid().toString());
            logger.info("Access Token: " + session.getAccessToken());
            logger.info("Type: " + session.getAccountType().toString().toLowerCase());
            return Commands.COMMAND_SUCCESS;
        }

        if (args[0].equals("set")) {
            if (args.length < 3) {
                logger.error("Not Enough Arguments.");
                return Commands.COMMAND_FAILURE;
            }

            if (args[1].equals("username")) {
                setUsername(args[2]);
                logger.info("Changed username.");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[1].equals("uuid")) {
                if (!setUUID(args[2])) {
                    logger.error("Invalid UUID.");
                    return Commands.COMMAND_FAILURE;
                }
                logger.info("Changed UUID.");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[1].equals("token")) {
                setAccessToken(args[2]);
                logger.info("Changed access token.");
                return Commands.COMMAND_SUCCESS;
            }

            if (args[1].equals("type")) {
                if (!setAccountType(args[2])) {
                    logger.error("Invalid Account Type.");
                    return Commands.COMMAND_FAILURE;
                }
                logger.info("Changed account type.");
                return Commands.COMMAND_SUCCESS;
            }

            logger.error("Invalid Session Field.");

            return Commands.COMMAND_FAILURE;
        }

        logger.error("Invalid Operation.");

        return Commands.COMMAND_FAILURE;
    }

    private static void setUsername(String username) {
        SessionAccessor session = ((SessionAccessor) Command.client.getSession());
        session.setUsername(username);
    }

    private static boolean setUUID(String uuid) {
        SessionAccessor session = ((SessionAccessor) Command.client.getSession());
        try {
            session.setUuid(UUID.fromString(uuid));
            return true;
        } catch (Exception e) {
            MainClient.LOGGER.warn("Failed to set UUID, invalid UUID string input.", e);
            return false;
        }
    }

    private static void setAccessToken(String accessToken) {
        SessionAccessor session = ((SessionAccessor) Command.client.getSession());
        session.setAccessToken(accessToken);
    }
    
    private static boolean setAccountType(String type) {
        Session.AccountType e = switch (type) {
            case "msa" -> Session.AccountType.MSA;
            case "mojang" -> Session.AccountType.MOJANG;
            case "legacy" -> Session.AccountType.LEGACY;
            default -> null;
        };

        if (e == null) {
            return false;
        }

        SessionAccessor session = ((SessionAccessor) Command.client.getSession());
        session.setAccountType(e);

        return true;
    }
}
