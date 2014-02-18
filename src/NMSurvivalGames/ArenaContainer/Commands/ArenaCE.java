package NMSurvivalGames.ArenaContainer.Commands;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import NMSurvivalGames.Arena.Arena;
import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class ArenaCE implements CommandExecutor {

	public static final String PERMISSION_STRING = "You do not have the permission to use this command";

	private ArenaContainer ac;

	public ArenaCE(ArenaContainer ac) {
		setAc(ac);
		getAc().getMain().getCommand("arena").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(getAc().getArenaOnWorld(player.getWorld()) != null) {
				World w = player.getWorld();
				Arena a = getAc().getArenaOnWorld(w);
				if(args.length > 0) {
					if(args[0].equalsIgnoreCase("locations")) {
						if(args.length > 1) {
							if(args[1].equalsIgnoreCase("match")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(a.getSML().addLocation(player.getLocation())) {
											player.sendMessage(ChatColor.GOLD + "Location added.");
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "Location already exists here");
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										HashSet<Location> locations = a.getSML().getLocations();
										if(locations.size() != 0) {
											player.sendMessage(ChatColor.GOLD + "Locations for " + w.getName());
											for(Location loc : a.getSML().getLocations()) {
												player.sendMessage(loc.toString());
											}
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "There are no locations");
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(a.getSML().removeLocation(player.getLocation())) {
											player.sendMessage(ChatColor.GOLD + "Location removed");
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "Location does not exist, can not remove");
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations match <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations match <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("deathmatch")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(a.getDML().addLocation(player.getLocation())) {
											player.sendMessage(ChatColor.GOLD + "Location added.");
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "Location already exists here");
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										HashSet<Location> locations = a.getDML().getLocations();
										if(locations.size() != 0) {
											player.sendMessage(ChatColor.GOLD + "Locations for " + w.getName());
											for(Location loc : a.getSML().getLocations()) {
												player.sendMessage(loc.toString());
											}
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "There are no locations");
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(a.getDML().removeLocation(player.getLocation())) {
											player.sendMessage(ChatColor.GOLD + "Location removed");
											return true;
										} else {
											player.sendMessage(ChatColor.RED + "Location does not exist, can not remove");
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations deathmatch <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations deathmatch <add:list:remove>");
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations <match:deathmatch>");								
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " locations <match:deathmatch>");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("commands")) {
						if(args.length > 1) {
							if(args[1].equalsIgnoreCase("prematch")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getPC().addCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands prematch add <time> <command...>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												HashSet<String> commands = a.getPC().listCommands();
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else if(args.length == 4) {
												String time = args[3];
												HashSet<String> commands = a.getPC().listCommandsAtTime(Integer.parseInt(time));
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands prematch list [time]");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length == 4) {
												String time = args[3];
												if(a.getPC().clearCommandTime(Integer.parseInt(time))) {
													player.sendMessage(ChatColor.GOLD + "All commands removed from time " + time);
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "There are no commands to remove");
													return false;
												}
											} else if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getPC().removeCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command Removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "Unable to remove command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands prematch remove <time> [command...]");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands prematch <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands prematch <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("match")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}												
												if(a.getMC().addCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands match add <command> <time>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												HashSet<String> commands = a.getMC().listCommands();
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else if(args.length == 4) {
												String time = args[3];
												HashSet<String> commands = a.getMC().listCommandsAtTime(Integer.parseInt(time));
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands match list [time]");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length == 4) {
												String time = args[3];
												if(a.getMC().clearCommandTime(Integer.parseInt(time))) {
													player.sendMessage(ChatColor.GOLD + "All commands removed from time " + time);
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "There are no commands to remove");
													return false;
												}
											} else if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getMC().removeCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command Removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "Unable to remove command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands match remove <time> [command]");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands match <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands match <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("predeathmatch")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getPDC().addCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands predeathmatch add <command> <time>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												HashSet<String> commands = a.getPDC().listCommands();
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else if(args.length == 4) {
												String time = args[3];
												HashSet<String> commands = a.getPDC().listCommandsAtTime(Integer.parseInt(time));
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands predeathmatch list [time]");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length == 4) {
												String time = args[3];
												if(a.getPDC().clearCommandTime(Integer.parseInt(time))) {
													player.sendMessage(ChatColor.GOLD + "All commands removed from time " + time);
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "There are no commands to remove");
													return false;
												}
											} else if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getPDC().removeCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command Removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "Unable to remove command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands predeathmatch remove <time> [command]");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands predeathmatch <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands predeathmatch <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("deathmatch")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getDC().addCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands deathmatch add <command> <time>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												HashSet<String> commands = a.getDC().listCommands();
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else if(args.length == 4) {
												String time = args[3];
												HashSet<String> commands = a.getDC().listCommandsAtTime(Integer.parseInt(time));
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands deathmatch list [time]");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length == 4) {
												String time = args[3];
												if(a.getDC().clearCommandTime(Integer.parseInt(time))) {
													player.sendMessage(ChatColor.GOLD + "All commands removed from time " + time);
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "There are no commands to remove");
													return false;
												}
											} else if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getDC().removeCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command Removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "Unable to remove command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands deathmatch remove <time> [command]");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands deathmatch <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands deathmatch <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("endmatch")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getEC().addCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands endmatch add <command> <time>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												HashSet<String> commands = a.getEC().listCommands();
												for(String comm : commands) {
													player.sendMessage(comm);
												}
												return true;
											} else if(args.length == 4) {
												String time = args[3];
												String[] commands = (String[]) a.getEC().listCommandsAtTime(Integer.parseInt(time)).toArray();
												player.sendMessage(commands);
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands endmatch list [time]");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length == 4) {
												String time = args[3];
												if(a.getEC().clearCommandTime(Integer.parseInt(time))) {
													player.sendMessage(ChatColor.GOLD + "All commands removed from time " + time);
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "There are no commands to remove");
													return false;
												}
											} else if(args.length >= 5) {
												String time = args[3];
												String command = "";
												for(int i = 4; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getEC().removeCommand(Integer.parseInt(time), command)) {
													player.sendMessage(ChatColor.GOLD + "Command Removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "Unable to remove command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands endmatch remove <time> [command]");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands endmatch <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands endmatch <add:list:remove>");
									return false;
								}
							} else if(args[1].equalsIgnoreCase("repeat")) {
								if(args.length > 2) {
									if(args[2].equalsIgnoreCase("add")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.add")) {
											if(args.length >= 6) {
												String startdelay = args[3];
												String repeatdelay = args[4];
												String command = "";
												for(int i = 5; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getRC().addCommand(Integer.parseInt(startdelay), Integer.parseInt(repeatdelay), command)) {
													player.sendMessage(ChatColor.GOLD + "Command added sucessfully");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "This command is already at this time");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands repeat add <command> <startdelay> <repeatdelay>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("list")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.list")) {
											if(args.length == 3) {
												String[] commands = (String[]) a.getEC().listCommands().toArray();
												player.sendMessage(commands);
												return true;
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands repeat list");	
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else if(args[2].equalsIgnoreCase("remove")) {
										if(player.hasPermission("NMSurvivalGames.Arena.Commands.remove")) {
											if(args.length >= 4) {
												String command = "";
												for(int i = 3; i < args.length; i++) {
													command += args[i] + " ";
												}
												if(a.getRC().removeCommand(command)) {
													player.sendMessage(ChatColor.GOLD + "Command removed");
													return true;
												} else {
													player.sendMessage(ChatColor.RED + "You cannot remove that command");
													return false;
												}
											} else {
												player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands repeat remove <command>");
												return false;
											}
										} else {
											player.sendMessage(ChatColor.RED + PERMISSION_STRING);
											return false;
										}
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands repeat <add:list:remove>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + "Invalid Syntax, use /"+ cmd.getName() +" commands repeat <add:list:remove>");
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands <prematch:match:deathmatch:endmatch:repeat>");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " commands <prematch:match:deathmatch:endmatch:repeat>");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("chests")) {
						if(args.length > 1) {
							if(args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("set")) {
								if(player.hasPermission("NMSurvivalGames.Arena.Chests.add")) {
									if(args.length == 4) {
										String item = args[2];
										String ammount = args[3];
										Material mat = Material.getMaterial(item);
										a.getCh().addItem(new ItemStack(mat), Integer.parseInt(ammount));
										player.sendMessage("Added " + ammount + " of " + mat.name());
										return true;
									} else if(args.length == 3) {
										String item = args[2];
										Material mat = Material.getMaterial(item);
										a.getCh().addItem(new ItemStack(mat), 1);
										player.sendMessage("Added  1 of " + mat.name());
										return true;
									} else {
										player.sendMessage("Invalid Syntax, use /" + cmd.getName() + " chests add <material> [ammount]");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + PERMISSION_STRING); 
									return false;
								}
							} else if(args[1].equalsIgnoreCase("remove")) {
								if(player.hasPermission("NMSurvivalGames.Arena.Chests.remove")) {
									if(args.length == 3) {
										String item = args[2];
										a.getCh().removeItem(new ItemStack(Material.getMaterial(item)));
										return true;
									} else {
										player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " chests remove <material>");
										return false;
									}
								} else {
									player.sendMessage(ChatColor.RED + PERMISSION_STRING);
									return false;
								}
							} else if(args[1].equalsIgnoreCase("list")) {
								if(player.hasPermission("NMSurvivalGames.Arena.Chests.list")) {
									player.sendMessage("List of all items and ammounts to add in this arena");
									for(Material m : a.getCh().getItemMap().keySet()) {
										player.sendMessage(m + ": " + a.getCh().getItemMap().get(m));
									}
									return true;
								} else {
									player.sendMessage(ChatColor.RED + PERMISSION_STRING);
									return false;
								}
							} else if(args[1].equalsIgnoreCase("fill")) {
								if(player.hasPermission("NMSurvivalGames.Arena.Chests.fill")) {
									if(args.length == 3) {
										if(args[2].equalsIgnoreCase("add")) {
											a.getCh().addToChestInventories();
											player.sendMessage("Items added to chests.");
											return true;
										} else if(args[2].equalsIgnoreCase("replace")) {
											a.getCh().replaceChestInventories();
											player.sendMessage("Chests refilled");
											return true;
										} else {
											player.sendMessage("Invalid Syntax, use /" + cmd.getName() + " chests fill [add:replace]");
											return false;
										}
									} else {
										player.sendMessage("Chests refilled");
										a.getCh().replaceChestInventories();
										return true;
									}
								} else {
									player.sendMessage(ChatColor.RED + PERMISSION_STRING);
									return false;
								}
							} else if(args[1].equalsIgnoreCase("empty")) {
								if(player.hasPermission("NMSurvivalGames.Arena.Chests.empty")) {
									a.getCh().emptyChests();
									player.sendMessage(ChatColor.GOLD + "ALL chests on this world have been emptied");
									return true;
								} else {
									player.sendMessage(ChatColor.RED + PERMISSION_STRING);
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " chests <add:list:remove:fill:empty>");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " chests <add:list:remove:fill:empty>");
							return false;
						}
					} else if(args[0].equalsIgnoreCase("file")) {
						if(args.length > 1) {
							if(args[1].equalsIgnoreCase("save")) {
								if(player.hasPermission("NMSurvivalGames.Arena.File.save")) {
									a.getW().write();
									player.sendMessage(ChatColor.GOLD + "Arena file saved");
									return true;
								} else {
									return false;
								}
							} else if(args[1].equalsIgnoreCase("defaults")) {
								if(player.hasPermission("NMSurvivalGames.Arena.File.save")) {
									a.getW().writeDefaults();
									player.sendMessage(ChatColor.GOLD + "Arena defaults saved");
									return true;
								} else {
									return false;
								}
							} else if(args[1].equalsIgnoreCase("load")) {
								if(player.hasPermission("NMSurvivalGames.Arena.File.load")) {
									a.getR().read();
									player.sendMessage(ChatColor.GOLD + "Arena file loaded");
									return true;
								} else {
									return false;
								}
							} else {
								player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " file <save:load:defaults>");
								return false;
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " file <save:load:defaults>");
							return false;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " <locations:commands:chests:file>");
						return false;
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid Syntax, use /" + cmd.getName() + " <locations:commands:chests:file>");
					return false;
				} 
			} else {
				player.sendMessage(ChatColor.RED + "There is no arena on this world, please move to one so you can better control it");
				return false;
			}
		} else {
			sender.sendMessage("You currently cannot operate these commands from the command line");
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