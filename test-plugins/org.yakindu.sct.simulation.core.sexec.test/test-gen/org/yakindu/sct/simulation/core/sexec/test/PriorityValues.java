/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.sct.simulation.core.sexec.test;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yakindu.sct.model.sexec.ExecutionFlow;
import org.yakindu.sct.model.sexec.interpreter.test.util.AbstractExecutionFlowTest;
import org.yakindu.sct.model.sexec.interpreter.test.util.SExecInjectionProvider;
import org.yakindu.sct.test.models.SCTUnitTestModels;
import com.google.inject.Inject;
import static org.junit.Assert.*;

/**
 * Unit TestCase for PriorityValues
 */
@SuppressWarnings("all")
@RunWith(XtextRunner.class)
@InjectWith(SExecInjectionProvider.class)
public class PriorityValues extends AbstractExecutionFlowTest {
	
	@Before
	public void setup() throws Exception{
		ExecutionFlow flow = models.loadExecutionFlowFromResource("PriorityValues.sct");
		initInterpreter(flow);
	}
	@Test
	public void transitionPriority() throws Exception {
		interpreter.enter();
		assertTrue(isStateActive("A"));
		raiseEvent("event1");
		timer.timeLeap(getCyclePeriod());
		assertTrue(isStateActive("C"));
	}
	@Test
	public void regionPriority() throws Exception {
		interpreter.enter();
		assertTrue(isStateActive("A"));
		raiseEvent("event2");
		timer.timeLeap(getCyclePeriod());
		assertTrue(isStateActive("B"));
		assertTrue(!isStateActive("E"));
	}
}
