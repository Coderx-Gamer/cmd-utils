package org.cmdutils.terminal.logger;

public interface Logger {
    boolean canClear();
    void clear();
    void print(String text);
    void println(String text);
    void info(String log);
    void warn(String log);
    void error(String log);
    void error(Throwable throwable);
    void error(String log, Throwable throwable);
}