package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.command.RunnableCommand;
import org.cmdutils.terminal.logger.Logger;
import org.cmdutils.terminal.logger.NullLogger;
import org.cmdutils.util.Utils;

import java.util.Arrays;

public class LoopCommand extends Command {
    public LoopCommand() {
        super("loop", "Loop a command any amount of times.", "Syntax: \"loop args0[commandName] argumentsOfCommandIfNeeded[]... -- OPTIONAL\"",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        if (args.length < 2) {
            logger.error("Invalid Loop Arguments.");
            return Commands.COMMAND_FAILURE;
        }

        Integer times = Utils.toInteger(args[0]);

        if (times == null) {
            logger.error("Invalid Loop Count.");
            return Commands.COMMAND_FAILURE;
        }

        NullLogger nullLogger = new NullLogger();
        for (int i = 0; i < times; i++) {
            new RunnableCommand(Commands.find(args[1]), args.length == 2 ? new String[0] : Arrays.copyOfRange(args, 2, args.length), nullLogger, env).execute();
        }

        logger.info("Finished loop.");

        return Commands.COMMAND_SUCCESS;
    }
}
