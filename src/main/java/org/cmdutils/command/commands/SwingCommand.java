package org.cmdutils.command.commands;

import org.cmdutils.MainClient;
import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.gui.TerminalGui;
import org.cmdutils.terminal.logger.Logger;

public class SwingCommand extends Command {
    public SwingCommand() {
        super("swing", "Open the CMD-Utils (Swing) terminal.", "Telnet is better, open up a (real) terminal and run \"telnet localhost 3300\"",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length != 0) {
            logger.error("Invalid Arguments, usage: 'swing'.");
            return Commands.COMMAND_FAILURE;
        }

        try {
            new TerminalGui().show();
            logger.info("Opened new CMD-Utils (Swing) terminal.");

            return Commands.COMMAND_SUCCESS;
        } catch (Exception e) {
            MainClient.LOGGER.error("Failed to open CMD-Utils (Swing) terminal.", e);
            logger.error("Failed to open CMD-Utils (Swing) terminal", e);

            return Commands.COMMAND_FAILURE;
        }
    }
}
