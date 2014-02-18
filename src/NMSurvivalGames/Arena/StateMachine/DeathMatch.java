package NMSurvivalGames.Arena.StateMachine;


public class DeathMatch implements ArenaStateLike {

	private ArenaState as;
	
	public DeathMatch(ArenaState as) {
		this.as = as;
	}
	
	@Override
	public int getValue() {
		return 2;
	}

	@Override
	public void nextState() {
		as.setState(new PostMatch(as));
	}

}
