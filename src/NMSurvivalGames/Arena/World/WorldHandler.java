package NMSurvivalGames.Arena.World;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import NMSurvivalGames.Arena.Arena;

public class WorldHandler {

	private Arena a;
	
	public WorldHandler(Arena a) {
		setA(a);
	}
	
	//
	public void reloadWorldFromFile() {
		reloadWorldFromFile(false);
	}

	public void reloadWorldFromFile(boolean saving) {
		String world_name = getA().getWorld().getName();
		getA().getW().write();
		for(Player p : getA().getWorld().getPlayers()) {
			p.kickPlayer("World is unloading, you were kicked as a precaution. Please log in again.");
		}
		getA().getAc().getMain().getServer().unloadWorld(getA().getWorld(), saving);
		getA().setWorld(getA().getAc().getMain().getServer().createWorld(new WorldCreator(world_name)));
	}
	
	public void setWorldAttribs() {
		World w = getA().getWorld();
		w.setAutoSave(false);
		w.setGameRuleValue("doMobSpawning", "false");
		w.setGameRuleValue("commandBlockOutput","false");
		w.save();
	}
	
	//	
	public void setA(Arena a) {
		this.a = a;
	}
	
	public Arena getA() {
		return a;
	}
}
