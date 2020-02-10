/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.eventdriven;

import java.util.LinkedList;
import java.util.Queue;

public class EventDrivenStatemachine implements IEventDrivenStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean inEvent;
		
		
		public void raiseInEvent() {
			inEvent = true;
			runCycle();
		}
		
		private boolean inEventBool;
		
		private boolean inEventBoolValue;
		
		
		public void raiseInEventBool(final boolean value) {
			inEventBoolValue = value;
			inEventBool = true;
			runCycle();
		}
		protected boolean getInEventBoolValue() {
			if (! inEventBool ) 
				throw new IllegalStateException("Illegal event value access. Event InEventBool is not raised!");
			return inEventBoolValue;
		}
		
		private boolean outEvent;
		
		
		public boolean isRaisedOutEvent() {
			return outEvent;
		}
		
		protected void raiseOutEvent() {
			outEvent = true;
		}
		
		private boolean running;
		
		
		public boolean isRaisedRunning() {
			return running;
		}
		
		protected void raiseRunning() {
			running = true;
		}
		
		private boolean oe1;
		
		
		public boolean isRaisedOe1() {
			return oe1;
		}
		
		protected void raiseOe1() {
			oe1 = true;
		}
		
		private boolean oe2;
		
		
		public boolean isRaisedOe2() {
			return oe2;
		}
		
		protected void raiseOe2() {
			oe2 = true;
		}
		
		private boolean oe3;
		
		
		public boolean isRaisedOe3() {
			return oe3;
		}
		
		protected void raiseOe3() {
			oe3 = true;
		}
		
		protected void clearEvents() {
			inEvent = false;
			inEventBool = false;
		}
		protected void clearOutEvents() {
		
		outEvent = false;
		running = false;
		oe1 = false;
		oe2 = false;
		oe3 = false;
		}
		
	}
	
	
	protected class SCINamedIImpl implements SCINamedI {
	
		private boolean namedInEvent;
		
		
		public void raiseNamedInEvent() {
			namedInEvent = true;
			runCycle();
		}
		
		protected void clearEvents() {
			namedInEvent = false;
		}
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	protected SCINamedIImpl sCINamedI;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_StateA,
		main_region_StateB,
		main_region_StateC,
		main_region_StateE,
		main_region_StateE__region0_State1,
		main_region_StateE__region0_State2,
		main_region_StateE__region0_State3,
		main_region_StateE__region1_State1,
		main_region_StateE__region1_State2,
		main_region_StateF,
		main_region_StateD,
		main_region__final_,
		$NullState$
	};
	
	private final State[] stateVector = new State[2];
	
	private int nextStateIndex;
	
	private Queue<Runnable> internalEventQueue = new LinkedList<Runnable>();
	private boolean locEvent;
	private boolean e1;
	private boolean e2;
	private boolean e3;
	public EventDrivenStatemachine() {
		sCInterface = new SCInterfaceImpl();
		sCINamedI = new SCINamedIImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		enterSequence_main_region_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		
		clearOutEvents();
	
		Runnable task = getNextEvent();
		if (task == null) {
			task = getDefaultEvent();
		}
		
		while (task != null) {
			task.run();
			clearEvents();
			task = getNextEvent();
		}
		
	}
	
	protected void singleCycle() {
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region_StateA:
				main_region_StateA_react(true);
				break;
			case main_region_StateB:
				main_region_StateB_react(true);
				break;
			case main_region_StateC:
				main_region_StateC_react(true);
				break;
			case main_region_StateE__region0_State1:
				main_region_StateE__region0_State1_react(true);
				break;
			case main_region_StateE__region0_State2:
				main_region_StateE__region0_State2_react(true);
				break;
			case main_region_StateE__region0_State3:
				main_region_StateE__region0_State3_react(true);
				break;
			case main_region_StateE__region1_State1:
				main_region_StateE__region1_State1_react(true);
				break;
			case main_region_StateE__region1_State2:
				main_region_StateE__region1_State2_react(true);
				break;
			case main_region_StateF:
				main_region_StateF_react(true);
				break;
			case main_region_StateD:
				main_region_StateD_react(true);
				break;
			case main_region__final_:
				main_region__final__react(true);
				break;
			default:
				// $NullState$
			}
		}
	}
	
	protected Runnable getNextEvent() {
		if(!internalEventQueue.isEmpty()) {
			return internalEventQueue.poll();
		}
		return null;
	}
	
	protected Runnable getDefaultEvent() {
		return new Runnable() {
			@Override
			public void run() {
				singleCycle();
			}
		};
	}
	
	public void exit() {
		exitSequence_main_region();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$;
	}
	
	/** 
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return (stateVector[0] == State.main_region__final_) && (stateVector[1] == State.$NullState$);
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
		sCINamedI.clearEvents();
		locEvent = false;
		e1 = false;
		e2 = false;
		e3 = false;
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCInterface.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_StateA:
			return stateVector[0] == State.main_region_StateA;
		case main_region_StateB:
			return stateVector[0] == State.main_region_StateB;
		case main_region_StateC:
			return stateVector[0] == State.main_region_StateC;
		case main_region_StateE:
			return stateVector[0].ordinal() >= State.
					main_region_StateE.ordinal()&& stateVector[0].ordinal() <= State.main_region_StateE__region1_State2.ordinal();
		case main_region_StateE__region0_State1:
			return stateVector[0] == State.main_region_StateE__region0_State1;
		case main_region_StateE__region0_State2:
			return stateVector[0] == State.main_region_StateE__region0_State2;
		case main_region_StateE__region0_State3:
			return stateVector[0] == State.main_region_StateE__region0_State3;
		case main_region_StateE__region1_State1:
			return stateVector[1] == State.main_region_StateE__region1_State1;
		case main_region_StateE__region1_State2:
			return stateVector[1] == State.main_region_StateE__region1_State2;
		case main_region_StateF:
			return stateVector[0] == State.main_region_StateF;
		case main_region_StateD:
			return stateVector[0] == State.main_region_StateD;
		case main_region__final_:
			return stateVector[0] == State.main_region__final_;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public SCINamedI getSCINamedI() {
		return sCINamedI;
	}
	
	private void raiseLocEvent() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				locEvent = true;					
				singleCycle();
			}
		});
	}
	
	private void raiseE1() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				e1 = true;					
				singleCycle();
			}
		});
	}
	
	private void raiseE2() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				e2 = true;					
				singleCycle();
			}
		});
	}
	
	private void raiseE3() {
	
		internalEventQueue.add( new Runnable() {
			@Override public void run() {
				e3 = true;					
				singleCycle();
			}
		});
	}
	
	public void raiseInEvent() {
		sCInterface.raiseInEvent();
	}
	
	public void raiseInEventBool(boolean value) {
		sCInterface.raiseInEventBool(value);
	}
	
	public boolean isRaisedOutEvent() {
		return sCInterface.isRaisedOutEvent();
	}
	
	public boolean isRaisedRunning() {
		return sCInterface.isRaisedRunning();
	}
	
	public boolean isRaisedOe1() {
		return sCInterface.isRaisedOe1();
	}
	
	public boolean isRaisedOe2() {
		return sCInterface.isRaisedOe2();
	}
	
	public boolean isRaisedOe3() {
		return sCInterface.isRaisedOe3();
	}
	
	/* Entry action for state 'StateA'. */
	private void entryAction_main_region_StateA() {
		sCInterface.raiseRunning();
	}
	
	/* Entry action for state 'StateB'. */
	private void entryAction_main_region_StateB() {
		sCInterface.raiseOe1();
	}
	
	/* Entry action for state 'StateC'. */
	private void entryAction_main_region_StateC() {
		sCInterface.raiseOe2();
	}
	
	/* Entry action for state 'State3'. */
	private void entryAction_main_region_StateE__region0_State3() {
		raiseE3();
	}
	
	/* Entry action for state 'State1'. */
	private void entryAction_main_region_StateE__region1_State1() {
		raiseLocEvent();
	}
	
	/* Entry action for state 'State2'. */
	private void entryAction_main_region_StateE__region1_State2() {
		raiseE2();
	}
	
	/* Entry action for state 'StateD'. */
	private void entryAction_main_region_StateD() {
		sCInterface.raiseOe3();
	}
	
	/* 'default' enter sequence for state StateA */
	private void enterSequence_main_region_StateA_default() {
		entryAction_main_region_StateA();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateA;
	}
	
	/* 'default' enter sequence for state StateB */
	private void enterSequence_main_region_StateB_default() {
		entryAction_main_region_StateB();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateB;
	}
	
	/* 'default' enter sequence for state StateC */
	private void enterSequence_main_region_StateC_default() {
		entryAction_main_region_StateC();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateC;
	}
	
	/* 'default' enter sequence for state StateE */
	private void enterSequence_main_region_StateE_default() {
		enterSequence_main_region_StateE__region0_default();
		enterSequence_main_region_StateE__region1_default();
	}
	
	/* 'default' enter sequence for state State1 */
	private void enterSequence_main_region_StateE__region0_State1_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateE__region0_State1;
	}
	
	/* 'default' enter sequence for state State2 */
	private void enterSequence_main_region_StateE__region0_State2_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateE__region0_State2;
	}
	
	/* 'default' enter sequence for state State3 */
	private void enterSequence_main_region_StateE__region0_State3_default() {
		entryAction_main_region_StateE__region0_State3();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateE__region0_State3;
	}
	
	/* 'default' enter sequence for state State1 */
	private void enterSequence_main_region_StateE__region1_State1_default() {
		entryAction_main_region_StateE__region1_State1();
		nextStateIndex = 1;
		stateVector[1] = State.main_region_StateE__region1_State1;
	}
	
	/* 'default' enter sequence for state State2 */
	private void enterSequence_main_region_StateE__region1_State2_default() {
		entryAction_main_region_StateE__region1_State2();
		nextStateIndex = 1;
		stateVector[1] = State.main_region_StateE__region1_State2;
	}
	
	/* 'default' enter sequence for state StateF */
	private void enterSequence_main_region_StateF_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateF;
	}
	
	/* 'default' enter sequence for state StateD */
	private void enterSequence_main_region_StateD_default() {
		entryAction_main_region_StateD();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateD;
	}
	
	/* Default enter sequence for state null */
	private void enterSequence_main_region__final__default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region__final_;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region  */
	private void enterSequence_main_region_StateE__region0_default() {
		react_main_region_StateE__region0__entry_Default();
	}
	
	/* 'default' enter sequence for region  */
	private void enterSequence_main_region_StateE__region1_default() {
		react_main_region_StateE__region1__entry_Default();
	}
	
	/* Default exit sequence for state StateA */
	private void exitSequence_main_region_StateA() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateB */
	private void exitSequence_main_region_StateB() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateC */
	private void exitSequence_main_region_StateC() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateE */
	private void exitSequence_main_region_StateE() {
		exitSequence_main_region_StateE__region0();
		exitSequence_main_region_StateE__region1();
	}
	
	/* Default exit sequence for state State1 */
	private void exitSequence_main_region_StateE__region0_State1() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State2 */
	private void exitSequence_main_region_StateE__region0_State2() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State3 */
	private void exitSequence_main_region_StateE__region0_State3() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State1 */
	private void exitSequence_main_region_StateE__region1_State1() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state State2 */
	private void exitSequence_main_region_StateE__region1_State2() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateF */
	private void exitSequence_main_region_StateF() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateD */
	private void exitSequence_main_region_StateD() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for final state. */
	private void exitSequence_main_region__final_() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_StateA:
			exitSequence_main_region_StateA();
			break;
		case main_region_StateB:
			exitSequence_main_region_StateB();
			break;
		case main_region_StateC:
			exitSequence_main_region_StateC();
			break;
		case main_region_StateE__region0_State1:
			exitSequence_main_region_StateE__region0_State1();
			break;
		case main_region_StateE__region0_State2:
			exitSequence_main_region_StateE__region0_State2();
			break;
		case main_region_StateE__region0_State3:
			exitSequence_main_region_StateE__region0_State3();
			break;
		case main_region_StateF:
			exitSequence_main_region_StateF();
			break;
		case main_region_StateD:
			exitSequence_main_region_StateD();
			break;
		case main_region__final_:
			exitSequence_main_region__final_();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case main_region_StateE__region1_State1:
			exitSequence_main_region_StateE__region1_State1();
			break;
		case main_region_StateE__region1_State2:
			exitSequence_main_region_StateE__region1_State2();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region  */
	private void exitSequence_main_region_StateE__region0() {
		switch (stateVector[0]) {
		case main_region_StateE__region0_State1:
			exitSequence_main_region_StateE__region0_State1();
			break;
		case main_region_StateE__region0_State2:
			exitSequence_main_region_StateE__region0_State2();
			break;
		case main_region_StateE__region0_State3:
			exitSequence_main_region_StateE__region0_State3();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region  */
	private void exitSequence_main_region_StateE__region1() {
		switch (stateVector[1]) {
		case main_region_StateE__region1_State1:
			exitSequence_main_region_StateE__region1_State1();
			break;
		case main_region_StateE__region1_State2:
			exitSequence_main_region_StateE__region1_State2();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_StateA_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_StateE__region0__entry_Default() {
		enterSequence_main_region_StateE__region0_State1_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region_StateE__region1__entry_Default() {
		enterSequence_main_region_StateE__region1_State1_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_StateA_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (e1) {
					exitSequence_main_region_StateA();
					enterSequence_main_region_StateB_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			if (sCInterface.inEvent) {
				raiseE1();
				
				raiseE2();
				
				raiseE3();
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateB_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (e2) {
					exitSequence_main_region_StateB();
					enterSequence_main_region_StateC_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateC_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (e3) {
					exitSequence_main_region_StateC();
					enterSequence_main_region_StateD_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (e3) {
					exitSequence_main_region_StateE();
					enterSequence_main_region_StateF_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE__region0_State1_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_StateE_react(try_transition)==false) {
				if (locEvent) {
					exitSequence_main_region_StateE__region0_State1();
					enterSequence_main_region_StateE__region0_State2_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE__region0_State2_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_StateE_react(try_transition)==false) {
				if (e2) {
					exitSequence_main_region_StateE__region0_State2();
					enterSequence_main_region_StateE__region0_State3_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			if (sCInterface.inEvent) {
				raiseE1();
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE__region0_State3_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_StateE_react(try_transition)==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE__region1_State1_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (e1) {
				exitSequence_main_region_StateE__region1_State1();
				enterSequence_main_region_StateE__region1_State2_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateE__region1_State2_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
	private boolean main_region_StateF_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (sCINamedI.namedInEvent) {
					exitSequence_main_region_StateF();
					enterSequence_main_region__final__default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateD_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (((sCInterface.inEventBool) && (sCInterface.getInEventBoolValue()==true))) {
					exitSequence_main_region_StateD();
					enterSequence_main_region_StateE_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region__final__react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
