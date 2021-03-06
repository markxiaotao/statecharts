/**
 * Copyright (c) 2020 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.model.sexec.concepts

import org.yakindu.sct.model.sexec.Sequence
import org.eclipse.emf.ecore.EObject
import org.yakindu.sct.model.sexec.Statement
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.types.Expression

/**
 * Named sequences are used to capture specific abstract execution concepts. These behaviors are not defined 
 * in detail by the sexec transformations but are expected to be specifically expanded by the code generators.
 * So they serve as specific hooks. 
 * 
 * Parameters like declarations or expressions may be specified for these concepts and this class provides
 * extension methods which implemebt this parameter handling. 
 * 
 * @author aterfloth
 */
class StateMachineBehaviorConcept {
	
	public static val CONCEPT_NAME_PREFIX = "@"
	
	def isStateMachineConcept (Sequence it) {
		!it.name.nullOrEmpty && it.name.startsWith(CONCEPT_NAME_PREFIX)
	}
	
	def getParameter(Sequence s) {
		s.getParameter(0)
	}
	
	def EObject getParameter(Sequence s, int idx) {
		if (s !== null && s.steps.size > idx) {
			return (s.getExpression(idx) as ElementReferenceExpression)
					.reference
		}
		else return null
	}
	
	def getExpression(Sequence s) {
		s.getExpression(0)
	}
	
	def Expression getExpression(Sequence s, int idx) {
		if (s !== null && s.steps.size > idx) {
			return (s.steps.get(idx) as Statement).expression
		}
		else return null
	}
	
}