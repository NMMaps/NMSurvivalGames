package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class onBlockPlaceListener extends BaseListener implements Listener{

	public onBlockPlaceListener(ArenaListener al) {
		super(al);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onBlockPlacedEvent(BlockPlaceEvent event) {
		if(event.getPlayer() != null) {
			if(event.getBlock().getWorld() == getAl().getA().getWorld()) {
				if(!getAl().getA().isPlaceBlocks()) {
					if(!getAl().getA().getPh().containsAdmin(event.getPlayer().getName())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
