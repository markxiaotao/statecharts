/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	Thomas Kutz, René Beckmann - initial API and implementation
 * 
 */
package org.yakindu.base.expressions.inferrer

import com.google.common.collect.ListMultimap
import com.google.inject.Inject
import java.util.List
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.GenericElement
import org.yakindu.base.types.Operation
import org.yakindu.base.types.Parameter
import org.yakindu.base.types.PrimitiveType
import org.yakindu.base.types.Property
import org.yakindu.base.types.Type
import org.yakindu.base.types.TypeParameter
import org.yakindu.base.types.TypeSpecifier
import org.yakindu.base.types.inferrer.ITypeSystemInferrer.InferenceResult
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.base.types.validation.IValidationIssueAcceptor
import org.yakindu.base.types.validation.IValidationIssueAcceptor.ListBasedValidationIssueAcceptor
import org.yakindu.base.types.validation.IValidationIssueAcceptor.ValidationIssue
import org.yakindu.base.types.validation.IValidationIssueAcceptor.ValidationIssue.Severity
import org.yakindu.base.types.validation.TypeValidator

import static org.yakindu.base.expressions.inferrer.ExpressionsTypeInferrerMessages.*
import static org.yakindu.base.types.inferrer.ITypeSystemInferrer.NOT_COMPATIBLE_CODE
import static org.yakindu.base.types.inferrer.ITypeSystemInferrer.NOT_INFERRABLE_TYPE_PARAMETER_CODE
import com.google.common.collect.Lists
import java.util.Collection
import java.util.Collections

/**
 * Infers the actual type for a type parameter used in generic elements like operations or complex types.
 * <p>
 * For a generic operation, the actual type is inferred from the arguments used in the operation call:
 * <ul>
 * <li><code>op(p: T)</code> called as <code>op(10)</code> implies that <code>T</code> is <code>integer</code></li>
 * <li><code>op(p1: T, p2: T)</code> called as <code>op(10, 1.5)</code> implies that <code>T</code> is <code>real</code> (common type of <code>integer</code> and <code>real</code>)</li>
 * </ul>
 * 
 * For a generic type, the actual type of its type parameter is inferred from the type instantiation:
 * <ul>
 * <li><code>CPT&lt;T&gt;</code> instantiated as <code>var x : CPT&lt;integer&gt;</code> implies that <code>T</code> is <code>integer</code></li>
 * <li><code>CPT&lt;T&gt;</code> instantiated as <code>var x : CPT&lt;List&lt;integer&gt;&gt;</code> implies that <code>T</code> is <code>List&lt;integer&gt;</code></li>
 * </ul>
 */
class TypeParameterInferrer {

	@Inject ITypeSystem registry;
	@Inject TypeValidator typeValidator;

	/**
	 * Goes through the parameters and the arguments of the operation and the operation call
	 * and tries to infer the type of the TypeParameters for this operation call.
	 * 
	 * @param parameters the parameters of the operation
	 * @param arguments the inferred types of operation call's arguments
	 * @param inferredTypeParameterTypes map of type parameters to their inference result which will be filled by this method
	 */
	def void inferTypeParametersFromOperationArguments(List<Parameter> parameters, List<InferenceResult> arguments,
		ListMultimap<TypeParameter, InferenceResult> typeConstraints, IValidationIssueAcceptor acceptor) {
		if (parameters.size <= arguments.size) {
			for (var i = 0; i < parameters.size; i++) {
				val parameter = parameters.get(i).typeSpecifier;
				val argument = arguments.get(i);
				if (parameterContainsTypeParameter(parameter)) {
					val thisIssueAcceptor = createIssueAcceptor
					assertArgumentAndParameterSoftCompatible(argument, parameter, [
						thisIssueAcceptor.accept(it);
						acceptor.accept(it)
					])
					if (thisIssueAcceptor.traces.empty) {
						inferTypeParameterFromOperationArgument(parameter, argument, typeConstraints,
							acceptor)
					}
				}
			}
		}
	}

	def protected boolean parameterContainsTypeParameter(TypeSpecifier specifier) {
		val type = specifier?.type
		if (type instanceof PrimitiveType) {
			return false
		}
		if (type instanceof TypeParameter) {
			return true
		}
		if (type instanceof ComplexType) {
			if (type.typeParameters !== null) {
				return true;
			} else {
				for (prop : type.features.filter(Property)) {
					if (prop.typeSpecifier.parameterContainsTypeParameter) {
						return true
					}
				}
			}
		}
		return false
	}

	def protected void inferTypeParameterFromOperationArgument(TypeSpecifier parameterTypeSpecifier,
		InferenceResult argumentType, ListMultimap<TypeParameter, InferenceResult> typeConstraints,
		IValidationIssueAcceptor acceptor) {
		val parameterType = parameterTypeSpecifier?.type

		if (parameterType instanceof TypeParameter) {
			doInferTypeParameterFromOperationArgument(parameterType, argumentType, typeConstraints, acceptor)
		} else if (parameterType instanceof GenericElement) {
			doInferGenericTypeFromOperationArgument(parameterTypeSpecifier, argumentType, typeConstraints,
				acceptor)
		}
	}

	def protected doInferGenericTypeFromOperationArgument(TypeSpecifier parameterTypeSpecifier,
		InferenceResult argumentType, ListMultimap<TypeParameter, InferenceResult> typeConstraints,
		IValidationIssueAcceptor acceptor) {
		for (var i = 0; i < parameterTypeSpecifier.typeArguments.size; i++) {
			val typeParameter = parameterTypeSpecifier.typeArguments.get(i);
			if (argumentType.bindings.size <= i) {
				acceptor.error(typeParameter, NOT_INFERRABLE_TYPE_PARAMETER_CODE)
			} else {
				val typeArgument = argumentType.bindings.get(i);
				inferTypeParameterFromOperationArgument(typeParameter, typeArgument, typeConstraints,
					acceptor);
			}
		}
	}

	def protected doInferTypeParameterFromOperationArgument(TypeParameter typeParameter, InferenceResult argumentType,
		ListMultimap<TypeParameter, InferenceResult> typeConstraints, IValidationIssueAcceptor acceptor) {

		val newMappedType = argumentType.type;
		typeConstraints.put(typeParameter, InferenceResult.from(newMappedType, argumentType.bindings));
	}

	/**
	 * For a given inference result with potentially unresolved type parameters a new inference result is built by 
	 * resolving type parameters based on the given type parameter inference map.
	 * For generic types this method calls itself recursively to fill all nested type parameters.
	 */
	def protected InferenceResult buildInferenceResult(InferenceResult oldInferenceResult, ListMultimap<TypeParameter, InferenceResult> typeConstraints, IValidationIssueAcceptor acceptor) {
		val oldInferredType = oldInferenceResult?.type
		if (oldInferredType instanceof TypeParameter) {
			// get already inferred type from type parameter map
			val mappedTypes = typeConstraints.get(oldInferredType);
			if (mappedTypes.isEmpty || mappedTypes.size > 1) {
				acceptor.warning(oldInferredType, NOT_INFERRABLE_TYPE_PARAMETER_CODE)
				return null
			} else {
				return mappedTypes.head;
			}
		} else if (oldInferredType instanceof GenericElement) {
			val List<InferenceResult> newBindings = newArrayList()
			for (InferenceResult oldBinding : oldInferenceResult.bindings) {
				newBindings.add(buildInferenceResult(oldBinding, typeConstraints, acceptor))
			}
			return InferenceResult.from(oldInferenceResult.type, newBindings);
		}
		return oldInferenceResult;
	}
	
	/**
	 * recursive resolution of type, e.g. when we have E->F and F->Int in map, the method will return Int for E.
	 */
	def protected InferenceResult resolveType(ListMultimap<TypeParameter, InferenceResult> typeConstraints, TypeParameter key) {
		val res = typeConstraints.get(key)
		if (res.isEmpty)
			return null

		// TODO, only first result is used
		if (res.get(0).type instanceof TypeParameter) {
			val recursiveRes = resolveType(typeConstraints, res.get(0).type as TypeParameter)
			if (recursiveRes === null) {
				return res.get(0)
			}
			return recursiveRes
		}
		return res.get(0)
	}
	
	
	def void solve(ListMultimap<TypeParameter, InferenceResult> typeConstraints, IValidationIssueAcceptor acceptor) {
		typeConstraints.asMap.forEach[typeParam, inferenceResults|
			val concreteTypes = inferenceResults.map[toConcreteType(typeConstraints)].flatten
			val commonType = getCommonType(concreteTypes, acceptor)
			if (commonType === null) {
				acceptor.error(
					String.format(INFER_COMMON_TYPE, typeParam.name, concreteTypes.map[type.name]), NOT_INFERRABLE_TYPE_PARAMETER_CODE)
			}
			typeConstraints.replaceValues(typeParam, Lists.newArrayList(commonType))
		]
	}
	
	def Iterable<InferenceResult> toConcreteType(InferenceResult result, ListMultimap<TypeParameter, InferenceResult> typeConstraints) {
		if (result.type instanceof TypeParameter) {
			val concreteTypes = typeConstraints.get(result.type as TypeParameter)
			return concreteTypes.map[toConcreteType(typeConstraints)].flatten
		}
		return Collections.singletonList(result)
	}
	
	def InferenceResult getCommonType(Iterable<InferenceResult> results, IValidationIssueAcceptor acceptor) {
		results.reduce [ type1, type2 |
			getCommonType(type1, type2, acceptor)
		]
	}
	
	def InferenceResult getCommonType(InferenceResult type1, InferenceResult type2, IValidationIssueAcceptor acceptor) {
		if(type1 === null || type2 === null) return null
		
		val errorMsg = String.format(INCOMPATIBLE_TYPES, type1.toString, type2.toString)
		val thisIssueAcceptor = createIssueAcceptor
		typeValidator.assertTypeBindingsSame(type1, type2, errorMsg, [
			thisIssueAcceptor.accept(it);
			acceptor.accept(it) // TODO: is this intermediate error relevant to be reported?
		])
		val commonType = registry.getCommonTypeWithConversion(type1.type, type2.type)

		if (commonType === null || !thisIssueAcceptor.traces.isEmpty) {
			return null
		}

		return InferenceResult.from(commonType, type1.bindings)
	}

	def void inferTypeParametersFromOwner(InferenceResult operationOwnerResult, ListMultimap<TypeParameter, InferenceResult> typeConstraints) {
		val operationOwnerType = operationOwnerResult?.type
		if (operationOwnerType instanceof GenericElement) {
			for (var i = 0; i < operationOwnerType.typeParameters.size &&
				i < operationOwnerResult.bindings.size; i++) { // T1, T2...
				val typeParameter = operationOwnerType.typeParameters.get(i)
				val binding = operationOwnerResult.bindings.get(i) // integer, boolean...
				typeConstraints.put(typeParameter, binding)
			}
			// add mapping from supertype's type parameters to this type's type parameters
			inferTypeParametersFromSuperTypes(operationOwnerType, typeConstraints)
			
		}
	}
	
	protected def void inferTypeParametersFromSuperTypes(Type operationOwnerType, ListMultimap<TypeParameter, InferenceResult> typeConstraints) {
		val superTypes = operationOwnerType.superTypes
		for (TypeSpecifier superType : superTypes) {
			for (var i = 0; i < superType.typeArguments.size; i++){
				val argument = superType.typeArguments.get(i)
				val param = (superType.type as GenericElement).typeParameters.get(i)
				typeConstraints.put(param, InferenceResult.from(argument.type))
			}
			inferTypeParametersFromSuperTypes(superType.type, typeConstraints)
		}
	}

	/**
	 * Asserts that two types are <i>roughly</i> compatible.<br>
	 * These are compatible:
	 * <ul>
	 * <li><code>ComplexType&lt;integer&gt;</code></li>
	 * <li> <code>ComplexType&lt;T&gt;</code></li>
	 * </ul>
	 * These are as well:
	 * <ul>
	 * <li><code>T</code></li>
	 * <li><code>integer</code></li>
	 * </ul>
	 * These are not:
	 * <ul>
	 * <li><code>ComplexType&lt;T&gt;</code></li>
	 * <li><code>integer</code></li>
	 * </ul>
	 */
	def protected assertArgumentAndParameterSoftCompatible(InferenceResult argumentResult, TypeSpecifier parameter,
		IValidationIssueAcceptor acceptor) {
		// I can't think of anything that's not compatible to a TypeParameter, so...
		if (parameter?.type instanceof TypeParameter) {
			return
		}
		val result1 = InferenceResult.from(argumentResult.type) // ignore bindings
		val result2 = InferenceResult.from(parameter.type)
		val thisIssueAcceptor = createIssueAcceptor();
		typeValidator.assertCompatible(result1, result2, null, [thisIssueAcceptor.accept(it); acceptor.accept(it)])
		// check for correct number of TypeParameters / Argument's type parameters
		if (thisIssueAcceptor.traces.isEmpty && parameter.typeArguments !== null &&
			parameter.typeArguments.size != argumentResult.bindings.size) {
			// build temporary binding list for error message
			val bindings = parameter.typeArguments.map [
				InferenceResult.from(type)
			]
			acceptor.error(
				String.format(INCOMPATIBLE_TYPES, argumentResult, InferenceResult.from(parameter.type, bindings)),
				NOT_COMPATIBLE_CODE)
		}
	}
		
	def void inferTypeParametersFromTargetType(InferenceResult operationTargetResult, Operation op, ListMultimap<TypeParameter, InferenceResult> typeConstraints, IValidationIssueAcceptor acceptor) {
		inferTypeParameterFromOperationArgument(op.typeSpecifier, operationTargetResult, typeConstraints, acceptor);
	}
		
	def protected ListBasedValidationIssueAcceptor createIssueAcceptor() {
		new IValidationIssueAcceptor.ListBasedValidationIssueAcceptor()
	}

	def protected error(IValidationIssueAcceptor acceptor, String msg, String issueCode) {
		acceptor.accept(new ValidationIssue(Severity.ERROR, msg, issueCode));
	}

	def protected error(IValidationIssueAcceptor acceptor, TypeSpecifier typeSpecifier, String issueCode) {
		acceptor.accept(
			new ValidationIssue(Severity.ERROR, String.format(INFER_TYPE_PARAMETER, typeSpecifier.type.name),
				issueCode));
	}

	def protected error(IValidationIssueAcceptor acceptor, TypeParameter typeParameter, String issueCode) {
		acceptor.accept(
			new ValidationIssue(Severity.ERROR, String.format(INFER_TYPE_PARAMETER, typeParameter.name),
				issueCode));
		}

	def protected warning(IValidationIssueAcceptor acceptor, String msg, String issueCode) {
		acceptor.accept(new ValidationIssue(Severity.WARNING, msg, issueCode))
	}

	def protected warning(IValidationIssueAcceptor acceptor, TypeParameter typeParameter, String issueCode) {
		acceptor.accept(
			new ValidationIssue(Severity.WARNING, String.format(INFER_TYPE_PARAMETER, typeParameter.name),
				issueCode));
	}
}
		