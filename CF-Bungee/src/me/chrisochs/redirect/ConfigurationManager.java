package me.chrisochs.redirect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigurationManager {
	private Plugin plugin;
	private Configuration config;
	private File configFile;
	private File datafolder;

	public ConfigurationManager(Plugin pl) {
		plugin = pl;
		datafolder = plugin.getDataFolder();
		loadConfigFile(datafolder);
		loadConfig();

	}

	public void loadConfigFile(File datafolder) {
		if (!datafolder.exists())
			datafolder.mkdir();
		configFile = new File(datafolder, "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				try (InputStream is = plugin.getResourceAsStream("config.yml");
						OutputStream os = new FileOutputStream(configFile)) {
					ByteStreams.copy(is, os);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void loadConfig() {
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException ee) {
			System.out.println("Error at loading Config from File");
			ee.printStackTrace();
		}
	}
	
	public void reloadConfig() {
		loadConfigFile(datafolder);
		loadConfig();
	}

	public void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException ee) {
			System.out.println("Error at saving Config File");
			ee.printStackTrace();
		}
	}
	
	public Configuration getConfig() {
		return config;
	}

}
