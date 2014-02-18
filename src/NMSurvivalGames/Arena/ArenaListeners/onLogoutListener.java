package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLogoutListener implements Listener {
	
	private ArenaListener al;
	
	public onLogoutListener(ArenaListener al) {
		this.al = al;
		getAl().getA().getAc().getMain().getServer().getPluginManager().registerEvents(this, getAl().getA().getAc().getMain());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent event) {
		if(event.getPlayer().getWorld() == getAl().getA().getWorld()) {
			getAl().getA().getPh().clearPlayer(event.getPlayer());
		}
	}
	
	public void setAl(ArenaListener al) {
		this.al = al;
	}
	
	public ArenaListener getAl() {
		return al;
	}
	
}
