package me.chrisochs.redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

public class Wartung {
	private Main plugin;
	private boolean enabled;
	private String versionString;
	private List<String> playerInfoMessage;
	private String kickMessage;
	private List<UUID> whitelisted = new ArrayList<UUID>();
	private String motd;

	public Wartung(Main pl) {
		plugin = pl;
		loadFromConfig();
	}

	private void loadFromConfig() {
		String mainteance = "mainteance.";
		enabled = plugin.getConfig().getBoolean(mainteance + "enabled");
		versionString = plugin.getConfig().getString(mainteance + "versionstring");
		playerInfoMessage = plugin.getConfig().getStringList(mainteance + "playerinfomessage");
		kickMessage = plugin.getConfig().getString(mainteance + "kickmessage");
		kickMessage = ChatColor.translateAlternateColorCodes('&', kickMessage).replaceAll("/n", "\n");
		motd = plugin.getConfig().getString(mainteance + "motd");
		motd = ChatColor.translateAlternateColorCodes('&', motd).replaceAll("/n", "\n");

		loadWhitelistFromStringList(plugin.getConfig().getStringList(mainteance + "whitelist"));
	}

	public void saveToConfig() {
		String mainteance = "mainteance.";
		plugin.getConfig().set(mainteance + "enabled", enabled);
		plugin.getConfig().set(mainteance + "versionstring", versionString);
		plugin.getConfig().set(mainteance + "playerinfomessage", playerInfoMessage);
		plugin.getConfig().set(mainteance + "kickmessage", kickMessage);
		kickMessage = kickMessage.replace("\n", "/n");
		kickMessage = ChatColor.translateAlternateColorCodes('&', kickMessage).replaceAll("/n", "\n");
		motd = motd.replace("\n", "/n");
		plugin.getConfig().set(mainteance + "motd", motd);
		List<String> whitelistedlist = new ArrayList<String>();
		for(UUID uuid: whitelisted) {
			whitelistedlist.add(uuid.toString());
		}
		plugin.getConfig().set(mainteance + "whitelist", whitelistedlist);
	}

	private void loadWhitelistFromStringList(List<String> whitelist) {
		whitelisted.clear();
		for (String s : whitelist) {
			whitelisted.add(UUID.fromString(s));
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getVersionString() {
		return versionString;
	}

	public List<String> getPlayerInfoMessage() {
		return playerInfoMessage;
	}

	public String getKickMessage() {
		return kickMessage;
	}

	public List<UUID> getWhitelisted() {
		return whitelisted;
	}

	public boolean isWhitelisted(UUID uuid) {
		if (whitelisted.contains(uuid))
			return true;
		return false;
	}

	public String getMoTD() {
		return motd;
	}

	public void addToWhitelist(UUID uuid) {
		if (!whitelisted.contains(uuid))
			whitelisted.add(uuid);
	}

	public void removeFromWhitelist(UUID uuid) {
		if (!whitelisted.contains(uuid))
			whitelisted.remove(uuid);
	}

	public void setEnabled() {
		enabled = true;
	}

	public void setDisabled() {
		enabled = false;
	}

}
