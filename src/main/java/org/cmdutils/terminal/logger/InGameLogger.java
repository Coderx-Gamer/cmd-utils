package org.cmdutils.terminal.logger;

import net.minecraft.client.gui.widget.EditBoxWidget;

public class InGameLogger implements Logger {
    public EditBoxWidget log;

    public InGameLogger(EditBoxWidget log) {
        this.log = log;
    }

    @Override
    public boolean canClear() {
        return true;
    }

    @Override
    public void clear() {
        this.log.setText("");
    }

    @Override
    public void print(String text) {
        this.log.setText(this.log.getText() + text);
    }

    @Override
    public void println(String text) {
        this.print(text + '\n');
    }

    @Override
    public void info(String log) {
        this.println("I: " + log);
    }

    @Override
    public void warn(String log) {
        this.println("W: " + log);
    }

    @Override
    public void error(String log) {
        this.println("E: " + log);
    }

    @Override
    public void error(Throwable throwable) {
        this.error(throwable.toString());
    }

    @Override
    public void error(String log, Throwable throwable) {
        this.error(log + " : " + throwable.toString());
    }
}
