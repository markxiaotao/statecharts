/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.constants;


public class ConstantsStatemachine implements IConstantsStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		
		public void raiseE() {
			e = true;
		}
		
		private boolean e2;
		
		private long e2Value;
		
		
		public void raiseE2(long value) {
			e2Value = value;
			e2 = true;
		}
		protected long getE2Value() {
			if (! e2 ) 
				throw new IllegalStateException("Illegal event value access. Event E2 is not raised!");
			return e2Value;
		}
		
		public long getX() {
			return x;
		}
		
		public long getY() {
			return y;
		}
		
		private long result;
		
		public long getResult() {
			return result;
		}
		
		public void setResult(long value) {
			this.result = value;
		}
		
	}
	
	protected class SCINamedImpl implements SCINamed {
	
		public String getY() {
			return y;
		}
		
		public long getTwo() {
			return two;
		}
		
	}
	
	private static class SCInterfaceEvBuf {
		private boolean e;
		private boolean e2;
		
		private long e2Value;
	}
	private static class ConstantsStatemachineEvBuf {
		private SCInterfaceEvBuf iface = new SCInterfaceEvBuf();
	}
	protected SCInterfaceImpl sCInterface;
	
	protected SCINamedImpl sCINamed;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_A,
		main_region_B,
		main_region_C,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	protected long getInternalConstant() {
		return internalConstant;
	}
	
	
	private ConstantsStatemachineEvBuf _current = new ConstantsStatemachineEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public ConstantsStatemachine() {
		sCInterface = new SCInterfaceImpl();
		sCINamed = new SCINamedImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		clearInEvents();
		
		sCInterface.setResult(0);
		
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
			case main_region_C:
				main_region_C_react(true);
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
		
		_current.iface.e2 = sCInterface.e2;
		_current.iface.e2Value = sCInterface.e2Value;
		sCInterface.e2 = false;
	}
	
	private void clearInEvents() {
		sCInterface.e = false;
		sCInterface.e2 = false;
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
		case main_region_C:
			return stateVector[0] == State.main_region_C;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public SCINamed getSCINamed() {
		return sCINamed;
	}
	
	public void raiseE() {
		sCInterface.raiseE();
	}
	
	public void raiseE2(long value) {
		sCInterface.raiseE2(value);
	}
	
	public long getX() {
		return sCInterface.getX();
	}
	
	public long getY() {
		return sCInterface.getY();
	}
	
	public long getResult() {
		return sCInterface.getResult();
	}
	
	public void setResult(long value) {
		sCInterface.setResult(value);
	}
	
	/* Entry action for state 'B'. */
	private void entryAction_main_region_B() {
		sCInterface.setResult((SCINamed.two * SCInterface.x));
	}
	
	/* Entry action for state 'C'. */
	private void entryAction_main_region_C() {
		sCInterface.setResult((sCInterface.result * IConstantsStatemachine.internalConstant));
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_B_default() {
		entryAction_main_region_B();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_main_region_C_default() {
		entryAction_main_region_C();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_C;
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
	
	/* Default exit sequence for state C */
	private void exitSequence_main_region_C() {
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
		case main_region_C:
			exitSequence_main_region_C();
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
					enterSequence_main_region_C_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (_current.iface.e2) {
					exitSequence_main_region_C();
					sCInterface.setResult((((_current.iface.e2Value * SCInterface.x) * SCINamed.two) * IConstantsStatemachine.internalConstant));
					
					enterSequence_main_region_A_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
}
