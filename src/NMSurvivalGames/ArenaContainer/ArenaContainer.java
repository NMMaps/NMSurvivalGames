package NMSurvivalGames.ArenaContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import NMSurvivalGames.NMSurvivalGames;
import NMSurvivalGames.Arena.Arena;
import NMSurvivalGames.ArenaContainer.Commands.ArenaCE;
import NMSurvivalGames.ArenaContainer.Commands.ArenaContainerCE;
import NMSurvivalGames.ArenaContainer.Commands.InternalCE;
import NMSurvivalGames.ArenaContainer.misc.Read;
import NMSurvivalGames.ArenaContainer.misc.Write;

public class ArenaContainer {

	private NMSurvivalGames main;	
	private Write w;
	private Read r;

	private ArenaContainerCE acce;
	private ArenaCE ace;
	private InternalCE ice;

	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	private HashMap<String, Boolean> enabled = new HashMap<String, Boolean>();

	public ArenaContainer(NMSurvivalGames main) {
		setMain(main);

		setAcce(new ArenaContainerCE(this));
		setAce(new ArenaCE(this));
		setIce(new InternalCE(this));

		setR(new Read(this));
		setW(new Write(this));
		getR().read();

		for(String str : enabled.keySet()) {
			if(enabled.get(str)) {
				boolean contains = false;
				for(World w : Bukkit.getWorlds()) {
					if(w.getName().equalsIgnoreCase(str)) {
						addArena(w);
						contains = true;
					}
				}
				if(!contains) {
					World w;
					if((w = addWorldFromFile(str)) != null) {
						addArena(w);
					} else {
						getMain().getLogger().warning("Arena not added for " + str + "; world file does not exist!");
						enabled.put(str, false);
					}
				}
			}
		}

		setMaxPlayers();
	}

	private void setMaxPlayers() {

	}

	public Arena addArena(World w) {
		Arena a = new Arena(w, this);
		enabled.put(w.getName(), true);
		arenas.add(a);
		return a;
	}

	public void disableArena(Arena a) {
		enabled.put(a.getWorld().getName(), false);
		arenas.remove(a);
	}

	public Arena getArenaOnWorld(World w) {
		for(Arena a : arenas) {
			if(a.getWorld() == w && enabled.get(a.getWorld().getName())) {
				return a;
			}
		}
		return null;
	}
	
	public World addWorldFromFile(String name) {
		File container = Bukkit.getWorldContainer();
		for(String file : container.list()) {
			if(name.equalsIgnoreCase(file)) {
				World w = Bukkit.createWorld(new WorldCreator(file));
				return w;
			}
		}
		return null;
	}

	public void shutdown() {
		for(Arena a : arenas) {
			a.shutdown();
			a = null;
		}

		getW().write();
	}

	public NMSurvivalGames getMain() {
		return main;
	}

	public void setMain(NMSurvivalGames main) {
		this.main = main;
	}

	public void setEnabled(HashMap<String, Boolean> enabled) {
		this.enabled = enabled;
	}

	public HashMap<String, Boolean> getEnabled() {
		return enabled;
	}

	public Write getW() {
		return w;
	}

	public void setW(Write w) {
		this.w = w;
	}

	public Read getR() {
		return r;
	}

	public void setR(Read r) {
		this.r = r;
	}

	public ArrayList<Arena> getArenas() {
		return arenas;
	}

	public ArenaContainerCE getAcce() {
		return acce;
	}

	public void setAcce(ArenaContainerCE acce) {
		this.acce = acce;
	}

	public ArenaCE getAce() {
		return ace;
	}

	public void setAce(ArenaCE ace) {
		this.ace = ace;
	}

	public InternalCE getIce() {
		return ice;
	}

	public void setIce(InternalCE ice) {
		this.ice = ice;
	}
}
