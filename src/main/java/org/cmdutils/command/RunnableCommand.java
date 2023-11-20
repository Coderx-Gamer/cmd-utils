package org.cmdutils.command;

import org.cmdutils.terminal.logger.Logger;

public record RunnableCommand(Command command, String[] args, Logger logger, CommandEnvironment env) {
    public int execute() {
        return this.command().execute(this.args(), this.logger(), this.env());
    }
}
