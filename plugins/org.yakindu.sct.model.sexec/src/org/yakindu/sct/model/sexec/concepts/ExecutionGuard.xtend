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

import org.yakindu.sct.model.sexec.ExecutionFlow
import com.google.inject.Inject
import org.yakindu.base.types.Property
import org.yakindu.base.types.TypeBuilder
import org.yakindu.sct.model.sexec.transformation.ExpressionBuilder
import org.yakindu.sct.model.sexec.extensions.SexecBuilder
import org.yakindu.sct.model.sexec.Step

/**
 * An execution guard makes ensures that no concurrent state changes are possible. 
 * 
 * @author aterfloth
 */
class ExecutionGuard {

	public static val IS_EXECUTING = "isExecuting"
	 
	@Inject extension TypeBuilder
	@Inject extension ExpressionBuilder
	@Inject extension SexecBuilder


	def defineFeatures (ExecutionFlow it) {
		it.features += _variable(IS_EXECUTING, _boolean)
	}
	

	def Step _guardExecution(ExecutionFlow it, Step body){
		_sequence(
			_if(isExecutingProperty._ref)._then(_return(null)),
			isExecutingProperty._assign(_true),
				
			body,
			
			isExecutingProperty._assign(_false)	
		)
	}
	
	def _initIsExecuting(ExecutionFlow it) {
		if (isExecutingProperty !== null) 
			isExecutingProperty._assign(_false)
		else
			_empty
	}	

	def Property isExecutingProperty(ExecutionFlow it) {
		features.filter( typeof(Property) ).filter( m | m.name == IS_EXECUTING).head
	}
	
	
}