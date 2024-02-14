package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

public class CrashCommand extends Command {
    public CrashCommand() {
        super("crash", "Forcefully crashes the game without java shutdown hook events executing.", "You can use this for duping in singleplayer instead of using task manager",null);
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            executeWindows(logger);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            executeLinux(logger);
        } else if (osName.contains("mac")) {
            executeMac(logger);
        } else {
            logger.error("Unsupported operating system.");
            return Commands.COMMAND_FAILURE;
        }

        return Commands.COMMAND_SUCCESS;
    }

    private void executeWindows(Logger logger) {
        try {
            Runtime.getRuntime().exec("taskkill /f /im java.exe");
            logger.info("Java process forcefully terminated on Windows.");
        } catch (Exception e) {
            logger.error("Error occurred while trying to terminate the Java process on Windows.");
        }
    }

    private void executeLinux(Logger logger) {
        try {
            Runtime.getRuntime().exec("pkill -9 java");
            logger.info("Java process forcefully terminated on Linux.");
        } catch (Exception e) {
            logger.error("Error occurred while trying to terminate the Java process on Linux.");
        }
    }

    private void executeMac(Logger logger) {
        try {
            Runtime.getRuntime().exec("pkill -9 java");
            logger.info("Java process forcefully terminated on macOS.");
        } catch (Exception e) {
            logger.error("Error occurred while trying to terminate the Java process on macOS.");
        }
    }
}