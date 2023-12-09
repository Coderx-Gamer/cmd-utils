# CMD Utils

CMD-Utils is a FabricMC Minecraft modification used for debugging mods, plugins, and servers 
using a command line with many features useful for glitch hunting.

---

## About

CMD-Utils was created due to issues with maintaining UI-Utils, CMD-Utils is able to do 
everything that UI-Utils can and more. CMD-Utils as well as UI-Utils will be updated 
to the latest version of Minecraft as quickly as possible. This mod is not intended to 
kill UI-Utils.

---

## Contact

Discord Server:\
https://discord.gg/NaKb65CEZn

Coderx Gamer (owner):\
Discord: public_static_void_main_args\
Email: [coderx@mailfence.com](mailto:coderx@mailfence.com)

MrBreakNFix (developer):\
Discord: mrbreaknfix

---

## Building

CMD-Utils does not distribute any binaries of itself, as it is harder to maintain 
and less safe. You must build CMD-Utils from source to use it.

To build CMD-Utils on Linux, run these commands in a terminal:

```shell
git clone https://github.com/Coderx-Gamer/cmd-utils.git
cd ./cmd-utils/
chmod +x ./gradlew
./gradlew build
```

To build on Windows, click the green "Code" button and click "Download ZIP,"
extract it, go into that folder in File Explorer, then do SHIFT+Right Click 
and click "Open PowerShell window here," in that window, type `./gradlew build`.

Now that you built CMD-Utils, your jar file should be located at `./build/libs/cmd-utils-<version>.jar`

---

## Documentation

### Using the terminal

To get started with CMD-Utils, open a terminal by pressing the keybinding in the controls 
menu (Y by default) and if you are in a GUI, do CTRL+Y. When you close this GUI it will 
go back to whatever GUI you had open before (or none if you opened it without a GUI open.)

You can enter a command into the terminal by typing it and pressing enter, and scroll through 
your command history with the up and down arrow keys.

If you want to send multiple commands at once, press CTRL+Enter while sending the command, 
and when you are done, send the last command with only the enter key.

If you prefer an external terminal window, you can run the 'swing' command to open a new 
one using Java Swing.

### Commands

There are many commands in CMD-Utils, which will be listed here, there will be more commands 
added in the future.

[help](./docs/help.md) - Show the help menu.\
[cmdutils](./docs/cmdutils.md) - Show CMD-Utils information\
[clear](./docs/clear.md) - Clear the log.\
[swing](./docs/swing.md) - Open the Java Swing terminal.\
[loop](./docs/loop.md) - Loop a command any amount of times.\
[leave](./docs/leave.md) - Leave a server or single player world.\
[menuinfo](./docs/menuinfo.md) - Show GUI information.\
[savegui](./docs/savegui.md) - Save your current GUI, so it can be restored later.\
[restoregui](./docs/restoregui.md) - Restore your saved GUI.\
[close](./docs/close.md) - Close your GUI without sending a packet.\
[desync](./docs/desync.md) - Send a CloseHandledScreen packet without closing your GUI (client-side.)\
[chat](./docs/chat.md) - Send a chat message or command.\
[rpack](./docs/rpack.md) - Resource pack bypass tool.\
[wake](./docs/wake.md) - Wake up from a bed (client-side.)\
[drop](./docs/drop.md) - Drop your selected item.\
[trade](./docs/trade.md) - Select a villager trade by ID.\
[button](./docs/button.md) - Send a ButtonClick packet.\
[click](./docs/click.md) - Send a ClickSlot packet.\
[mcfw](./docs/mcfw.md) - Minecraft Firewall packet filter.\
[session](./docs/session.md) - Session ID getter/setter.

---
