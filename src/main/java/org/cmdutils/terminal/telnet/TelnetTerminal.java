package org.cmdutils.terminal.telnet;

import org.cmdutils.MainClient;
import org.cmdutils.command.*;
import org.cmdutils.terminal.logger.TelnetLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelnetTerminal {
    private List<TelnetSession> sessions = Collections.synchronizedList(new ArrayList<>());
    public TelnetLogger logger;

    public static String reset = "\u001B[0m";
    public static String black = "\u001B[30m";
    public static String red = "\u001B[31m";
    public static String green = "\u001B[32m";
    public static String yellow = "\u001B[33m";
    public static String blue = "\u001B[34m";
    public static String purple = "\u001B[35m";
    public static String cyan = "\u001B[36m";
    public static String white = "\u001B[37m";
    public static String gray = "\u001B[90m";
    public static String brightRed = "\u001B[91m";
    public static String brightGreen = "\u001B[92m";
    public static String brightYellow = "\u001B[93m";
    public static String brightBlue = "\u001B[94m";
    public static String brightPurple = "\u001B[95m";
    public static String brightCyan = "\u001B[96m";
    public static String brightWhite = "\u001B[97m";
    private final List<RunnableCommand> queuedCommands = new ArrayList<>();

    public class TelnetSession implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public TelnetSession(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void open() throws IOException {
            printOut("\n\n\n     ╔═════════════════════════════════════╗");
            printOut("     ║  " + green + "Connected to CMD-Utils via telnet" + reset + "  ║");
            printOut("     ╚═════════════════════════════════════╝\n\n");
            printPrompt();
            TelnetTerminal.this.logger = new TelnetLogger(new ArrayList<>(sessions));
        }

        public void close() throws IOException {
            printOut("Session closed");
            socket.close();
        }

        public void handleCommand(String command) {


            RunnableCommand cmd = CommandParser.parseCommand(command, TelnetTerminal.this.logger, CommandEnvironment.TELNET);
            TelnetTerminal.this.queuedCommands.add(cmd);

            if (command.equals("exit") || (command.equals("^C"))) {
                TelnetTerminal.this.logger.info("Press ^], then ^D to exit a telnet session");
                return;
            }

            for (RunnableCommand c : queuedCommands) {
                if (c == null) {
                    TelnetTerminal.this.logger.error("Invalid Command: " + command);

                    String guessedCommand = guessCommand(command);
                    if (guessedCommand != null && !guessedCommand.isEmpty()) {
                        TelnetTerminal.this.logger.error("Did you mean: " + brightYellow + guessedCommand + "?");
                    } else {
                        TelnetTerminal.this.logger.error("Did you mean: " + brightYellow + "$ " +command + reset + "?");
                    }

                } else {
                    try {
                        c.execute();
                    } catch (Exception ex) {
                        MainClient.LOGGER.error("Exception thrown while executing CMD-Utils command.", ex);
                    }
                }
            }
            queuedCommands.clear();
            printPrompt();
        }


        public void printOut(String text) {
            out.println(text);
            MainClient.LOGGER.info("TELNET: " + text);
        }

        public void printPrompt() {
            out.print(brightCyan + "cmd-utils > " + reset);
            out.flush();
        }

        public void readAndHandleCommand() {
            try {
                String command = in.readLine();
                if (command != null) {
                    handleCommand(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                open();
                while (!socket.isClosed()) {
                    readAndHandleCommand();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public String guessCommand(String inputCommand) {
            List<String> availableCommands = getAvailableCommands();
            String guessedCommand = "";
            int maxMatches = 0;

            for (String command : availableCommands) {
                int matches = 0;
                int minLength = Math.min(inputCommand.length(), command.length());

                // Check for partial matches
                for (int i = 0; i < minLength; i++) {
                    if (inputCommand.charAt(i) == command.charAt(i)) {
                        matches++;
                    } else {
                        break;
                    }
                }

                // Update guessedCommand if the current command has more matches
                if (matches > maxMatches || (matches == maxMatches && command.length() < guessedCommand.length())) {
                    maxMatches = matches;
                    guessedCommand = command;
                }
            }

            return guessedCommand;
        }

        private List<String> getAvailableCommands() {
            List<String> availableCommands = new ArrayList<>();
            for (Command command : Commands.COMMANDS) {
                availableCommands.add(command.getName());
            }
            return availableCommands;
        }
    }

    public void handleConnections(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                TelnetSession session = new TelnetSession(clientSocket);
                sessions.add(session);
                Thread sessionThread = new Thread(session);
                sessionThread

                        .start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}