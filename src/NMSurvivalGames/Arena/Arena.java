package NMSurvivalGames.Arena;

import org.bukkit.World;

import NMSurvivalGames.Arena.ArenaListeners.ArenaListener;
import NMSurvivalGames.Arena.Chests.ChestHandler;
import NMSurvivalGames.Arena.Locations.Locations;
import NMSurvivalGames.Arena.Misc.Read;
import NMSurvivalGames.Arena.Misc.Write;
import NMSurvivalGames.Arena.StateMachine.ArenaState;
import NMSurvivalGames.Arena.Timer.Commands;
import NMSurvivalGames.Arena.Timer.RepeatCommands;
import NMSurvivalGames.Arena.World.PlayerHandler;
import NMSurvivalGames.Arena.World.WorldHandler;
import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class Arena {

	private World world;
	private ArenaContainer ac;

	private ArenaState as;
	private ArenaListener al;
	private Read r;
	private Write w;

	private Locations startmatchLocations;
	private Locations deathmatchLocations;

	private Commands prematchCommands;
	private Commands matchCommands;
	private Commands deathmatchCommands;
	private Commands predeathmatchCommands;
	private Commands endmatchCommands;
	private RepeatCommands repeatCommands;

	private ChestHandler ch;
	private WorldHandler wh;
	private PlayerHandler ph;

	private String name;
	private boolean lock = false;
	private boolean frozen = false;
	private boolean breakBlocks = false;
	private boolean placeBlocks = false;

	public Arena(World w, ArenaContainer ac) {

		/* World/arena/container attribs */
		setWorld(w);
		setName(world.getName());
		setAc(ac);

		/* File reader/writer */
		setW(new Write(this));
		setR(new Read(this));

		/* Arena state machine */
		setAs(new ArenaState());

		/* Commands & Listeners */
		setAl(new ArenaListener(this));

		/* Location/teleporting */
		setSML(new Locations(this));
		setDML(new Locations(this));

		/* Commands */
		setPC(new Commands(this));
		setMC(new Commands(this));
		setPDC(new Commands(this));
		setDC(new Commands(this));
		setEC(new Commands(this));
		setRC(new RepeatCommands(this));

		/* Chests */
		setCh(new ChestHandler(this));
		setWh(new WorldHandler(this));
		setPh(new PlayerHandler(this));

		/* Read settings from file*/
		getR().read();

		getWh().setWorldAttribs();
		getPh().getPlayers();
	}

	public void EnableArena() {
		getW().write();
	}

	public void DisableArena() {
		getW().write();
	}

	public void shutdown() {
		getW().write();
	}

	public boolean getLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Read getR() {
		return r;
	}

	public void setR(Read r) {
		this.r = r;
	}

	public Write getW() {
		return w;
	}

	public void setW(Write w) {
		this.w = w;
	}

	public ArenaListener getAl() {
		return al;
	}

	public void setAl(ArenaListener al) {
		this.al = al;
	}

	public ArenaState getAs() {
		return as;
	}

	public void setAs(ArenaState as) {
		this.as = as;
	}

	public ArenaContainer getAc() {
		return ac;
	}

	public void setAc(ArenaContainer ac) {
		this.ac = ac;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Locations getSML() {
		return startmatchLocations;
	}

	public void setSML(Locations locations) {
		this.startmatchLocations = locations;
	}

	public Locations getDML() {
		return deathmatchLocations;
	}

	public void setDML(Locations locations) {
		this.deathmatchLocations = locations;
	}

	public Commands getPC() {
		return prematchCommands;
	}

	public void setPC(Commands prematchCommands) {
		this.prematchCommands = prematchCommands;
	}

	public Commands getMC() {
		return matchCommands;
	}

	public void setMC(Commands commands) {
		this.matchCommands = commands;
	}
	
	public Commands getPDC() {
		return predeathmatchCommands;
	}

	public void setPDC(Commands commands) {
		this.predeathmatchCommands = commands;
	}

	public Commands getDC() {
		return deathmatchCommands;
	}

	public void setDC(Commands commands) {
		this.deathmatchCommands = commands;
	}
	
	public Commands getEC() {
		return endmatchCommands;
	}

	public void setEC(Commands commands) {
		this.endmatchCommands = commands;
	}

	public RepeatCommands getRC() {
		return repeatCommands;
	}

	public void setRC(RepeatCommands repeatCommands) {
		this.repeatCommands = repeatCommands;
	}

	public ChestHandler getCh() {
		return ch;
	}

	public void setCh(ChestHandler ch) {
		this.ch = ch;
	}

	public WorldHandler getWh() {
		return wh;
	}

	public void setWh(WorldHandler wh) {
		this.wh = wh;
	}

	public PlayerHandler getPh() {
		return ph;
	}

	public void setPh(PlayerHandler ph) {
		this.ph = ph;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isBreakBlocks() {
		return breakBlocks;
	}

	public void setBreakBlocks(boolean breakBlocks) {
		this.breakBlocks = breakBlocks;
	}

	public boolean isPlaceBlocks() {
		return placeBlocks;
	}

	public void setPlaceBlocks(boolean placeBlocks) {
		this.placeBlocks = placeBlocks;
	}
}