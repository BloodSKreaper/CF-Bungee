package me.chrisochs.redirect.commands;

import java.util.UUID;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class WhitelistCommand extends Command {
	private Main main;

	public WhitelistCommand(Main m) {
		super("whitelist");
		main = m;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("whitelist")) {
			if (args.length == 0) {
				sender.sendMessage(new TextComponent("Whitelisted:"));
				for (UUID uuid : main.getWartung().getWhitelisted()) {
					sender.sendMessage(new TextComponent(uuid.toString()));
				}
			} else if (args.length == 1) {
				sender.sendMessage(new TextComponent("Usage: /whitelist add/remove <UUID>"));
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					String input = args[1];
					if (isValidUUID(input)) {
						UUID uuid = UUID.fromString(input);
						main.getWartung().addToWhitelist(uuid);
						sender.sendMessage(new TextComponent("Added " + uuid.toString() + " to Whitelist!"));
					} else {
						sender.sendMessage(new TextComponent("Invalid UUID!"));
					}

				} else if (args[0].equalsIgnoreCase("remove")) {
					String input = args[1];
					if (isValidUUID(input)) {
						UUID uuid = UUID.fromString(input);
						main.getWartung().removeFromWhitelist(uuid);
						sender.sendMessage(new TextComponent("Removed " + uuid.toString() + " from Whitelist!"));
					} else {
						sender.sendMessage(new TextComponent("Invalid UUID!"));
					}

				} else {
					sender.sendMessage(new TextComponent("Usage: /whitelist add/remove <UUID>"));
				}
			}
		}
	}

	public boolean isValidUUID(String uuid) {
		return uuid.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
	}

}
