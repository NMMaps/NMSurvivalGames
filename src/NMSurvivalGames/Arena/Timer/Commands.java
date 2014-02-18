package NMSurvivalGames.Arena.Timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import org.bukkit.Bukkit;

import NMSurvivalGames.Arena.Arena;

public class Commands {
	
	private Arena a;
	
	TreeMap<Integer, HashSet<String>> commands = new TreeMap<Integer, HashSet<String>>();
	TreeMap<Integer, HashMap<String, Integer>> commandIDS = new TreeMap<Integer, HashMap<String, Integer>>();
	
	public Commands(Arena a) {
		this.a = a;
	}
	
	public boolean addCommand(int time, String command) {
		HashSet<String> commandsAtTime;
		if(commands.get(time) == null) {
			commandsAtTime = new HashSet<String>();
		} else {
			commandsAtTime = commands.get(time);
		}
		if(commandsAtTime.add(command)) {
			commands.put(time, commandsAtTime);
			return true;
		} else {
			return false;
		}
	}
	
	public HashSet<String> listCommands() {
		HashSet<String> retcommands = new HashSet<String>();
		for(Integer i : commands.keySet()) {
			for(String str : commands.get(i)) {
				retcommands.add(i + ": " + str);
			}
		}
		if(retcommands.isEmpty()) {
			retcommands.add("There are no commands to list");
		}
		return retcommands;
	}
	
	public HashSet<String> listCommandsAtTime(int time) {
		HashSet<String> retcommands = new HashSet<String>();
		if(commands.containsKey(time)) {
			if(commands.get(time) != null) {
				for(String str : commands.get(time)) {
					retcommands.add(str);
				}
			}
		}
		if(retcommands.isEmpty()) {
			retcommands.add("There are no commands at this time");
		}
		return retcommands;
	}
	
	public boolean removeCommand(int time, String command) {
		if(commands.containsKey(time)) {
			if(commands.get(time) != null) {
				for(String str : commands.get(time)) {
					if(str.equalsIgnoreCase(command)) {
						commands.get(time).remove(command);
						if(commands.get(time).isEmpty()) {
							commands.remove(time);
						}
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean clearCommandTime(int time) {
		if(commands.containsKey(time)) {
			if(commands.get(time) != null) {
				commands.remove(time);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void runCommands() {
		for(final Integer time : commands.keySet()) {
			if(commands.get(time) != null) {
				for(final String str : commands.get(time)) {
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					int ID = Bukkit.getScheduler().scheduleSyncDelayedTask(getA().getAc().getMain(), new Runnable() {

						@Override
						public void run() {
							String command = str;
							if(command.contains("@a")) {
								command = command.replace("@a", a.getWorld().getName());
							}
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
							if(commandIDS.containsKey(time)) {
								if(commandIDS.get(time) != null) {
									if(commandIDS.get(time).containsKey(str)) {
										if(commandIDS.get(time).get(str) != null) {
											commandIDS.get(time).remove(str);
										}
									}
								}
							}
						}
						
					}, time * 20);
					map.put(str, ID);
					commandIDS.put(time, map);
				}
			}
		}
	}
		
	public void stopFutureCommands() {
		for(Integer i : commandIDS.keySet()) {
			for(String str : commandIDS.get(i).keySet()) {
				Bukkit.getScheduler().cancelTask(commandIDS.get(i).get(str));
			}
		}
	}
	
	public void reset() {
		commands.clear();
	}
	
	public TreeMap<Integer, HashSet<String>> getCommands() {
		return commands;
	}
	
	public Arena getA() {
		return a;
	}
}
