package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class onLoginListener extends BaseListener implements Listener {
	
	public onLoginListener(ArenaListener al) {
		super(al);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onLogin(final PlayerLoginEvent event) {
		
		/*
		 * Must delay, the player position at login is always the world start position (i.e. world, starting spawn point)
		 */
		Bukkit.getScheduler().scheduleSyncDelayedTask(getAl().getA().getAc().getMain(), new Runnable() {
			
			@Override
			public void run() {
				if(event.getPlayer().getWorld() == getAl().getA().getWorld()) {
					if(getAl().getA().getPh().getNumOfPlayers() >= getAl().getA().getPh().getMaxPlayers()) {
						event.disallow(Result.KICK_FULL, ChatColor.RED + "The world is full! We are trying to implement something where it will default you to a specific world!");
					} else {
						getAl().getA().getPh().addDead(event.getPlayer());
					}
				}
			}
			
		}, 20);
	}	
}
