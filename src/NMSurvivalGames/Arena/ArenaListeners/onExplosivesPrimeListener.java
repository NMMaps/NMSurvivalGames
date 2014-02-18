package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class onExplosivesPrimeListener implements Listener {
	
	private ArenaListener al;

	public onExplosivesPrimeListener(ArenaListener al) {
		setAl(al);
		getAl().getA().getAc().getMain().getServer().getPluginManager().registerEvents(this, getAl().getA().getAc().getMain());
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onExplosivesPrime(ExplosionPrimeEvent event) {
		if(event.getEntity().getType() == EntityType.PRIMED_TNT) {
			System.out.println("Explosion Cancelled");
			event.setCancelled(true);
		}
	}
	
	public ArenaListener getAl() {
		return al;
	}
	
	public void setAl(ArenaListener al) {
		this.al = al;
	}
	
}
