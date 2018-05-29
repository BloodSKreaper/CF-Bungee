package me.chrisochs.redirect.commands;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class WartungCommand extends Command {
	private Main main;

	public WartungCommand(Main m) {

		super("wartung");
		main = m;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("wartung")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					main.getWartung().setEnabled();
					sender.sendMessage(new TextComponent("Wartungsmodus aktiviert"));
					return;
				}
				if (args[0].equalsIgnoreCase("off")) {
					main.getWartung().setDisabled();
					sender.sendMessage(new TextComponent("Wartungsmodus deaktiviert"));
					return;
				}
			}
			sender.sendMessage(new TextComponent("Wrong usage: /wartung on|off"));
		}

	}
}
