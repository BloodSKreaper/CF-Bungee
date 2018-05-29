package me.chrisochs.redirect;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class LocationManager {
	private Plugin plugin;
	private Configuration config;
	private File configFile;
	private File datafolder;
	private HashMap<UUID, String> locations = new HashMap<UUID, String>();

	public void addLocation(UUID uuid, String server) {
		locations.put(uuid, server);
	}
	

	public LocationManager(Plugin pl) {
		plugin = pl;
		datafolder = plugin.getDataFolder();
		loadConfigFile(datafolder);
		loadConfig();
		initializeLocation();
	}
	
	public void initializeLocation() {
		for(String s:config.getKeys()) {
			UUID uuid = UUID.fromString(s);
			String servername = config.getString(s);
			locations.put(uuid, servername);
			
		}
	}

	private void loadConfigFile(File datafolder) {
		File folder = datafolder;
		if (!folder.exists())
			folder.mkdir();
		configFile = new File(folder, "location.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadConfig() {
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException ee) {
			System.out.println("Error at loading Config from File");
			ee.printStackTrace();
		}
	}

	public void save() {
		config.set("", null);
		for(UUID uuid: locations.keySet()) {
			String uuidstring = uuid.toString();
			String server = locations.get(uuid);
			config.set(uuidstring, server);
		}
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException ee) {
			System.out.println("Error at saving Config File");
			ee.printStackTrace();
		}
	}

	public String getServerName(UUID uuid) {
		System.out.println(locations.get(uuid));
		return locations.get(uuid);
	}

}
