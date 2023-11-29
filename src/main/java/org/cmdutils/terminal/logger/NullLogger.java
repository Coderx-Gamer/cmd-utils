package org.cmdutils.terminal.logger;

public class NullLogger implements Logger {

    @Override
    public boolean canClear() {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public void print(String text) {
    }

    @Override
    public void println(String text) {
    }

    @Override
    public void info(String log) {
    }

    @Override
    public void warn(String log) {
    }

    @Override
    public void error(String log) {
    }

    @Override
    public void error(Throwable throwable) {
    }

    @Override
    public void error(String log, Throwable throwable) {
    }
}
