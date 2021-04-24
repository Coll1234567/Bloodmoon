package me.jishuna.bloodmoon;

import org.bukkit.World;

public class BloodmoonData {

	private boolean enabled = false;
	private boolean forced = false;

	private int defaultSpawnLimit = 0;

	public BloodmoonData() {
	}

	public BloodmoonData(World world) {
		this.defaultSpawnLimit = world.getMonsterSpawnLimit();
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isForced() {
		return this.forced;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setForced(boolean forced) {
		this.forced = forced;
	}

	public int getDefaultSpawnLimit() {
		return this.defaultSpawnLimit;
	}
}
