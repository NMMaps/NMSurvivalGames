package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMovementListener extends BaseListener implements Listener {

	public onPlayerMovementListener(ArenaListener al) {
		super(al);
	}
	
	@EventHandler
	public void onPlayerMovement(PlayerMoveEvent event) {
		if(event.getPlayer().getWorld() == getAl().getA().getWorld()) {
			if(getAl().getA().isFrozen()) {
				if(getAl().getA().getPh().containsAlive(event.getPlayer().getName())) {
					if(event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
						event.setTo(event.getFrom().getBlock().getLocation().add(.5,0,.5).setDirection(event.getFrom().getDirection()));
						event.getPlayer().sendMessage(ChatColor.RED + "You cannot move off this block! Please do not try again until the game starts!");
					}
				}
			}
		}
	}
}
