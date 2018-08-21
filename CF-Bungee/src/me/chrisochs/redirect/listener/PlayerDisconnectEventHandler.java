package me.chrisochs.redirect.listener;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectEventHandler implements Listener {
	private Main main;

	public PlayerDisconnectEventHandler(Main m) {
		main = m;
	}

	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent e) {
		if (e.getPlayer().getServer() != null) {
			main.getLocManager().addLocation(e.getPlayer().getUniqueId(),
					e.getPlayer().getServer().getInfo().getName());
		}
	}

}
