package me.chrisochs.redirect.messaging;

import java.util.HashMap;

public class PluginChannelListener implements PluginMessageListener {

	private static HashMap<Player, Object> obj = new HashMap<Player, Object>();

	@Override
	public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {

	}

	public synchronized Object get(Player p, boolean integer) { // here you can add parameters (e.g. String table,
																// String column, ...)
		return null;
	}

}
