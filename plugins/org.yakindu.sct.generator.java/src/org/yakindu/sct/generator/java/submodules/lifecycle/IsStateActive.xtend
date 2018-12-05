package org.yakindu.sct.generator.java.submodules.lifecycle

import com.google.inject.Inject
import org.yakindu.sct.generator.java.FlowCode
import org.yakindu.sct.generator.java.GenmodelEntries
import org.yakindu.sct.generator.java.JavaNamingService
import org.yakindu.sct.generator.java.Naming
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions

class IsStateActive implements org.yakindu.sct.generator.core.submodules.lifecycle.IsStateActive {
	@Inject protected extension Naming
	@Inject protected extension JavaNamingService
	@Inject protected extension SExecExtensions
	@Inject protected extension FlowCode
	@Inject protected extension StateVectorExtensions
	@Inject protected extension GenmodelEntries
	
	override isStateActive(ExecutionFlow flow) '''
		/**
		* Returns true if the given state is currently active otherwise false.
		*/
		public boolean isStateActive(State state) {

			switch (state) {
			«FOR s : flow.states»
			case «s.stateName.asEscapedIdentifier»:
				return «IF s.leaf»stateVector[«s.stateVector.offset»] == State.«s.stateName.asEscapedIdentifier»«ELSE»stateVector[«s.stateVector.offset»].ordinal() >= State.
						«s.stateName.asEscapedIdentifier».ordinal()&& stateVector[«s.stateVector.offset»].ordinal() <= State.«s.subStates.last.stateName.asEscapedIdentifier».ordinal()«ENDIF»;
			«ENDFOR»
			default:
				return false;
			}
		}

	'''
	
}