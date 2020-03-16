/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.timedtransitions;

import org.yakindu.scr.IStatemachine;
import org.yakindu.scr.ITimerCallback;

public interface ITimedTransitionsStatemachine extends ITimerCallback,IStatemachine {
	
	public interface SCInterface {
	
		public long getSeconds();
		
		public void setSeconds(long value);
		
		public long getCycles();
		
		public void setCycles(long value);
		
	}
	
	public SCInterface getSCInterface();
	
}
