package NMSurvivalGames.Arena.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import NMSurvivalGames.Arena.Arena;

public class RepeatCommands {
	
	private Arena a;
	
	HashMap<String, Integer[]> commands = new HashMap<String, Integer[]>();
	
	public RepeatCommands(Arena a) {
		this.a = a;
	}
	
	public boolean addCommand(int startDelay, int repeatDelay, String command) {
		Integer[] values = new Integer[3];
		values[0] = startDelay;
		values[1] = repeatDelay;
		values[2] = 0;
		commands.put(command, values);
		return true;
	}
	
	public List<String> listCommands() {
		List<String> retcommands = new ArrayList<String>();
		for(String str : commands.keySet()) {
			retcommands.add(str + " : " + commands.get(str)[0] + " : " + commands.get(str)[1]);
		} if(retcommands.isEmpty()) {
			retcommands.add("There are no commands to list");
		}
		return retcommands;
	}
	
	public boolean removeCommand(String command) {
		if(commands.containsKey(command)) {
			commands.remove(command);
			return true;
		}
		return false;
	}
	
	public void runCommands() {
		for(final String command : commands.keySet()) {
			if(commands.get(command) != null) {
				int ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(getA().getAc().getMain(), new Runnable() {
						
						@Override
						public void run() {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
							commands.get(0)[3] = 0;
						}
				
				}, commands.get(command)[0], commands.get(command)[1]);
				commands.get(command)[3] = ID;
			}
		}
	}
	
	public void stopFutureCommands() {
		for(String str : commands.keySet()) {
			if(commands.get(str)[3] != 0) {
				Bukkit.getScheduler().cancelTask(commands.get(str)[3]);
			}
		}
	}

	public Arena getA() {
		return a;
	}
}
