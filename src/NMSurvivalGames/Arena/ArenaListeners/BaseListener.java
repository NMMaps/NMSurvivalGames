package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.event.Listener;

public class BaseListener implements Listener {
	
	private ArenaListener al;
	
	public BaseListener(ArenaListener al) {
		setAl(al);
		getAl().getA().getAc().getMain().getServer().getPluginManager().registerEvents(this, getAl().getA().getAc().getMain());
	}
	
	public ArenaListener getAl() {
		return al;
	}
	
	public void setAl(ArenaListener al) {
		this.al = al;
	}

}
