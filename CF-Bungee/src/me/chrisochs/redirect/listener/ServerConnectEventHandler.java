package me.chrisochs.redirect.listener;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectEventHandler implements Listener {

	private Main main;

	public ServerConnectEventHandler(Main m) {
		main = m;

	}

	@EventHandler
	public void ServerConnectEvent(ServerConnectEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if (player.getServer() == null) {
			ServerInfo reconnectserver = main.getProxy().getServerInfo(main.getLocManager().getServerName(player.getUniqueId()));
			if (reconnectserver != null
					&& main.getProxy().getServers().containsValue(reconnectserver)) {
				e.setTarget(reconnectserver);
			} else {
				ServerInfo fallback = main.getProxy().getServers().get(main.getConfig().getString("fallbackserver"));
				if (fallback != null) {
					e.setTarget(fallback);
				} else {
					ServerInfo replace = main.getProxy().getServers().values().iterator().next();
					e.setTarget(replace);
				}
			}
		}

		if (main.getWartung().isEnabled() == true) {
			player.sendMessage(new TextComponent("§6------------------------------------"));
			player.sendMessage(new TextComponent("§4DERZEIT IST DER WARTUNGSMODUS AKTIV!"));
			player.sendMessage(new TextComponent("§6------------------------------------"));
		}
	}

}
