package org.cmdutils.command;

import org.cmdutils.command.commands.*;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    public static final int COMMAND_SUCCESS = 0;
    public static final int COMMAND_FAILURE = 1;

    public static final List<Command> COMMANDS = new ArrayList<>();

    public static void registerCommand(Command command) {
        COMMANDS.add(command);
    }

    public static Command find(String name) {
        for (Command command : COMMANDS) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }

    static {
        registerCommand(new ClearCommand());
        registerCommand(new HelpCommand());
        registerCommand(new CmdUtilsCommand());
        registerCommand(new SwingCommand());
        registerCommand(new CloseCommand());
        registerCommand(new DeSyncCommand());
        registerCommand(new MenuInfoCommand());
        registerCommand(new SaveGuiCommand());
        registerCommand(new RestoreGuiCommand());
        registerCommand(new LeaveCommand());
        registerCommand(new WakeCommand());
        registerCommand(new McfwCommand());
        registerCommand(new RPackCommand());
        registerCommand(new SessionCommand());
        registerCommand(new ChatCommand());
        registerCommand(new ClickCommand());
        registerCommand(new ButtonCommand());
        registerCommand(new TradeCommand());
        registerCommand(new LoopCommand());
        registerCommand(new DropCommand());
        registerCommand(new CrashCommand());
        registerCommand(new BashCommand());
        registerCommand(new SlashCommand());
//        registerCommand(new RespawnCommand());
//        registerCommand(new JoinServerCommand());
//        registerCommand(new QuitGameCommand()); // pov too lazy to press "Quit Game"



    }
}
