package NMSurvivalGames;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class NMSurvivalGames extends JavaPlugin {
	
	private ArenaContainer ac;
	private World spawnworld;

	public void onEnable() {
		ac = new ArenaContainer(this);
	}

	public void onDisable() {
		getAc().shutdown();
	}

	public ArenaContainer getAc() {
		return ac;
	}

	public void setAc(ArenaContainer ac) {
		this.ac = ac;
	}
	
	public World getSpawnWorld() {
		return spawnworld;
	}
}
