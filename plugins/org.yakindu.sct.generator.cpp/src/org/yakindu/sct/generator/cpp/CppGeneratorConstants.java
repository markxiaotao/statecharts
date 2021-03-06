package org.yakindu.sct.generator.cpp;

public abstract class CppGeneratorConstants {
	/*
	 * TYPES
	 */
	public static final String ERROR_TYPE = "sc_errorCode";
	public static final String SCT_EVENT = "SctEvent";
	public static final String TYPED_SCT_EVENT = "TypedSctEvent";

	/*
	 * FUNCTIONS
	 */
	public static final String STATES_COUNT = "numStates";
	public static final String GET_TIMER = "getTimer";
	public static final String NEXT_EVENT = "getNextEvent";

	/*
	 * CONSTANTS
	 */
	public static final String MAX_ORTHOGONAL_STATES = "maxOrthogonalStates";
	public static final String MAX_HISTORY_STATES = "maxHistoryStates";
	public static final String TIMER = "timer";
	public static final String TIME_EVENTS_COUNT = "timeEventsCount";
	public static final String PARALLEL_TIME_EVENTS_COUNT = "parallelTimeEventsCount";
	public static final String TIME_EVENTS = "timeEvents";
	public static final String PARENT = "parent";
	
	/*
	 * CLASS NAMES
	 */
	public static final String SM_INTERFACE = "StatemachineInterface";
	public static final String SM_TIMED_INTERFACE = "TimedStatemachineInterface";
	public static final String TIMER_INTERFACE = "TimerInterface";
	public static final String SM_TRACING = "scTracing";
	public static final String TRACE_OBSERVER = "TraceObserver";
	
	/*
	 * YSC NAMESPACE 
	 */
	public static final String YSC_NAMESPACE = "ysc";
	
	/*
	 * FILES
	 */
	public static final String RXCPP_MODULE = "sc_rxcpp";
}
