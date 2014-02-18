package NMSurvivalGames.Arena.StateMachine;


public class Match implements ArenaStateLike {

	public ArenaState as;
	
	public Match(ArenaState as) {
		this.as = as;
	}
	
	@Override
	public int getValue() {
		return 1;
	}

	@Override
	public void nextState() {
		as.setState(new DeathMatch(as));
	}

}
