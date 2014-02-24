package NMSurvivalGames.ArenaContainer.Commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import NMSurvivalGames.Arena.Arena;
import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class ArenaContainerCE implements CommandExecutor {

	private ArenaContainer ac;
	
	public static final String PERMISSION_STRING = "You do not have the permission to use that command.";

	public ArenaContainerCE(ArenaContainer ac) {
		setAc(ac);
		getAc().getMain().getCommand("arenacontainer").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("enable")) {
				if(sender.hasPermission("NMSurvivalGames.ArenaContainer.enable")) {
					if(args.length == 2) {
						String world = args[1];
						for(World w : getAc().getMain().getServer().getWorlds()) {
							if(w.getName().equalsIgnoreCase(world)) {
								Arena a;
								if((a = getAc().getArenaOnWorld(w)) != null) {
									a.EnableArena();
									sender.sendMessage((sender instanceof Player) ? ChatColor.GOLD + "Arena enabled. There is already an arena here, so chances are that the arena was already enabled." : "Arena Enabled. There is already an arena here, so chances are that the arena was already enabled.");
									return true;
								} else {
									a = getAc().addArena(w);
									a.EnableArena();
									sender.sendMessage((sender instanceof Player) ? ChatColor.GOLD + "Arena created and enabled." : "Arena created and enabled. ");
									return true;
								}
							}
						}
						World w;
						if((w = getAc().addWorldFromFile(world)) != null) {
							Arena a = getAc().addArena(w);
							a.EnableArena();
							sender.sendMessage(ChatColor.GOLD + "Arena created and enabled on world " + w.getName());
							return true;
						} else {
							sender.sendMessage(ChatColor.RED + "This world could not be added, we cannot find it in the world registry. Is it in the correct file folder?");
							return false;
						}
					} else {
						sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "Invalid Syntax, use /arenacontainer enable <world>." : "Invalid Syntax, use /arenacontainer enable <world>.");
						return false;
					}
				} else {
					sender.sendMessage((sender instanceof Player) ? ChatColor.RED + PERMISSION_STRING : PERMISSION_STRING);
					return false;
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(getAc().getArenas().size() != 0) {
					sender.sendMessage((sender instanceof Player) ? ChatColor.DARK_PURPLE + "Arenas:" : "Arenas:");
					for(Arena arena : getAc().getArenas()) {
						sender.sendMessage(arena.getWorld().getName());
					}
					return true;
				} else {
					sender.sendMessage("There are no arenas");
					return false;
				}
			} else if(args[0].equalsIgnoreCase("disable")) {
				if(sender.hasPermission("NMSurvivalGames.ArenaContainer.disable")) {
					if(args.length == 2) {
						String world = args[1];
						for(World w : getAc().getMain().getServer().getWorlds()) {
							if(w.getName().equalsIgnoreCase(world)) {
								Arena a;
								if((a = getAc().getArenaOnWorld(w)) != null) {
									a.DisableArena();
									sender.sendMessage((sender instanceof Player) ? ChatColor.GOLD + "Arena disabled and unloaded." : "Arena disabled and unloaded.");
									return true;
								} else {
									sender.sendMessage((sender instanceof Player) ? ChatColor.LIGHT_PURPLE + "Arena already seems disabled." : "Arena already seems disabled");
									return false;
								}
							}
						}
						sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "That world does not exist." : "That world does not exist.");
						return false;
					} else {
						sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "Invalid Syntax, use /arenacontainer disable <world>" : "Invalid Syntax, use /arenacontainer disable <world>");
						return false;
					}
				} else {
					sender.sendMessage((sender instanceof Player) ? ChatColor.RED + PERMISSION_STRING : PERMISSION_STRING);
					return false;
				}
			} else if(args[0].equalsIgnoreCase("lock")) {
				if(sender.hasPermission("NMSurvivalGames.ArenaContainer.lock")) {
					if(args.length == 3) {
						String world = args[1];
						String lock = args[2];
						for(World w : getAc().getMain().getServer().getWorlds()) {
							if(w.getName().equalsIgnoreCase(world)) {
								Arena a = getAc().getArenaOnWorld(w);
								if(a != null) {
									a.setLock(Boolean.parseBoolean(lock));
									sender.sendMessage((sender instanceof Player) ? ChatColor.GOLD + "World lock for " + world + " changed to " + Boolean.parseBoolean(lock) : "World lock for " + world + " changed to " + Boolean.parseBoolean(lock));
									return true;
								}
							}
						}
						sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "That world does not exist" : "That world does not exist");
						return false;
					} else {
						sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "Invalid Syntax, use /arenacontainer lock <world> <true:false>" : "Invalid Syntax, use /arenacontainer <enable:disable>");
						return false;
					}
				} else {
					sender.sendMessage((sender instanceof Player) ? ChatColor.RED + PERMISSION_STRING : PERMISSION_STRING);
					return false;
				}
			} else {
				sender.sendMessage((sender instanceof Player) ? ChatColor.RED + "Invalid Syntax, use /arenacontainer <enable:list:disable:lock>" : "Invalid Syntax, use /arenacontainer <enable:disable>");
				return false;
			}
		} else {
			sender.sendMessage((sender instanceof Player) ? ChatColor.GREEN+ "#--- " + ChatColor.DARK_PURPLE + "NMSurvivalGames - /ac && /arenacommands" + ChatColor.GREEN + " ---#" : "#--- Arena Container Commands - NMSurvivalGames ---#");
			sender.sendMessage((sender instanceof Player) ? ChatColor.DARK_PURPLE + "/ac" + ChatColor.GREEN + " enable [world]" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + " Adds an arena to this server." : "/arenacontainer enable [world] - Adds an arena to this server.");
			sender.sendMessage((sender instanceof Player) ? ChatColor.DARK_PURPLE + "/ac" + ChatColor.GREEN + " list" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + " Lists all active arenas on the server" : "/arenacontainer list - Lists all active arenas on the server");
			sender.sendMessage((sender instanceof Player) ? ChatColor.DARK_PURPLE + "/ac" + ChatColor.GREEN + " disable [world]" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + " Removes an arena from this server." : "/arenacontainer disable [world] - Removes an arena from this server.");
			sender.sendMessage((sender instanceof Player) ? ChatColor.DARK_PURPLE + "/ac" + ChatColor.GREEN + " lock [world] <lock>" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + " Locks or unlocks an arena." : "/arenacontainer disable [world] - Removes an arena from this server.");
			return false;
		}
	}

	public ArenaContainer getAc() {
		return ac;
	}

	public void setAc(ArenaContainer ac) {
		this.ac = ac;
	}
}