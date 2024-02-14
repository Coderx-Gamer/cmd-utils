package org.cmdutils.terminal.logger;

import org.cmdutils.terminal.telnet.TelnetTerminal;

import java.util.List;

public class TelnetLogger implements Logger {
    private List<TelnetTerminal.TelnetSession> sessions;

    public TelnetLogger(List<TelnetTerminal.TelnetSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean canClear() {
        return true;
    }

    @Override
    public void clear() {
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut("\u001B[2J");
        }
    }

    @Override
    public void print(String text) {
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut(text);
        }
        System.out.print(text);
    }

    @Override
    public void println(String text) {
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut(text);
        }
        System.out.print(text);
    }

    @Override
    public void info(String log) {
        String infoPrefix = TelnetTerminal.yellow + "(I) " + TelnetTerminal.reset;
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut(infoPrefix + log);
        }
        System.out.print(log);

    }

    @Override
    public void warn(String log) {
        String warnPrefix = TelnetTerminal.brightYellow + "(W) " + TelnetTerminal.reset;
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut(warnPrefix + log);
        }
        System.out.print(log);

    }

    @Override
    public void error(String log) {
        String errorPrefix = TelnetTerminal.red + "(E) " + TelnetTerminal.reset;
        for (TelnetTerminal.TelnetSession session : sessions) {
            session.printOut(errorPrefix + log);
        }
        System.out.print(log);

    }

    @Override
    public void error(Throwable throwable) {
        error(throwable.toString());
    }

    @Override
    public void error(String log, Throwable throwable) {
        error(log + " : " + throwable.toString());
    }
}