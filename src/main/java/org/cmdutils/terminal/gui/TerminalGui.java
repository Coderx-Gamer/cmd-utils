package org.cmdutils.terminal.gui;

import net.minecraft.client.MinecraftClient;
import org.cmdutils.MainClient;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.CommandParser;
import org.cmdutils.command.RunnableCommand;
import org.cmdutils.terminal.logger.TextAreaLogger;
import org.cmdutils.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class TerminalGui {
    public JFrame frame;

    public JTextArea logArea;
    public JScrollPane logScroll;
    public JTextField promptField;

    public TextAreaLogger logger;

    private final List<RunnableCommand> queuedCommands = new ArrayList<>();

    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;

    public TerminalGui() {
        this.init();
    }

    private void init() {
        this.frame = new JFrame("CMD-Utils Terminal");
        this.frame.setBounds(0, 0, 600, 475);
        this.frame.setMinimumSize(new Dimension(300, 190));
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(null);
        this.frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                TerminalGui.this.update();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        Image icon = Toolkit.getDefaultToolkit().getImage(TerminalGui.class.getClassLoader().getResource("assets/cmd-utils/icon.png"));
        if (icon == null) {
            MainClient.LOGGER.warn("Failed to load icon for CMD-Utils terminal GUI (assets/cmd-utils/icon.png)");
        } else {
            this.frame.setIconImage(icon);
        }

        this.logArea = new JTextArea();
        this.logArea.setForeground(Color.WHITE);
        this.logArea.setBackground(Color.BLACK);
        this.logArea.setBorder(BorderFactory.createEmptyBorder());
        this.logArea.setFont(FontUtil.AWT_MONOSPACE);
        this.logArea.setLineWrap(true);
        this.logArea.setEditable(false);
        this.logArea.setFocusable(false);

        this.logScroll = new JScrollPane(this.logArea);
        this.logScroll.setBackground(Color.BLACK);
        this.logScroll.setBorder(BorderFactory.createEmptyBorder());
        this.logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.logger = new TextAreaLogger(this.logArea);

        this.promptField = new JTextField();
        this.promptField.setForeground(Color.WHITE);
        this.promptField.setBackground(Color.BLACK);
        this.promptField.setCaretColor(Color.WHITE);
        this.promptField.setBorder(BorderFactory.createEmptyBorder());
        this.promptField.setFont(FontUtil.AWT_MONOSPACE);
        this.promptField.setEditable(true);
        this.promptField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && (!TerminalGui.this.promptField.getText().isEmpty() || !TerminalGui.this.queuedCommands.isEmpty())) {
                    RunnableCommand command = CommandParser.parseCommand(TerminalGui.this.promptField.getText(), TerminalGui.this.logger, CommandEnvironment.SWING);
                    if (!TerminalGui.this.promptField.getText().isEmpty() && (TerminalGui.this.commandHistory.isEmpty() || !TerminalGui.this.commandHistory.get(TerminalGui.this.commandHistory.size() - 1).equals(TerminalGui.this.promptField.getText()))) {
                        TerminalGui.this.commandHistory.add(TerminalGui.this.promptField.getText());
                    }

                    TerminalGui.this.historyIndex = TerminalGui.this.commandHistory.size();

                    if (!TerminalGui.this.promptField.getText().isEmpty()) {
                        TerminalGui.this.queuedCommands.add(command);
                    }
                    TerminalGui.this.promptField.setText("");

                    if (e.isControlDown()) {
                        return;
                    }

                    for (RunnableCommand c : TerminalGui.this.queuedCommands) {
                        if (c == null) {
                            TerminalGui.this.logger.error("Invalid Command.");
                        } else {
                            MinecraftClient.getInstance().send(() -> {
                                try {
                                    c.execute();
                                } catch (Exception ex) {
                                    MainClient.LOGGER.error("Exception thrown while executing CMD-Utils command.", ex);
                                }
                            });
                        }
                    }

                    TerminalGui.this.queuedCommands.clear();
                }

                if (e.getKeyCode() == KeyEvent.VK_UP && TerminalGui.this.historyIndex > 0) {
                    TerminalGui.this.promptField.setText(TerminalGui.this.commandHistory.get(--TerminalGui.this.historyIndex));
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN && TerminalGui.this.historyIndex < TerminalGui.this.commandHistory.size() - 1) {
                    TerminalGui.this.promptField.setText(TerminalGui.this.commandHistory.get(++TerminalGui.this.historyIndex));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        this.frame.add(this.logScroll);
        this.frame.add(this.promptField);
    }

    private void update() {
        this.logScroll.setBounds(0, 0, this.frame.getWidth(), this.frame.getHeight() - 50);
        this.promptField.setBounds(0, this.frame.getHeight() - 50, this.frame.getWidth(), 20);
    }

    public void show() {
        this.frame.setVisible(true);
    }
}
