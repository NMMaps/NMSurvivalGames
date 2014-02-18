package NMSurvivalGames.Arena.ArenaListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import NMSurvivalGames.Arena.StateMachine.ArenaState;
import NMSurvivalGames.Arena.StateMachine.PostMatch;

public class onDeathListener extends BaseListener implements Listener {

	public onDeathListener(ArenaListener al) {
		super(al);
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void deathListener(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player killed = (Player) event.getEntity();
			System.out.println(killed + " has died");
			if(killed.getWorld() == getAl().getA().getWorld()) {
				if(getAl().getA().getPh().containsAlive(killed.getName())) {
					getAl().getA().getPh().addDead(killed);
					if(killed.getKiller() instanceof Player) {
						event.setDeathMessage(ChatColor.RED + killed.getName() + ChatColor.AQUA + " has been killed by " + ChatColor.GREEN + killed.getKiller().getName() + ChatColor.AQUA + ", and ended their survival games.");
					} else {
						event.setDeathMessage(ChatColor.RED + killed.getName() + ChatColor.AQUA + " has died, and ended their survival games.");
					}
					
					if(getAl().getA().getPh().getAlive().size() == 1) {
						getAl().getA().getAs().setState(new PostMatch(getAl().getA().getAs()));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "internal " + killed.getWorld() + " startendmatch");
						for(String p : getAl().getA().getPh().getAlive()) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "internal " + killed.getWorld() + " say " + ChatColor.GOLD + p + " has won the survival games!");
						}
					} else if(getAl().getA().getPh().getAlive().size() <= 3) {
						if(getAl().getA().getAs().getState().getValue() == ArenaState.Match) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "internal " + killed.getWorld() + " startpredeathmatch");
							getAl().getA().getAs().getState().nextState();
						}
					} else {
						event.setDeathMessage(ChatColor.YELLOW + "" + getAl().getA().getPh().getAlive().size() + "" + ChatColor.RESET + " players left in the survival games");
					}
					
					killed.getWorld().strikeLightningEffect(killed.getLocation().add(0, 100, 0));
				} else {
					event.setDeathMessage("");
				}
			}
		}
	}
}
