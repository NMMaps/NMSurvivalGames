package NMSurvivalGames.Arena.StateMachine;


public class PostMatch implements ArenaStateLike {

	private ArenaState as;
	
	public PostMatch(ArenaState as) {
		this.as = as;
	}
	
	@Override
	public int getValue() {
		return 3;
	}

	@Override
	public void nextState() {
		as.setState(new PreMatch(as));
	}
}
