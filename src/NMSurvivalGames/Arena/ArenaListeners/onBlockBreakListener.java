package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class onBlockBreakListener extends BaseListener implements Listener{

	public onBlockBreakListener(ArenaListener al) {
		super(al);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(event.getPlayer() != null) {
			if(event.getBlock().getWorld() == getAl().getA().getWorld()) {
				if(!getAl().getA().isBreakBlocks()) {
					if(!getAl().getA().getPh().containsAdmin(event.getPlayer().getName())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

}
