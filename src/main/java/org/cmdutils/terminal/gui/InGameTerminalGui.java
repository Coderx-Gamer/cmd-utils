package org.cmdutils.terminal.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.cmdutils.MainClient;
import org.cmdutils.command.CommandEnvironment;
import org.cmdutils.command.CommandParser;
import org.cmdutils.command.RunnableCommand;
import org.cmdutils.terminal.logger.InGameLogger;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class InGameTerminalGui extends Screen {
    public EditBoxWidget log;
    public InGameLogger logger;

    public TextFieldWidget prompt;

    public final Screen previousScreen;
    public final ScreenHandler previousScreenHandler;

    private final List<RunnableCommand> queuedCommands = new ArrayList<>();

    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1;

    private final Text logText = Text.of("");
    private final Text promptText = Text.of("");

    public InGameTerminalGui(Screen previousScreen, ScreenHandler previousScreenHandler) {
        super(Text.of("CMD-Utils Terminal"));

        this.previousScreen = previousScreen;
        this.previousScreenHandler = previousScreenHandler;
    }

    @Override
    public void init() {
        this.log = new EditBoxWidget(
                this.textRenderer,
                (this.width / 2) - this.width / 4,
                this.height / 4,
                this.width / 2,
                this.height / 3,
                Text.of(""),
                this.logText
        ) {
            @Override
            public boolean charTyped(char chr, int modifiers) {
                return false;
            }
        };

        this.logger = new InGameLogger(this.log);

        this.prompt = new TextFieldWidget(
                this.textRenderer,
                (this.width / 2) - this.width / 4,
                (this.height / 3) + this.height / 4 + 10,
                this.width / 2,
                20,
                this.promptText
        ) {
            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                if (keyCode == GLFW.GLFW_KEY_ENTER && (!this.getText().isEmpty() || !InGameTerminalGui.this.queuedCommands.isEmpty())) {
                    RunnableCommand command = CommandParser.parseCommand(this.getText(), InGameTerminalGui.this.logger, CommandEnvironment.IN_GAME);
                    if (!this.getText().isEmpty() && (InGameTerminalGui.this.commandHistory.isEmpty() || !InGameTerminalGui.this.commandHistory.get(InGameTerminalGui.this.commandHistory.size() - 1).equals(this.getText()))) {
                        InGameTerminalGui.this.commandHistory.add(this.getText());
                    }

                    InGameTerminalGui.this.historyIndex = InGameTerminalGui.this.commandHistory.size();

                    if (!this.getText().isEmpty()) {
                        InGameTerminalGui.this.queuedCommands.add(command);
                    }
                    this.setText("");

                    if (hasControlDown()) {
                        return false;
                    }

                    for (RunnableCommand c : InGameTerminalGui.this.queuedCommands) {
                        if (c == null) {
                            InGameTerminalGui.this.logger.error("Invalid Command.");
                        } else {
                            try {
                                c.execute();
                            } catch (Exception ex) {
                                MainClient.LOGGER.error("Exception thrown while executing CMD-Utils command.", ex);
                            }
                        }
                    }

                    InGameTerminalGui.this.queuedCommands.clear();

                    return false;
                }

                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        };
        this.prompt.setMaxLength(4096);

        this.addDrawableChild(this.log);
        this.addDrawableChild(this.prompt);

        this.setInitialFocus(this.prompt);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.prompt.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_UP && InGameTerminalGui.this.historyIndex > 0) {
                this.prompt.setText(InGameTerminalGui.this.commandHistory.get(--InGameTerminalGui.this.historyIndex));
            }

            if (keyCode == GLFW.GLFW_KEY_DOWN && InGameTerminalGui.this.historyIndex < InGameTerminalGui.this.commandHistory.size() - 1) {
                this.prompt.setText(InGameTerminalGui.this.commandHistory.get(++InGameTerminalGui.this.historyIndex));
            }

            if (keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_DOWN) {
                return false;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, "CMD-Utils Terminal", this.width / 2, this.height / 4 - 20, 0xFFFFFF);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.previousScreen);
            if (this.client.player != null) {
                this.client.player.currentScreenHandler = this.previousScreenHandler;
            }
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
