package me.chrisochs.redirect;

import me.chrisochs.redirect.commands.ReloadCommand;
import me.chrisochs.redirect.commands.WartungCommand;
import me.chrisochs.redirect.commands.WhitelistCommand;
import me.chrisochs.redirect.listener.LoginHandler;
import me.chrisochs.redirect.listener.PlayerDisconnectEventHandler;
import me.chrisochs.redirect.listener.ProxyPingEventHandler;
import me.chrisochs.redirect.listener.ServerConnectEventHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class Main extends Plugin implements Listener {
	private ConfigurationManager configmanager;
	private LocationManager locmanager;
	private Wartung wartung;

	public void onEnable() {
		configmanager = new ConfigurationManager(this);
		locmanager = new LocationManager(this);
		wartung = new Wartung(this);
		registerListeners();
		registerCommands();
		registerPluginMessageListener();
	}

	public void onDisable() {
		wartung.saveToConfig();
		configmanager.saveConfig();
		locmanager.save();
	}

	private void registerListeners() {
		ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginHandler(this));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyPingEventHandler(this));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerConnectEventHandler(this));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectEventHandler(this));
	}

	private void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new WartungCommand(this));
		getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
		getProxy().getPluginManager().registerCommand(this, new WhitelistCommand(this));
	}
	
	private void registerPluginMessageListener() {
		
	}

	public Configuration getConfig() {
		return configmanager.getConfig();
	}

	public Wartung getWartung() {
		return wartung;
	}
	
	public LocationManager getLocManager() {
		return locmanager;
	}
	
	public void reload() {
		configmanager.reloadConfig();
		wartung = new Wartung(this);
	}
}
