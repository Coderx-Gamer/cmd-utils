package org.cmdutils.command;

import net.minecraft.client.MinecraftClient;
import org.cmdutils.terminal.logger.Logger;

public abstract class Command {
    public static final MinecraftClient client = MinecraftClient.getInstance();

    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract int execute(String[] args, Logger logger, CommandEnvironment env);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
