package NMSurvivalGames.Arena.World;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import NMSurvivalGames.Arena.Arena;

public class PlayerHandler {
	
	public Arena a;

	private HashSet<String> deadTeam = new HashSet<String>();
	private HashSet<String> aliveTeam = new HashSet<String>();
	private HashSet<String> adminTeam = new HashSet<String>();
	private HashSet<String> spectatorTeam = new HashSet<String>();
	
	private int maxPlayers;
	private int maxSpectators;
	
	public PlayerHandler(Arena a) {
		setA(a);
		setMaxPlayers(24);
		setMaxSpectators(4);
	}
	
	//
	public void addDead(Player player) {
		deadTeam.add(player.getName());
		aliveTeam.remove(player.getName());
		adminTeam.remove(player.getName());
		spectatorTeam.remove(player.getName());
		player.setDisplayName(ChatColor.RED + "[DEAD] " + player.getName() + ChatColor.RESET);
		player.setCustomName(ChatColor.RED + player.getName() + ChatColor.RESET);
		player.setPlayerListName(ChatColor.RED + player.getName() + ChatColor.RESET);
	}
	
	public void addAlive(Player player) {
		deadTeam.remove(player.getName());
		aliveTeam.add(player.getName());
		adminTeam.remove(player.getName());
		spectatorTeam.remove(player.getName());
		player.setDisplayName(ChatColor.GREEN + "[ALIVE] " + player.getName() + ChatColor.RESET);
		player.setCustomName(ChatColor.GREEN + player.getName() + ChatColor.RESET);
		player.setPlayerListName(ChatColor.GREEN + player.getName() + ChatColor.RESET);
	}
	
	public void addAdmin(Player player) {
		deadTeam.remove(player.getName());
		aliveTeam.remove(player.getName());
		adminTeam.add(player.getName());
		spectatorTeam.remove(player.getName());
		player.setDisplayName(ChatColor.GOLD + "[ADMIN] " + player.getName());
		player.setCustomName(ChatColor.GOLD + "[ADMIN] " + player.getName() + ChatColor.RESET);
		player.setPlayerListName(ChatColor.GOLD + player.getName() + ChatColor.RESET);
	}
	
	public void addSpectator(Player player) {
		deadTeam.remove(player.getName());
		aliveTeam.remove(player.getName());
		adminTeam.remove(player.getName());
		spectatorTeam.add(player.getName());
		player.setCustomName(ChatColor.LIGHT_PURPLE + "[SPEC] " + player.getName() + ChatColor.RESET);
		player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET);
		player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RESET);
	}
	
	public void clearPlayer(Player player) {
		deadTeam.remove(player.getName());
		aliveTeam.remove(player.getName());
		adminTeam.remove(player.getName());
		spectatorTeam.remove(player.getName());
		player.setCustomName(player.getName());
		player.setDisplayName(player.getName());
		player.setPlayerListName(player.getName());
	}
	
	//
	public boolean containsDead(String player) {
		return deadTeam.contains(player);
	}
	
	public boolean containsAlive(String player) {
		return aliveTeam.contains(player);
	}
	
	public boolean containsAdmin(String player) {
		return adminTeam.contains(player);
	}
	
	public boolean containsSpectator(String player) {
		return spectatorTeam.contains(player);
	}
	
	//
	public HashSet<String> getDead() {
		return deadTeam;
	}
	
	public HashSet<String> getAlive() {
		return aliveTeam;
	}
	
	public HashSet<String> getAdmin() {
		return adminTeam;
	}
	
	public HashSet<String> getSpectator() {
		return spectatorTeam;
	}
	
	//
	public int getNumOfPlayers() {
		return getDead().size() + getAlive().size();
	}
	
	public void getPlayers() {
		for(Player p : getA().getWorld().getPlayers()) {
			addDead(p);
		}
	}
	
	//
	public void setMaxPlayers(int max) {
		maxPlayers = max;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public void setMaxSpectators(int max) {
		maxSpectators = max;
	}
	
	public int getMaxSpectators() {
		return maxSpectators;
	}
	public void setA(Arena a) {
		this.a = a;
	}
	
	public Arena getA() {
		return a;
	}
}
