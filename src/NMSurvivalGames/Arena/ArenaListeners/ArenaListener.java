package NMSurvivalGames.Arena.ArenaListeners;

import NMSurvivalGames.Arena.Arena;

public class ArenaListener {
	
	private Arena a;
	
	public ArenaListener(Arena a) {
		setA(a);
		
		new onLoginListener(this);
		new onLogoutListener(this);
		new onWorldChangeListener(this);
		new onExplosivesPrimeListener(this);
		new onDeathListener(this);
		new onPlayerMovementListener(this);
		new onBlockBreakListener(this);
		new onBlockPlaceListener(this);
	}

	public Arena getA() {
		return a;
	}

	public void setA(Arena a) {
		this.a = a;
	}
}
