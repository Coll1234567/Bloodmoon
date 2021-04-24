package me.jishuna.bloodmoon;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.FileUtils;

public class Bloodmoon extends JavaPlugin {

	private BloodmoonManager manager;
	private DataSaveRunnable saveRunnable;

	private YamlConfiguration config;
	private Map<String, ConfigurationSection> worldConfigMap = new HashMap<>();

	@Override
	public void onEnable() {
		loadConfiguration();

		this.manager = new BloodmoonManager(this);
		this.manager.loadData();

		this.saveRunnable = new DataSaveRunnable(this.manager);

		int interval = this.config.getInt("data-save-interval", 10) * 20 * 60;
		this.saveRunnable.runTaskTimerAsynchronously(this, interval, interval);
	}

	@Override
	public void onDisable() {
		this.manager.saveData();

		if (this.saveRunnable != null && !this.saveRunnable.isCancelled()) {
			this.saveRunnable.cancel();
		}
	}

	public YamlConfiguration getConfig() {
		return this.config;
	}

	public Optional<ConfigurationSection> getWorldConfig(World world) {
		return Optional.ofNullable(this.worldConfigMap.get(world.getName()));
	}

	private void loadConfiguration() {
		Optional<File> configOptional = FileUtils.copyResource(this, "config.yml");
		if (configOptional.isPresent()) {
			this.config = YamlConfiguration.loadConfiguration(configOptional.get());

			ConfigurationSection worldSection = this.config.getConfigurationSection("worlds");

			for (String worldName : worldSection.getKeys(false)) {
				this.worldConfigMap.put(worldName, worldSection.getConfigurationSection(worldName));
			}
		}
	}
}
