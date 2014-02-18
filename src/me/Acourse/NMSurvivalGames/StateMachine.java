package me.Acourse.NMSurvivalGames;

public class StateMachine {

	Main main;

	public StateLike currentState;

	public static final int PreMatch = 0;
	public static final int Match = 1;
	public static final int DeathMatch = 2;
	public static final int AfterMatch = 3;

	StateMachine(Main main) {

		this.main = main;

		setState(new PreMatch(this));
	}

	public int setState(StateLike sl) {

		currentState = sl;
		return sl.getValue();
	}

	public int nextState() {

		currentState.nextState();
		return currentState.getValue();
	}

	public int getState() {

		return currentState.getValue();
	}
}

interface StateLike {

	void nextState();

	int getValue();
}

class PreMatch implements StateLike {

	StateMachine sm;

	int value = 0;

	PreMatch(StateMachine statemachine) {

		sm = statemachine;
	}

	@Override
	public void nextState() {

		sm.setState(new Match(sm));
	}

	@Override
	public int getValue() {

		return value;
	}
}

class Match implements StateLike {

	StateMachine sm;

	int value = 1;

	Match(StateMachine statemachine) {

		sm = statemachine;
	}

	@Override
	public void nextState() {

		sm.setState(new DeathMatch(sm));
	}

	@Override
	public int getValue() {

		return value;
	}
}

class DeathMatch implements StateLike {

	StateMachine sm;

	int value = 2;

	DeathMatch(StateMachine statemachine) {

		sm = statemachine;
	}

	@Override
	public void nextState() {

		sm.setState(new AfterMatch(sm));
	}

	@Override
	public int getValue() {

		return value;
	}
}

class AfterMatch implements StateLike {

	StateMachine sm;

	int value = 3;

	AfterMatch(StateMachine statemachine) {

		sm = statemachine;
	}

	@Override
	public void nextState() {

		sm.setState(new AfterMatch(sm));
	}

	@Override
	public int getValue() {

		return value;
	}
}
