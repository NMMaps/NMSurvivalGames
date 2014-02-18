package NMSurvivalGames.Arena.StateMachine;


public class PreMatch implements ArenaStateLike {

	ArenaState as;
	
	public PreMatch(ArenaState as) {
		this.as = as;
	}
	
	@Override
	public int getValue() {
		return 0;		
	}

	@Override
	public void nextState() {
		as.setState(new Match(as));
	}

}
