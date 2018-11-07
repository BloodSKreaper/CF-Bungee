package me.chrisochs.redirect.listener;

import me.chrisochs.redirect.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingEventHandler implements Listener {
	private Main main;

	public ProxyPingEventHandler(Main m) {
		main = m;
	}

	@EventHandler
	public void ProxyPingEvent(ProxyPingEvent e) {
		if (main.getWartung().isEnabled()) {
			e.setResponse(getWartung(e.getResponse()));
			return;
		}
		e.setResponse(getRegular(e.getResponse()));
		return;

	}

	private ServerPing getWartung(ServerPing response) {
		ServerPing answer = response;
		String versionstring = main.getWartung().getVersionString();
		String motd = main.getWartung().getMoTD();
		String playerinfomessage = "";
		for (String s : main.getWartung().getPlayerInfoMessage()) {
			if (playerinfomessage.length() > 1) {
				playerinfomessage = playerinfomessage + "\n" + s;
			} else {
				playerinfomessage = playerinfomessage + s;
			}
		}
		playerinfomessage = ChatColor.translateAlternateColorCodes('&', playerinfomessage);
		PlayerInfo wartung = new PlayerInfo(playerinfomessage, "");
		PlayerInfo[] info = new PlayerInfo[] { wartung };
		Players players = new Players(0, 0, info);
		answer.setPlayers(players);
		answer.setVersion(new ServerPing.Protocol(versionstring, 0));
		motd = ChatColor.translateAlternateColorCodes('&', motd);
		answer.setDescriptionComponent(new TextComponent(motd));
		return answer;

	}

	private ServerPing getRegular(ServerPing response) {
		ServerPing answer = response;
		String versionstring = main.getConfig().getString("versionstring");
		String motd = main.getConfig().getString("motd");
		int protocolversion = main.getConfig().getInt("protocolversion");
		String playerinfomessage = "";

		for (String s : main.getConfig().getStringList("playerinfomessage")) {
			if (playerinfomessage.length() > 1) {
				playerinfomessage = playerinfomessage + "\n" + s;
			} else {
				playerinfomessage = playerinfomessage + s;
			}
		}
		PlayerInfo[] info;
		if (main.getConfig().getBoolean("showPlayersOnList")) {
			info = new PlayerInfo[main.getProxy().getPlayers().size() + 1];
			int counter = 1;
			for (ProxiedPlayer pp : main.getProxy().getPlayers()) {
				PlayerInfo playerinfo = new PlayerInfo(pp.getName(), "");
				info[counter] = playerinfo;
				counter++;
			}
		} else {
			info = new PlayerInfo[1];
		}

		playerinfomessage = ChatColor.translateAlternateColorCodes('&', playerinfomessage);

		PlayerInfo regular = new PlayerInfo(playerinfomessage, "");
		info[0] = regular;

		Players players = new Players(main.getConfig().getInt("maxplayers"), main.getProxy().getOnlineCount(), info);
		answer.setPlayers(players);
		answer.setVersion(new ServerPing.Protocol(versionstring, protocolversion));
		motd = ChatColor.translateAlternateColorCodes('&', motd);
		answer.setDescriptionComponent(new TextComponent(motd));
		return answer;

	}

}
