package org.cmdutils.command;

import org.cmdutils.terminal.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\"([^\"]*)\"|\\S+");

    public static RunnableCommand parseCommand(String commandString, Logger logger, CommandEnvironment env) {
        List<String> tokens = tokenize(commandString);
        if (tokens.isEmpty()) {
            return null;
        }

        String commandName = tokens.get(0);
        Command command = findCommand(commandName);
        if (command == null) {
            return null;
        }

        List<String> args = tokens.subList(1, tokens.size());
        return new RunnableCommand(command, args.toArray(new String[0]), logger, env);
    }

    private static List<String> tokenize(String command) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = COMMAND_PATTERN.matcher(command);
        while (matcher.find()) {
            String token = matcher.group(1);
            if (token == null) {
                token = matcher.group();
            }
            tokens.add(token);
        }
        return tokens;
    }

    private static Command findCommand(String name) {
        Command command = Commands.find(name);
        if (command == null) {
            for (Command cmd : Commands.COMMANDS) {
                if (cmd.getAlias() != null && cmd.getAlias().equals(name)) {
                    command = cmd;
                    break;
                }
            }
        }
        return command;
    }
}