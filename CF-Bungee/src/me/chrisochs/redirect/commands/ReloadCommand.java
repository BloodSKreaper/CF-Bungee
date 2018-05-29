package me.chrisochs.redirect.commands;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
	private Main main;

	public ReloadCommand(Main m) {

		super("reload");
		main = m;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("reload")) {
			main.reload();
			sender.sendMessage(new TextComponent("Plugin neu geladen"));
			return;
		}

	}
}