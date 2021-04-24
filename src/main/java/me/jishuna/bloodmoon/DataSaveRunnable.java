package me.jishuna.bloodmoon;

import org.bukkit.scheduler.BukkitRunnable;

public class DataSaveRunnable extends BukkitRunnable {
	private BloodmoonManager manager;

	public DataSaveRunnable(BloodmoonManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		manager.saveData();
	}

}
