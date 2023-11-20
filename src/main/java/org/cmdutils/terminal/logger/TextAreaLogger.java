package org.cmdutils.terminal.logger;

import javax.swing.*;

public class TextAreaLogger implements Logger {
    public JTextArea area;

    public TextAreaLogger(JTextArea area) {
        this.area = area;
    }

    @Override
    public boolean canClear() {
        return true;
    }

    @Override
    public void clear() {
        this.area.setText("");
    }

    @Override
    public void print(String text) {
        this.area.setText(this.area.getText() + text);
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
