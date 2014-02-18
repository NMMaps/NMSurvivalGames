package NMSurvivalGames.Arena.StateMachine;

public class Offline implements ArenaStateLike {

	public ArenaState as;
	
	public Offline(ArenaState as) {
		this.as = as;
	}
	
	@Override
	public int getValue() {
		return -1;
	}

	@Override
	public void nextState() {
		as.setState(new PreMatch(as));
	}

}
