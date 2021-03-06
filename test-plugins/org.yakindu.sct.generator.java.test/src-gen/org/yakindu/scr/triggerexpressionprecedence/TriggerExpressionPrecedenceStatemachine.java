/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.triggerexpressionprecedence;


public class TriggerExpressionPrecedenceStatemachine implements ITriggerExpressionPrecedenceStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e1;
		
		
		public void raiseE1() {
			e1 = true;
		}
		
		private boolean e2;
		
		
		public void raiseE2() {
			e2 = true;
		}
		
		private boolean c1;
		
		public boolean getC1() {
			return c1;
		}
		
		public void setC1(boolean value) {
			this.c1 = value;
		}
		
		private boolean c2;
		
		public boolean getC2() {
			return c2;
		}
		
		public void setC2(boolean value) {
			this.c2 = value;
		}
		
		private boolean e1_transition;
		
		public boolean getE1_transition() {
			return e1_transition;
		}
		
		public void setE1_transition(boolean value) {
			this.e1_transition = value;
		}
		
		private boolean e2_transition;
		
		public boolean getE2_transition() {
			return e2_transition;
		}
		
		public void setE2_transition(boolean value) {
			this.e2_transition = value;
		}
		
	}
	
	private static class SCInterfaceEvBuf {
		private boolean e1;
		private boolean e2;
	}
	private static class TriggerExpressionPrecedenceStatemachineEvBuf {
		private SCInterfaceEvBuf iface = new SCInterfaceEvBuf();
	}
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		triggerExpressionPrecedence_main_region_A,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private TriggerExpressionPrecedenceStatemachineEvBuf _current = new TriggerExpressionPrecedenceStatemachineEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public TriggerExpressionPrecedenceStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		clearInEvents();
		
		sCInterface.setC1(false);
		
		sCInterface.setC2(false);
		
		sCInterface.setE1_transition(false);
		
		sCInterface.setE2_transition(false);
		
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
			case triggerExpressionPrecedence_main_region_A:
				main_region_A_react(true);
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
		_current.iface.e1 = sCInterface.e1;
		sCInterface.e1 = false;
		
		_current.iface.e2 = sCInterface.e2;
		sCInterface.e2 = false;
	}
	
	private void clearInEvents() {
		sCInterface.e1 = false;
		sCInterface.e2 = false;
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case triggerExpressionPrecedence_main_region_A:
			return stateVector[0] == State.triggerExpressionPrecedence_main_region_A;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseE1() {
		sCInterface.raiseE1();
	}
	
	public void raiseE2() {
		sCInterface.raiseE2();
	}
	
	public boolean getC1() {
		return sCInterface.getC1();
	}
	
	public void setC1(boolean value) {
		sCInterface.setC1(value);
	}
	
	public boolean getC2() {
		return sCInterface.getC2();
	}
	
	public void setC2(boolean value) {
		sCInterface.setC2(value);
	}
	
	public boolean getE1_transition() {
		return sCInterface.getE1_transition();
	}
	
	public void setE1_transition(boolean value) {
		sCInterface.setE1_transition(value);
	}
	
	public boolean getE2_transition() {
		return sCInterface.getE2_transition();
	}
	
	public void setE2_transition(boolean value) {
		sCInterface.setE2_transition(value);
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.triggerExpressionPrecedence_main_region_A;
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
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case triggerExpressionPrecedence_main_region_A:
			exitSequence_main_region_A();
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
				if (((_current.iface.e1) && ((sCInterface.getC1() || sCInterface.getC2())))) {
					exitSequence_main_region_A();
					sCInterface.setE1_transition(true);
					
					enterSequence_main_region_A_default();
				} else {
					if (_current.iface.e2) {
						exitSequence_main_region_A();
						sCInterface.setE2_transition(true);
						
						enterSequence_main_region_A_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
}
