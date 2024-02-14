package org.cmdutils.command;

import net.minecraft.client.MinecraftClient;
import org.cmdutils.terminal.logger.Logger;

public abstract class Command {
    public static final MinecraftClient client = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final String alias;
    private final String help;

    public Command(String name, String description, String help) {
        this(name, description, null, help);
    }

    public Command(String name, String description, String help, String alias) {
        this.name = name;
        this.description = description;
        this.help = help;
        this.alias = alias;

    }

    public abstract int execute(String[] args, Logger logger, CommandEnvironment env);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getHelp() {
        return this.help;
    }
}