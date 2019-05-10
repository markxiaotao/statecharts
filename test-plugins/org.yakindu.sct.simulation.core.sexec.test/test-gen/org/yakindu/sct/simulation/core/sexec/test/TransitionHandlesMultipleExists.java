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
 * Unit TestCase for TransitionHandlesMultipleExits
 */
@SuppressWarnings("all")
@RunWith(XtextRunner.class)
@InjectWith(SExecInjectionProvider.class)
public class TransitionHandlesMultipleExists extends AbstractExecutionFlowTest {
	
	@Before
	public void setup() throws Exception{
		ExecutionFlow flow = models.loadExecutionFlowFromResource("exits/TransitionHandlesMultipleExits.sct");
		initInterpreter(flow);
	}
	@Test
	public void testTransitionHandling2Of2Exits() throws Exception {
		interpreter.enter();
		assertTrue(isStateActive("AA"));
		raiseEvent("e");
		assertTrue(isStateActive("BB"));
		assertTrue(getInteger("x") == 1l);
		interpreter.exit();
		interpreter.enter();
		setInteger("x", 0l);
		assertTrue(isStateActive("AA"));
		raiseEvent("f");
		assertTrue(isStateActive("BB"));
		assertTrue(getInteger("x") == 2l);
	}
	@Test
	public void testTransitionHandling2Of3Exits() throws Exception {
		interpreter.enter();
		raiseEvent("e");
		assertTrue(isStateActive("BB"));
		setInteger("x", 0l);
		raiseEvent("e");
		assertTrue(isStateActive("AA"));
		assertTrue(getInteger("x") == 11l);
		raiseEvent("e");
		assertTrue(isStateActive("BB"));
		setInteger("x", 0l);
		raiseEvent("g");
		assertTrue(isStateActive("AA"));
		assertTrue(getInteger("x") == 10l);
		interpreter.exit();
	}
	@Test
	public void testTransitionHandling1Of3Exits() throws Exception {
		interpreter.enter();
		raiseEvent("e");
		assertTrue(isStateActive("BB"));
		setInteger("x", 0l);
		raiseEvent("f");
		assertTrue(isStateActive("AA"));
		assertTrue(getInteger("x") == 24l);
		interpreter.exit();
	}
}