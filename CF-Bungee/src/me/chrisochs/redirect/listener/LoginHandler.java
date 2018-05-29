package me.chrisochs.redirect.listener;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.md_5.bungee.api.event.LoginEvent;

public class LoginHandler implements Listener {
	private Main main;

	public LoginHandler(Main m) {
		main = m;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void LoginEvent(LoginEvent event) {		
		if (main.getWartung().isEnabled()&& !main.getWartung().isWhitelisted(event.getConnection().getUniqueId())) {
			String message = main.getWartung().getKickMessage().replace("/n", "\n");
			message = ChatColor.translateAlternateColorCodes('&', message);
			event.getConnection().disconnect(new TextComponent(message));
		}
		if(main.getConfig().getInt("protocolversion") != event.getConnection().getVersion()) {
			String kick = main.getConfig().getString("wrongversionkick");
			kick = ChatColor.translateAlternateColorCodes('&', kick);
			event.getConnection().disconnect(new TextComponent(main.getConfig().getString("wrongversionkick")));
		}
	}

}
