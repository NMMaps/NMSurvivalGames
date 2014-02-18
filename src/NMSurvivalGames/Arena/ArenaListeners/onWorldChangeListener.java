package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class onWorldChangeListener implements Listener {

	private ArenaListener al;

	public onWorldChangeListener(ArenaListener al) {
		setAl(al);
		getAl().getA().getAc().getMain().getServer().getPluginManager().registerEvents(this, getAl().getA().getAc().getMain());
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onWorldSwitch(PlayerChangedWorldEvent event) {
		if(event.getFrom() == getAl().getA().getWorld()) {
			getAl().getA().getPh().clearPlayer(event.getPlayer());
		} else if (event.getPlayer().getWorld() == getAl().getA().getWorld()) {
			if(getAl().getA().getLock() && !event.getPlayer().hasPermission("NMSurvivalGames.Arena.bypasslock")) {
				event.getPlayer().sendMessage("Sorry, this world is locked. We just teleported you back to the world you were in.");
				event.getPlayer().teleport(event.getFrom().getSpawnLocation());
			} else {
				if(getAl().getA().getPh().getNumOfPlayers() < getAl().getA().getPh().getMaxPlayers()) {
					getAl().getA().getPh().addDead(event.getPlayer());
				} else {
					event.getPlayer().sendMessage("Sorry, this world is full. We just teleported you back to the world you were in.");
					event.getPlayer().teleport(event.getFrom().getSpawnLocation());
				}
			}
		}
	}

	public ArenaListener getAl() {
		return al;
	}

	public void setAl(ArenaListener al) {
		this.al = al;
	}
}
