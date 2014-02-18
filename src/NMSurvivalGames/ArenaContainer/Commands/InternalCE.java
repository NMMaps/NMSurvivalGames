package NMSurvivalGames.ArenaContainer.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import NMSurvivalGames.Arena.Arena;
import NMSurvivalGames.Arena.StateMachine.ArenaState;
import NMSurvivalGames.Arena.StateMachine.Offline;
import NMSurvivalGames.Arena.StateMachine.PreMatch;
import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class InternalCE implements CommandExecutor {

	private ArenaContainer ac;

	public InternalCE(ArenaContainer ac) {
		setAc(ac);
		getAc().getMain().getCommand("internal").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender) {
			if(args.length > 0) {
				World w = Bukkit.getWorld(args[0]);
				Arena a = getAc().getArenaOnWorld(w);
				if(a != null) {
					if(args.length > 1) {
						if(args[1].equalsIgnoreCase("startprematch")) {
							if(a.getAs().getState().getValue() == ArenaState.PostMatch || a.getAs().getState().getValue() == ArenaState.Offline) {
								a.getAs().setState(new PreMatch(a.getAs()));
								a.getPC().stopFutureCommands();
								a.getMC().stopFutureCommands();
								a.getPDC().stopFutureCommands();
								a.getDC().stopFutureCommands();
								a.getEC().stopFutureCommands();
								a.getPC().runCommands();
								return true;
							} else {
								sender.sendMessage("You cannot run this command at this time.");
								return false;
							}
						} else if(args[1].equalsIgnoreCase("startmatch")) {
							if(a.getAs().getState().getValue() == ArenaState.PreMatch) {
								a.getAs().getState().nextState();
								a.getPC().stopFutureCommands();
								a.getMC().stopFutureCommands();
								a.getPDC().stopFutureCommands();
								a.getDC().stopFutureCommands();
								a.getEC().stopFutureCommands();
								a.getMC().runCommands();
								return true;
							} else {
								sender.sendMessage("You cannot run this command at this time");
								return false;
							}
						} else if(args[1].equalsIgnoreCase("startpredeathmatch")) {
							if(a.getAs().getState().getValue() == ArenaState.Match) {
								a.getAs().getState().nextState();
								a.getPC().stopFutureCommands();
								a.getMC().stopFutureCommands();
								a.getPDC().stopFutureCommands();
								a.getDC().stopFutureCommands();
								a.getEC().stopFutureCommands();
								a.getPDC().runCommands();
								return true;
							} else {
								sender.sendMessage("You cannot run this command at this time");
								return false;
							}
						} else if(args[1].equalsIgnoreCase("startdeathmatch")) {
							if(a.getAs().getState().getValue() == ArenaState.Match) {
								a.getAs().getState().nextState();
								a.getPC().stopFutureCommands();
								a.getMC().stopFutureCommands();
								a.getPDC().stopFutureCommands();
								a.getDC().stopFutureCommands();
								a.getEC().stopFutureCommands();
								a.getDC().runCommands();
								return true;
							} else {
								sender.sendMessage("You cannot run this command at this time");
								return false;
							}
						} else if(args[1].equalsIgnoreCase("startendmatch")){ 
							if(a.getAs().getState().getValue() == ArenaState.DeathMatch) {
								a.getAs().getState().nextState();
								a.getPC().stopFutureCommands();
								a.getMC().stopFutureCommands();
								a.getPDC().stopFutureCommands();
								a.getDC().stopFutureCommands();
								a.getEC().stopFutureCommands();
								a.getEC().runCommands();
								return true;
							} else {
								sender.sendMessage("You cannot run this command at this time");
								return false;
							}
						} else if(args[1].equalsIgnoreCase("stopallcommands")) {
							a.getAs().setState(new Offline(a.getAs()));
							a.getPC().stopFutureCommands();
							a.getMC().stopFutureCommands();
							a.getPDC().stopFutureCommands();
							a.getDC().stopFutureCommands();
							a.getEC().stopFutureCommands();
							return true;
						} else if(args[1].equalsIgnoreCase("startrepeatcommands")) {
							a.getRC().stopFutureCommands();
							a.getRC().runCommands();
							return true;
						} else if(args[1].equalsIgnoreCase("stoprepeatcommands")) {
							a.getRC().stopFutureCommands();
							return true;						
						} else if(args[1].equalsIgnoreCase("teleportplayersstartmatch")){
							a.getSML().teleport(a.getPh().getAlive());
							return true;
						} else if(args[1].equalsIgnoreCase("teleportplayersdeathmatch")){ 
							a.getDML().teleport(a.getPh().getAlive());
							return true;
						} else if(args[1].equalsIgnoreCase("fillchests")){
							a.getCh().replaceChestInventories();
							return true;
						} else if(args[1].equalsIgnoreCase("addchests")){ 
							a.getCh().addToChestInventories();
							return true;
						} else if(args[1].equalsIgnoreCase("reloadworld")){ 
							return true;
						} else if(args[1].equalsIgnoreCase("say")) { 
							if(args.length > 2) {
								String output = ChatColor.DARK_AQUA + "[ARENA - " + w.getName().toUpperCase() + "]: " + ChatColor.RESET;
								for(int i = 2; i < args.length; i++) {
									output += args[i] + " ";
								}
								for(Player p : w.getPlayers()) {
									p.sendMessage(output);
								}
							}
							return true;
						} else if(args[1].equalsIgnoreCase("makealive")) {
							for(String p : a.getPh().getDead()) {
								a.getPh().addAlive(Bukkit.getPlayerExact(p));
							}
							return true;
						} else if(args[1].equalsIgnoreCase("freeze")) {
							if(args.length == 3) {
								a.setFrozen(Boolean.parseBoolean(args[2]));
								return true;
							} else {
								return false;
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void setAc(ArenaContainer ac) {
		this.ac = ac;
	}

	public ArenaContainer getAc() {
		return ac;
	}
}
