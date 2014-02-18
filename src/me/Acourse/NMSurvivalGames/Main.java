package me.Acourse.NMSurvivalGames;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

	/* Variables */

	// Constructs
	NMSGTimer timer;
	StateMachine sm;

	// ADMIN TP
	int adminX, adminY, adminZ = 0;

	// DEAD TP
	int deadX, deadY, deadZ = 0;

	// SPAWN LOCATION
	Vector<Vector<Integer>> spawnLocs = new Vector<Vector<Integer>>();

	// DEATHMATCH LOCATION
	Vector<Vector<Integer>> deathLocs = new Vector<Vector<Integer>>();

	// CHESTS
	int numOfChests = 40;

	Vector<Integer> Ids = new Vector<Integer>();
	Vector<Integer> numOf = new Vector<Integer>();
	Vector<Integer> totalVec = new Vector<Integer>();

	// PLAYERS
	Vector<Player> onlineplayers = new Vector<Player>();
	Vector<Player> aliveplayers = new Vector<Player>();
	Vector<Player> deadplayers = new Vector<Player>();

	/* End Variables */

	// Bukkit Commands

	public void onEnable() {

		getLogger().info("Survival Games Plugin running...");
		timer = new NMSGTimer(this);
		sm = new StateMachine(this);
		loadConfig();
		loadChestVec();
		getServer().getPluginManager().registerEvents(this, this);
		loadplayers();
		timer.runRepeatCommands();
		Bukkit.dispatchCommand(getServer().getConsoleSender(), "getchests");
		Bukkit.dispatchCommand(getServer().getConsoleSender(), "fillchests");
	}

	public void onDisable() {

		getLogger().info("Survival Games Plugin shutting down...");
		this.saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String cmdname = cmd.getName().toLowerCase();
		if (sender instanceof Player) {
			Player player = (Player) sender;

			switch (cmdname) {

				case "admin":
					return playeradmin(player, cmd, label, args);
				case "setadmin":
					return playersetadmin(player, cmd, label, args);
				case "dead":
					return playerdead(player, cmd, label, args);
				case "setdead":
					return playersetdead(player, cmd, label, args);
				case "setspawnloc":
					return playersetspawnloc(player, cmd, label, args);
				case "listspawnloc":
					return playerlistspawnloc(player, cmd, label, args);
				case "removespawnloc":
					return playerremovespawnloc(player, cmd, label, args);
				case "setdeathloc":
					return playersetdeathloc(player, cmd, label, args);
				case "listdeathloc":
					return playerlistdeathloc(player, cmd, label, args);
				case "removedeathloc":
					return playerremovedeathloc(player, cmd, label, args);
				default:
					player.sendMessage("[NMSG]  You cannot run that command in game, use the command console");

			}
		} else if (sender instanceof ConsoleCommandSender) {

			switch (cmdname) {

				case "startmatch":
					return commandstartmatch(sender, cmd, label, args);
				case "startdeathmatch":
					return commandstartdeathmatch(sender, cmd, label, args);
				case "addonecommand":
					return commandaddonecommand(sender, cmd, label, args);
				case "listonecommands":
					return commandlistonecommands(sender, cmd, label, args);
				case "removeonecommand":
					return commandremoveonecommand(sender, cmd, label, args);
				case "addrepeatcommand":
					return commandaddrepeatcommand(sender, cmd, label, args);
				case "listrepeatcommands":
					return commandlistrepeatcommands(sender, cmd, label, args);
				case "removerepeatcommand":
					return commandremoverepeatcommand(sender, cmd, label, args);
				case "adddeathmatchcommand":
					return commandadddeathmatchcommand(sender, cmd, label, args);
				case "listdeathmatchcommands":
					return commandlistdeathmatchcommands(sender, cmd, label, args);
				case "removedeathmatchcommand":
					return commandremovedeathmatchcommand(sender, cmd, label, args);
				case "getchests":
					return commandgetchests(sender, cmd, label, args);
				case "fillchests":
					return commandfillchests(sender, cmd, label, args);
				case "runrepeatcommands":
					return commandrunrepeatcommands(sender, cmd, label, args);
				case "start":
				case "runonecommands":
					return commandrunonecommands(sender, cmd, label, args);
				case "rundeathmatchcommands":
					return commandrundeathmatchcommands(sender, cmd, label, args);
				case "stoprepeatcommands":
					return commandstoprepeatcommands(sender, cmd, label, args);
				case "stoponecommands":
					return commandstoponecommands(sender, cmd, label, args);
				case "stopdeathmatchcommands":
					return commandstopdeathmatchcommands(sender, cmd, label, args);
				default:
					getLogger().info("[NMSG] You must use that command in game");
			}
		}
		return false;
	}

	// Events

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent event) {

		if (onlineplayers.size() >= 24 && !event.getPlayer().getName().equals("acourse") && !event.getPlayer().getName().equals("Nevrad1")) {
			event.disallow(Result.KICK_FULL, "There are already 24 players online, please wait until a spot opens");
		} else {
			onlineplayers.add(event.getPlayer());
			deadplayers.add(event.getPlayer());
			Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join dead " + event.getPlayer().getName());
		}
	}

	public void onLogin(PlayerJoinEvent event) {

		event.setJoinMessage("There are " + onlineplayers.size());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogoff(PlayerQuitEvent event) {

		onlineplayers.remove(event.getPlayer());
		if (aliveplayers.contains(event.getPlayer())) {
			aliveplayers.remove(event.getPlayer());
			Bukkit.dispatchCommand(event.getPlayer(), "say Hey guys, im a loser and I QUIT!");
			Bukkit.dispatchCommand(event.getPlayer(), "kill");
		} else if (deadplayers.contains(event.getPlayer())) {
			deadplayers.remove(event.getPlayer());
		}

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {

		if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
			Player killedP = (Player) event.getEntity();
			Player killerP = (Player) event.getEntity().getKiller();

			if (killerP.getGameMode() == GameMode.CREATIVE) {

				killedP.sendMessage(ChatColor.RED + "You have unfortuantly been killed by admin. Play nice next time!");

				if (aliveplayers.contains(killedP)) {
					aliveplayers.remove(killedP);
				}
				deadplayers.add(killedP);
				Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join dead " + killedP.getName());

			} else if (aliveplayers.contains(killedP) && aliveplayers.contains(killerP)) {

				killedP.getWorld().playEffect(killedP.getLocation(), Effect.SMOKE, 1);
				killedP.getWorld().strikeLightningEffect(killedP.getLocation());

				Bukkit.dispatchCommand(getServer().getConsoleSender(), "say " + ChatColor.GOLD + killerP.getName() + " has ended " + killedP.getName() + " Hunger Games!");
				killedP.sendMessage(ChatColor.RED + "You have been killed! Your hunger games has ended!");

				if (aliveplayers.contains(killedP)) {
					aliveplayers.remove(killedP);
				}
				deadplayers.add(killedP);
				Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join dead " + killedP.getName());

			}

			if (aliveplayers.size() == 1) {
				if (sm.getState() == StateMachine.DeathMatch) {
					sm.nextState();
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join dead " + killerP.getName());
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "say " + killerP.getName() + " has won THE HUNGER GAMES!");
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "say Server will reset shortly!");
				}
			} else if (aliveplayers.size() <= 3) {
				if (sm.getState() == StateMachine.Match) {
					Bukkit.dispatchCommand(getServer().getConsoleSender(), "rundeathmatchcommands");
				}
			}
		}
		event.setDeathMessage("");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

		Player player = (Player) event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE) {
			player.setDisplayName(ChatColor.RED + "[ADMIN]" + ChatColor.RESET + player.getName());
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BlockBreakEvent(BlockBreakEvent event) {

		if (event.getPlayer() instanceof Player) {
			if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BlockPlaceEvent(BlockPlaceEvent event) {
		if (event.getPlayer() instanceof Player) {
			if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
				event.setCancelled(true);
			}
		}
	}

	// Misc Methods

	public Material getById(int ID) {

		switch (ID) {
			case 264:
				return Material.DIAMOND;
			case 280:
				return Material.STICK;
			case 287:
				return Material.STRING;
			case 265:
				return Material.IRON_INGOT;
			case 267:
				return Material.IRON_SWORD;
			case 272:
				return Material.STONE_SWORD;
			case 268:
				return Material.WOOD_SWORD;
			case 261:
				return Material.BOW;
			case 262:
				return Material.ARROW;
			case 318:
				return Material.FLINT;
			case 334:
				return Material.LEATHER;
			case 298:
				return Material.LEATHER_HELMET;
			case 299:
				return Material.LEATHER_CHESTPLATE;
			case 300:
				return Material.LEATHER_LEGGINGS;
			case 301:
				return Material.LEATHER_BOOTS;
			case 302:
				return Material.CHAINMAIL_HELMET;
			case 303:
				return Material.CHAINMAIL_CHESTPLATE;
			case 304:
				return Material.CHAINMAIL_LEGGINGS;
			case 305:
				return Material.CHAINMAIL_BOOTS;
			case 306:
				return Material.IRON_HELMET;
			case 307:
				return Material.IRON_CHESTPLATE;
			case 308:
				return Material.IRON_LEGGINGS;
			case 309:
				return Material.IRON_BOOTS;
			case 349:
				return Material.RAW_FISH;
			case 346:
				return Material.COOKED_FISH;
			case 319:
				return Material.PORK;
			case 320:
				return Material.GRILLED_PORK;
			case 363:
				return Material.RAW_BEEF;
			case 364:
				return Material.COOKED_BEEF;
			case 59:
				return Material.WHEAT;
			case 392:
				return Material.POTATO_ITEM;
			case 391:
				return Material.CARROT_ITEM;
			case 263:
				return Material.COAL;
			case -1:
				return Material.AIR;
		}
		return Material.AIR;
	}

	// Configuration Manipulation

	public void loadConfig() {

		// ADMIN ROOM
		adminX = this.getConfig().getInt("admin.x");
		adminY = this.getConfig().getInt("admin.y");
		adminZ = this.getConfig().getInt("admin.z");

		// DEAD ROOM
		deadX = this.getConfig().getInt("dead.x");
		deadY = this.getConfig().getInt("dead.y");
		deadZ = this.getConfig().getInt("dead.z");

		// SPAWN LOCATIONS
		spawnLocs.clear();
		for (int i = 0; i < 200; i++) {
			Vector<Integer> temp = new Vector<Integer>();
			temp.clear();
			temp.add(0, this.getConfig().getInt("spawnLocs." + i + ".x"));
			temp.add(1, this.getConfig().getInt("spawnLocs." + i + ".y"));
			temp.add(2, this.getConfig().getInt("spawnLocs." + i + ".z"));
			if (!(temp.get(0).equals(0) && temp.get(1).equals(0) && temp.get(2).equals(0))) {
				if (!spawnLocs.contains(temp)) {
					spawnLocs.add(i, temp);
				}
			}
		}

		// DEATHMATCH LOCATIONS
		deathLocs.clear();
		for (int i = 0; i < 200; i++) {
			Vector<Integer> temp = new Vector<Integer>();
			temp.clear();
			temp.add(0, this.getConfig().getInt("deathLocs." + i + ".x"));
			temp.add(1, this.getConfig().getInt("deathLocs." + i + ".y"));
			temp.add(2, this.getConfig().getInt("deathLocs." + i + ".z"));
			if (!(temp.get(0).equals(0) && temp.get(1).equals(0) && temp.get(2).equals(0))) {
				if (!deathLocs.contains(temp)) {
					deathLocs.add(i, temp);
				}
			}
		}

		// CHESTS
		numOf.clear();
		for (int i = 0; i <= 422; i++) { // 422 is the number of items currently
			// in Minecraft (without data
			// values)
			numOf.add(this.getConfig().getInt("chests.numOf." + i));
		}

		// COMMANDS THAT RUN ONCE
		int size = this.getConfig().getInt("onecommands.size");
		timer.getOneCommand().clear();
		for (int i = 0; i < size; i++) {
			timer.getOneCommand().add(new me.Acourse.NMSurvivalGames.Command(this.getConfig().getInt("onecommands." + i + ".time"), this.getConfig().getString("onecommands." + i + ".command")));
		}

		// COMMANDS THAT RUN REPEATED
		size = this.getConfig().getInt("repeatcommands.size");
		timer.getRepeatCommand().clear();
		for (int i = 0; i < size; i++) {
			timer.getRepeatCommand().add(new me.Acourse.NMSurvivalGames.Command(this.getConfig().getInt("repeatcommands." + i + ".startdelay"), this.getConfig().getInt("repeatcommands." + i + ".repeatdelay"), this.getConfig().getString("repeatcommands." + i + ".command")));
		}

		// COMMANDS THAT RUN AT DEATHMATCH
		size = this.getConfig().getInt("deathmatchcommands.size");
		timer.getDeathmatchCommand().clear();
		for (int i = 0; i < size; i++) {
			timer.getDeathmatchCommand().add(new me.Acourse.NMSurvivalGames.Command(this.getConfig().getInt("deathmatchcommands." + i + ".time"), this.getConfig().getString("deathmatchcommands." + i + ".command")));
		}
	}

	public void loadChestVec() {

		for (int i = 0; i < numOf.size(); i++) {
			for (int j = 0; j < numOf.get(i); j++) {
				totalVec.add(i);
			}
		}
	}

	public void loadplayers() {

		Player[] p = getServer().getOnlinePlayers();
		for (int i = 0; i < p.length; i++) {
			onlineplayers.add(p[i]);
		}
	}

	// User Commands

	private boolean playeradmin(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (adminX != 0 && adminY != 0 && adminZ != 0) {
				player.teleport(new Location(Bukkit.getWorld("world"), (double) adminX + .5, (double) adminY, (double) adminZ + .5));
				player.sendMessage("[NMSG]  Teleported to admin");
				return true;
			} else {
				player.sendMessage("[NMSG] Please add a position using /setadmin");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playersetadmin(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (args.length == 0) {

				adminX = player.getLocation().getBlockX();
				adminY = player.getLocation().getBlockY();
				adminZ = player.getLocation().getBlockZ();

				this.getConfig().set("admin.x", adminX);
				this.getConfig().set("admin.y", adminY);
				this.getConfig().set("admin.z", adminZ);
				this.saveConfig();

				player.sendMessage("[NMSG] Admin teleport has been set to this location");
				return true;
			} else {
				player.sendMessage("[NMSG] Usage is /setadmin");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playerdead(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (deadX != 0 && deadY != 0 && deadZ != 0) {
				player.teleport(new Location(Bukkit.getWorld("world"), (double) deadX - .5, (double) deadY, (double) deadZ - .5));
				player.sendMessage("[NMSG] Teleported to dead room");
				return true;
			} else {
				player.sendMessage("[NMSG] Please set a position using /setdead");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playersetdead(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (args.length == 0) {

				deadX = player.getLocation().getBlockX();
				deadY = player.getLocation().getBlockY();
				deadZ = player.getLocation().getBlockZ();

				player.getWorld().setSpawnLocation((int) player.getLocation().getX(), (int) player.getLocation().getY(), (int) player.getLocation().getZ());

				this.getConfig().set("dead.x", deadX);
				this.getConfig().set("dead.y", deadY);
				this.getConfig().set("dead.z", deadZ);
				this.saveConfig();

				player.sendMessage("[NMSG] Spawn has been set to this location");
				return true;
			} else {
				player.sendMessage("[NMSG] Usage is /setdead");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playersetspawnloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("SurivialGamesPlugin.admincommands")) {
			int placeholder;
			if (args.length == 0) {
				player.sendMessage("[NMSG] Using the next available location for this point: " + spawnLocs.size());
				placeholder = spawnLocs.size();
			} else if (args.length == 1) {
				placeholder = Integer.parseInt(args[0]);
			} else {
				player.sendMessage("[NMSG] Invalid number of args, use either /setspawnloc or /setspawnloc <index>");
				return false;
			}

			Vector<Integer> loc = new Vector<Integer>();

			loc.add((int) player.getLocation().getX());
			loc.add((int) player.getLocation().getY());
			loc.add((int) player.getLocation().getZ());

			this.getConfig().set("spawnLocs." + placeholder + ".x", (int) player.getLocation().getX());
			this.getConfig().set("spawnLocs." + placeholder + ".y", (int) player.getLocation().getY());
			this.getConfig().set("spawnLocs." + placeholder + ".z", (int) player.getLocation().getZ());
			this.saveConfig();

			player.sendMessage("[NMSG] Setting index " + placeholder + " to " + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ());

			if (!spawnLocs.contains(loc)) {
				spawnLocs.add(placeholder, loc);
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
		return true;
	}

	private boolean playerlistspawnloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (spawnLocs.size() == 0) {
				player.sendMessage("[NMSG]  The list is empty");
				return false;
			} else {
				for (int i = 0; i < spawnLocs.size(); i++) {
					player.sendMessage("[NMSG]  Index: " + i + " and points " + spawnLocs.get(i).get(0) + " " + spawnLocs.get(i).get(1) + " " + spawnLocs.get(i).get(2));
				}
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
		return true;
	}

	private boolean playerremovespawnloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("all")) {
					this.getConfig().set("spawnLocs", null);
					player.sendMessage("[NMSG] There were " + spawnLocs.size() + " entrys removed");
					spawnLocs.clear();
					return true;
				} else {
					spawnLocs.remove(Integer.parseInt(args[0]));
					this.getConfig().set("spawnLocs" + Integer.parseInt(args[0]), null);
					player.sendMessage("[NMSG] Removed one entry at " + args[0]);
					return true;
				}
			} else {
				player.sendMessage("[NMSG] Usage is /removespawnloc <all;#> where # is the index you wish to remove");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playersetdeathloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			int placeholder;
			if (args.length == 0) {
				player.sendMessage("[NMSG] Using the next available location for this point: " + deathLocs.size());
				placeholder = deathLocs.size();
			} else if (args.length == 1) {
				placeholder = Integer.parseInt(args[0]);
			} else {
				player.sendMessage("[NMSG] Invalid number of args, use either /setdeathloc or /setdeathloc <TP #>");
				return false;
			}

			Vector<Integer> loc = new Vector<Integer>();

			loc.add((int) player.getLocation().getX());
			loc.add((int) player.getLocation().getY());
			loc.add((int) player.getLocation().getZ());

			this.getConfig().set("deathLocs." + placeholder + ".x", (int) player.getLocation().getX());
			this.getConfig().set("deathLocs." + placeholder + ".y", (int) player.getLocation().getY());
			this.getConfig().set("deathLocs." + placeholder + ".z", (int) player.getLocation().getZ());
			this.saveConfig();

			if (!deathLocs.contains(loc)) {
				deathLocs.add(placeholder, loc);
			}

		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
		return true;
	}

	private boolean playerlistdeathloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (deathLocs.size() == 0) {
				player.sendMessage("[NMSG] The list is empty");
				return false;
			} else {
				for (int i = 0; i < deathLocs.size(); i++) {
					player.sendMessage("[NMSG]  Index: " + i + " and points " + deathLocs.get(i).get(0) + " " + deathLocs.get(i).get(1) + " " + deathLocs.get(i).get(2));
				}
				return true;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	private boolean playerremovedeathloc(Player player, Command cmd, String label, String[] args) {

		if (player.hasPermission("NMSurvivalGames.admincommands")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("all")) {
					player.sendMessage("[NMSG] There were " + deathLocs.size() + " entrys removed");
					this.getConfig().set("deathLocs", null);
					deathLocs.clear();
					return true;
				} else {
					deathLocs.remove(Integer.parseInt(args[0]));
					this.getConfig().set("deathLocs" + Integer.parseInt(args[0]), null);
					player.sendMessage("[NMSG] Removed one entry at " + args[0]);
					return true;
				}
			} else {
				player.sendMessage("[NMSG] Usage is /removesdeathloc <all;#> where # is the index you wish to remove");
				return false;
			}
		} else {
			player.sendMessage("[NMSG] You do not have permission to do that.");
			return false;
		}
	}

	// Console Commands

	@SuppressWarnings("unchecked")
	private boolean commandstartmatch(CommandSender sender, Command cmd, String label, String[] args) {

		if (sm.getState() == StateMachine.PreMatch) {
			Player p[] = getServer().getOnlinePlayers();
			if (p.length > 0) {
				Vector<Vector<Integer>> tempvec = (Vector<Vector<Integer>>) spawnLocs.clone();
				for (int i = 0; i < p.length; i++) {
					int numOfSpawnLocs = tempvec.size();
					if (p[i].getGameMode() == GameMode.ADVENTURE) {
						if (numOfSpawnLocs > 0) {
							aliveplayers.add(p[i]);
							Bukkit.dispatchCommand(getServer().getConsoleSender(), "scoreboard teams join alive " + p[i].getName());
							Bukkit.dispatchCommand(getServer().getConsoleSender(), "clear " + p[i].getName());
							int playerSpawnLoc = (int) (Math.floor(Math.random() * numOfSpawnLocs));
							p[i].teleport(new Location(Bukkit.getWorld("world"), (double) tempvec.get(playerSpawnLoc).get(0) - .5, (double) tempvec.get(playerSpawnLoc).get(1), (double) tempvec.get(playerSpawnLoc).get(2) - .5));
							p[i].addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 30, 100));
							p[i].addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 254));
							p[i].sendMessage(ChatColor.GOLD + "[NMSG] Thank you for playing an NMMaps Survival Game, have fun!");
							tempvec.remove(playerSpawnLoc);
						} else {
							p[i].sendMessage("[NMSG] Hey! Sorry, you got missed... there are more than 24 players in the server right now");
						}
					} else {
						p[i].sendMessage(ChatColor.GOLD + "[NMSG] Welcome admin, the game is starting in 30 seconds");
					}
				}
			} else {
				getLogger().info("[NMSG] There is nobody online, why would you start a match?");
			}
			sm.nextState();
			return true;
		} else {
			getLogger().info("[NMSG] You are not able to start the match in this state");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean commandstartdeathmatch(CommandSender sender, Command cmd, String label, String[] args) {

		if (sm.getState() == StateMachine.Match) {
			Player p[] = new Player[aliveplayers.size()];
			for (int i = 0; i < aliveplayers.size(); i++) {
				p[i] = aliveplayers.get(i);
			}
			if (p.length > 0) {
				Vector<Vector<Integer>> tempvec = (Vector<Vector<Integer>>) deathLocs.clone();
				for (int i = 0; i < p.length; i++) {
					int numOfDeathLocs = tempvec.size();
					if (p[i].getGameMode() == GameMode.ADVENTURE) {
						int playerSpawnLoc = (int) (Math.floor(Math.random() * numOfDeathLocs));
						p[i].teleport(new Location(Bukkit.getWorld("world"), (double) tempvec.get(playerSpawnLoc).get(0) - .5, (double) tempvec.get(playerSpawnLoc).get(1), (double) tempvec.get(playerSpawnLoc).get(2) - .5));
					}
				}
			}
			sm.nextState();
			return true;
		} else {
			getLogger().info("[NMSG] You are not able to start deathmatch in this state");
			return false;
		}
	}

	private boolean commandaddonecommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length >= 2) {
			String command = "";
			for (int i = 1; i < args.length; i++) {
				command += args[i] + " ";
			}
			timer.addOneCommand(Integer.parseInt(args[0]), command);
		} else {
			getLogger().info("[NMSG] incorrect usage: use as addonecommand <timeinseconds> <command>");
			return false;
		}
		return true;
	}

	private boolean commandlistonecommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.listOneCommands();

		return true;
	}

	private boolean commandremoveonecommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("all")) {
				timer.removeOneCommand("all");
			} else {
				timer.removeOneCommand(Integer.parseInt(args[0]));
			}
		} else {
			getLogger().info("[NMSG] incorrect usage: use as removeonecommand <index:all>");
			return false;
		}

		return true;
	}

	private boolean commandaddrepeatcommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length >= 3) {
			String command = "";
			for (int i = 2; i < args.length; i++) {
				command += args[i] + " ";
			}
			timer.addRepeatCommand(Integer.parseInt(args[0]), Integer.parseInt(args[1]), command);
		} else {
			getLogger().info("[NMSG] incorrect usage: use as addrepeatcommand <startdelay> <repeatdelay> <command>");
			return false;
		}
		return true;
	}

	private boolean commandlistrepeatcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.listRepeatCommands();

		return true;
	}

	private boolean commandremoverepeatcommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("all")) {
				timer.removeOneCommand("all");
			} else {
				timer.removeRepeatCommand(Integer.parseInt(args[0]));
			}
		} else {
			getLogger().info("[NMSG] incorrect usage: use as removerepeatcommand <index:all>");
			return false;
		}

		return true;
	}

	private boolean commandadddeathmatchcommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length >= 2) {
			String command = "";
			for (int i = 1; i < args.length; i++) {
				command += args[i] + " ";
			}
			timer.addDeathmatchCommand(Integer.parseInt(args[0]), command);
		} else {
			getLogger().info("[NMSG] incorrect usage: use as adddeathmatchcommand <timeinseconds> <command>");
			return false;
		}
		return true;
	}

	private boolean commandlistdeathmatchcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.listDeathmatchCommands();

		return true;
	}

	private boolean commandremovedeathmatchcommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("all")) {
				timer.removeDeathmatchCommand("all");
			} else {
				timer.removeDeathmatchCommand(Integer.parseInt(args[0]));
			}
		} else {
			getLogger().info("[NMSG] incorrect usage: use as removedeathmatchcommand <index:all>");
			return false;
		}

		return true;
	}

	private boolean commandgetchests(CommandSender sender, Command cmd, String label, String[] args) {

		Chunk[] loadedWorld = getServer().getWorld("world").getLoadedChunks();
		int counter = 0;
		for (Chunk lw : loadedWorld) {
			BlockState[] te = lw.getTileEntities();
			for (BlockState t : te) {
				if (t instanceof Chest) {
					Chest chest = (Chest) t;
					if (chest.getInventory().getSize() == 27) {
						counter++;
					}
				}
			}
		}
		this.numOfChests = counter;
		getLogger().info("[NMSG] " + " There are " + numOfChests + " chests");
		int numOfSpaces = numOfChests * 27 - totalVec.size();
		for (int i = 0; i < numOfSpaces; i++) {
			totalVec.add(-1);
		}
		return true;
	}

	private boolean commandfillchests(CommandSender sender, Command cmd, String label, String[] args) {

		Chunk[] loadedWorld = getServer().getWorld("world").getLoadedChunks();
		getLogger().info("[NMSG] Filling Chests");
		for (Chunk lw : loadedWorld) {
			BlockState[] te = lw.getTileEntities();
			for (BlockState t : te) {
				if (t instanceof Chest && !(t instanceof DoubleChest)) {

					Chest chest = (Chest) t;

					chest.getBlockInventory().clear();
					Inventory iv = chest.getInventory();
					for (int i = 0; i < iv.getSize(); i++) {
						int randInt = (int) Math.floor(Math.random() * totalVec.size());
						iv.setItem(i, new ItemStack(getById(totalVec.get(randInt))));
					}
				}
			}
		}
		return true;
	}

	private boolean commandrunrepeatcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.runRepeatCommands();
		return true;
	}

	private boolean commandrunonecommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.runOneCommands();
		return false;
	}

	private boolean commandrundeathmatchcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.runDeathmatchCommands();
		return false;
	}

	private boolean commandstopdeathmatchcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.stopDeathmatchCommands();
		return true;
	}

	private boolean commandstoprepeatcommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.stopRepeatCommands();
		return true;
	}

	private boolean commandstoponecommands(CommandSender sender, Command cmd, String label, String[] args) {

		timer.stopOneCommands();
		return true;
	}
}