/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.outeventvisibilityeventdriven;

import java.util.LinkedList;
import java.util.Queue;
import org.yakindu.scr.IStatemachine;
import org.yakindu.sct.rx.Observable;

public interface IOutEventVisibilityEventDrivenStatemachine extends IStatemachine {
	
	public interface SCInterface {
	
		public void raiseI(long value);
		
		public boolean isRaisedO1();
		
		public Observable<Void> getO1();
		
		public boolean isRaisedO2();
		
		public Observable<Void> getO2();
		
	}
	
	public SCInterface getSCInterface();
	
}
