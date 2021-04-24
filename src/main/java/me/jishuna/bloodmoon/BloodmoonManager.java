package me.jishuna.bloodmoon;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class BloodmoonManager {
	private final Bloodmoon plugin;

	private Map<String, BloodmoonData> worldMap = new HashMap<>();

	public BloodmoonManager(Bloodmoon plugin) {
		this.plugin = plugin;
	}

	private void registerWorldData(World world) {
		this.worldMap.putIfAbsent(world.getName(), new BloodmoonData(world));
	}

	public void saveData() {
		File jsonFile = new File(this.plugin.getDataFolder(), "bloodmoon-data.json");
		if (!jsonFile.exists()) {
			try {
				this.plugin.getLogger().info("Bloodmoon data file not found, creating it now...");
				jsonFile.createNewFile();
			} catch (IOException e) {
				this.plugin.getLogger()
						.severe("Encountered " + e.getClass().getSimpleName() + " while creating data file.");
				e.printStackTrace();
			}
		}

		if (jsonFile.exists()) {
			this.plugin.getLogger().info("Bloodmoon data file found, writing save data...");
			saveToFile(jsonFile);
		}
	}

	public void loadData() {
		if (!this.plugin.getDataFolder().exists()) {
			this.plugin.getDataFolder().mkdirs();
		}

		File jsonFile = new File(this.plugin.getDataFolder(), "bloodmoon-data.json");

		if (jsonFile.exists()) {
			loadFromFile(jsonFile);
			this.plugin.getLogger().info("Bloodmoon data file found, loading saved data...");
		} else {
			Bukkit.getWorlds().forEach(world -> registerWorldData(world));
			this.plugin.getLogger().info("Bloodmoon data file not found, generating default data...");
		}
	}

	private void saveToFile(File file) {
		Gson gson = new Gson();
		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(this.worldMap, writer);
		} catch (JsonIOException | IOException e) {
			this.plugin.getLogger().severe("Encountered " + e.getClass().getSimpleName() + " while saving data.");
			e.printStackTrace();
		}
	}

	private void loadFromFile(File file) {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(file)) {
			Type mapType = new TypeToken<Map<String, BloodmoonData>>() {
			}.getType();
			this.worldMap = gson.fromJson(reader, mapType);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			this.plugin.getLogger().severe("Encountered " + e.getClass().getSimpleName() + " while loading data.");
			e.printStackTrace();
		}
	}

}
