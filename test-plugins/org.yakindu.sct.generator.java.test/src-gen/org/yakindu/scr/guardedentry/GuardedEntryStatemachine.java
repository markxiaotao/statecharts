/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.guardedentry;


public class GuardedEntryStatemachine implements IGuardedEntryStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		
		public void raiseE() {
			e = true;
		}
		
		private boolean guard;
		
		public boolean getGuard() {
			return guard;
		}
		
		public void setGuard(boolean value) {
			this.guard = value;
		}
		
		private boolean done;
		
		public boolean getDone() {
			return done;
		}
		
		public void setDone(boolean value) {
			this.done = value;
		}
		
	}
	
	private static class SCInterfaceEvBuf {
		private boolean e;
	}
	private static class GuardedEntryStatemachineEvBuf {
		private SCInterfaceEvBuf iface = new SCInterfaceEvBuf();
	}
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_A,
		main_region_B,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private GuardedEntryStatemachineEvBuf _current = new GuardedEntryStatemachineEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public GuardedEntryStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		clearInEvents();
		
		sCInterface.setGuard(false);
		
		sCInterface.setDone(false);
		
		isExecuting = false;
	}
	
	public void enter() {
		if (!initialized)
			throw new IllegalStateException(
			        "The state machine needs to be initialized first by calling the init() function.");
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		enterSequence_main_region_default();
		isExecuting = false;
	}
	
	public void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		exitSequence_main_region();
		isExecuting = false;
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
			        "The state machine needs to be initialized first by calling the init() function.");
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		swapInEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region_A:
				main_region_A_react(true);
				break;
			case main_region_B:
				main_region_B_react(true);
				break;
			default:
				// $NullState$
			}
		}
		
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	private void swapInEvents() {
		_current.iface.e = sCInterface.e;
		sCInterface.e = false;
	}
	
	private void clearInEvents() {
		sCInterface.e = false;
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_A:
			return stateVector[0] == State.main_region_A;
		case main_region_B:
			return stateVector[0] == State.main_region_B;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseE() {
		sCInterface.raiseE();
	}
	
	public boolean getGuard() {
		return sCInterface.getGuard();
	}
	
	public void setGuard(boolean value) {
		sCInterface.setGuard(value);
	}
	
	public boolean getDone() {
		return sCInterface.getDone();
	}
	
	public void setDone(boolean value) {
		sCInterface.setDone(value);
	}
	
	/* Entry action for state 'A'. */
	private void entryAction_main_region_A() {
		if (sCInterface.getGuard()) {
			sCInterface.setDone(true);
		}
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		entryAction_main_region_A();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_B;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_main_region_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_main_region_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_A:
			exitSequence_main_region_A();
			break;
		case main_region_B:
			exitSequence_main_region_B();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_A_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (_current.iface.e) {
					exitSequence_main_region_A();
					enterSequence_main_region_B_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (_current.iface.e) {
					exitSequence_main_region_B();
					enterSequence_main_region_A_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
}
