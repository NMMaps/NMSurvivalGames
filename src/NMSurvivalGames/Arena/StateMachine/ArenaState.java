package NMSurvivalGames.Arena.StateMachine;

public class ArenaState {

	private ArenaStateLike state;
	
	public static final int Offline = -1;
	public static final int PreMatch = 0;
	public static final int Match = 1;
	public static final int DeathMatch = 2;
	public static final int PostMatch = 3;
	
	public ArenaState() {
		setState(new Offline(this));
	}

	public ArenaStateLike getState() {
		return state;
	}

	public void setState(ArenaStateLike state) {
		this.state = state;
	}
}
