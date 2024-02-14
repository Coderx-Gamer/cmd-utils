package org.cmdutils.command.commands;

import org.cmdutils.command.Command;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.Commands;
import org.cmdutils.terminal.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BashCommand extends Command {
    public BashCommand() {
        super("bash", "Executes a Bash command. Alias: \"$\"", "Syntax: \"bash\"/\"/\" args0[UNIX bash command]\nArguments are accepted, but commands currently run forever. be careful\nExample: \"$ ls /\"", "$");
    }

    @Override
    public int execute(String[] args, Logger logger, CommandEnvironment env) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (!osName.contains("nix") && !osName.contains("nux") && !osName.contains("aix")) {
            logger.error("Bash commands are only supported on Unix-like systems.");
            return Commands.COMMAND_FAILURE;
        }

        if (args.length == 0) {
            logger.error("Usage: \"bash <command>\" OR \"$ <command>\"");
            return Commands.COMMAND_FAILURE;
        }

        String command = String.join(" ", args);

        try {
            Thread commandThread = new Thread(() -> {
                try {
                    Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logger.info(line);
                    }
                    process.waitFor();
                    reader.close();
                } catch (IOException | InterruptedException e) {
                    logger.error("Error executing Bash command: " + e.getMessage());
                }
            });

            commandThread.start();

            commandThread.join(15000);

            if (commandThread.isAlive()) {
                logger.error("Bash command execution timed out after 15 seconds.");
                commandThread.interrupt();
                return Commands.COMMAND_FAILURE;
            } else {
//                logger.info("Bash command executed successfully.");
                return Commands.COMMAND_SUCCESS;
            }
        } catch (InterruptedException e) {
            logger.error("Error executing Bash command: " + e.getMessage());
            return Commands.COMMAND_FAILURE;
        }
    }
}