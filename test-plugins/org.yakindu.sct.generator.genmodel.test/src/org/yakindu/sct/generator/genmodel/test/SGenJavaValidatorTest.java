/**
 * Copyright (c) 2012 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.genmodel.test;

import static org.junit.Assert.fail;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.DEPRECATED;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.DUPLICATE_FEATURE;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.DUPLICATE_PARAMETER;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.EMPTY_SGEN;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.MISSING_REQUIRED_FEATURE;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.MISSING_REQUIRED_PARAMETER;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.UNKNOWN_CONTENT_TYPE;
import static org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator.UNKOWN_GENERATOR;

import java.lang.reflect.Method;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.validation.Check;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yakindu.sct.generator.genmodel.test.util.AbstractSGenTest;
import org.yakindu.sct.generator.genmodel.test.util.SGenInjectorProvider;
import org.yakindu.sct.generator.genmodel.validation.SGenJavaValidator;
import org.yakindu.sct.model.sgen.FeatureConfiguration;
import org.yakindu.sct.model.sgen.FeatureParameter;
import org.yakindu.sct.model.sgen.FeatureType;
import org.yakindu.sct.model.sgen.GeneratorEntry;
import org.yakindu.sct.model.sgen.GeneratorModel;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author andreas muelder - Initial contribution and API
 *
 */
@RunWith(XtextRunner.class)
@InjectWith(SGenInjectorProvider.class)
public class SGenJavaValidatorTest extends AbstractSGenTest {
	
	@Test
	public void checkFeatureConfiguration() {
		// generator specific tests
		// - CSGenJavaValidatorTest
		// - ...
	}
	
	/**
	 * @see SGenJavaValidator#checkDomainCompatibility(GeneratorModel)
	 */
	@Test
	public void checkDomainCompatibility() {
		EObject model = parseExpression("GeneratorModel for yakindu::java { statechart UnknownDomainStatechart { }}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(
				"This generator can not be used for domain does.not.exist. Valid domains are [com.yakindu.domain.java, org.yakindu.domain.default]"));
	}
	
	/**
	 * @see SGenJavaValidator#checkContentType(org.yakindu.sct.model.sgen.GeneratorEntry)
	 */
	@Test
	public void checkContentType() {
		EObject model = parseExpression("GeneratorModel for yakindu::java { unkownType Example {}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(UNKNOWN_CONTENT_TYPE));
	}
	
	@Test
	public void checkInitialValue() {
		EObject model = parseExpression("GeneratorModel for yakindu::java { var x : boolean = 5 }",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate("Incompatible types boolean and integer."));
	}
	
	/**
	 * @see SGenJavaValidator#checkParameterValueType(org.yakindu.sct.model.sgen.FeatureParameterValue)
	 *
	 */
	@Test
	public void checkParameterValueType() {
		EObject model = parseExpression(
				"GeneratorModel for yakindu::java { statechart Example { feature Outlet { targetFolder = true }}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate("Incompatible types string and boolean."));
	}
	
	/**
	 * @see SGenJavaValidator#checkParameterValue(org.yakindu.sct.model.sgen.FeatureParameterValue)
	 */
	@Test
	public void checkParameterValue() {
		// Nothing to test
	}

	/**
	 * @see SGenJavaValidator#checkDuplicateElementRef(GeneratorEntry)
	 */
	@Test
	public void checkDuplicateElementRef() {
		// Tested by manual tests
	}
	
	/**
	 * @see SGenJavaValidator#checkGeneratorExists(GeneratorModel)
	 */
	@Test
	public void checkGeneratorExists() {
		EObject model = parseExpression("GeneratorModel for yakindu::unknown {}", GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(UNKOWN_GENERATOR));
	}
	
	/**
	 * @see SGenJavaValidator#checkDuplicateGeneratorEntryFeature(FeatureConfiguration)
	 */
	@Test
	public void checkDuplicateGeneratorEntryFeature() {
		EObject model = parseExpression(
				"GeneratorModel for yakindu::java { statechart Example { feature Outlet { } feature Outlet { }}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(DUPLICATE_FEATURE));
	}
	
	/**
	 * @see SGenJavaValidator#checkDuplicateFeatureParameter(org.yakindu.sct.model.sgen.FeatureParameterValue)
	 */
	@Test
	public void checkDuplicateFeatureParameter() {
		EObject model = parseExpression(
				"GeneratorModel for yakindu::java { statechart Example { feature Outlet { targetFolder = true  targetFolder = true }}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(DUPLICATE_PARAMETER));
	}
	
	/**
	 * @see SGenJavaValidator#checkRequiredFeatures(org.yakindu.sct.model.sgen.GeneratorEntry)
	 */
	@Test
	public void checkRequiredFeatures() {
		EObject model = parseExpression("GeneratorModel for yakindu::java { statechart Example {}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(MISSING_REQUIRED_FEATURE));
	}
	
	/**
	 * @see SGenJavaValidator#checkRequiredParameters(FeatureConfiguration)
	 */
	@Test
	public void checkRequiredParameters() {
		EObject model = parseExpression("GeneratorModel for yakindu::java { statechart Example { feature Outlet {}}}",
				GeneratorModel.class.getSimpleName());
		AssertableDiagnostics result = tester.validate(model);
		result.assertAny(new MsgPredicate(MISSING_REQUIRED_PARAMETER));
	}
	
	/**
	 * @see SGenJavaValidator#checkDeprecatedFeatures(GeneratorEntry)
	 */
	@Test
	public void checkDeprecatedFeatures() {
		EObject model = parseExpression(
				"GeneratorModel for yakindu::java { statechart Example { feature Outlet {targetFolder = \"src-gen\"  targetProject = \"TestProject\" }}}",
				GeneratorModel.class.getSimpleName());
		if (!(model instanceof GeneratorModel)) {
			fail("Model is of the wrong type");
		} else {
			GeneratorModel genModel = (GeneratorModel) model;
			FeatureType type = genModel.getEntries().get(0).getFeatures().get(0).getType();
			type.setDeprecated(true);
			AssertableDiagnostics result = tester.validate(genModel);
			result.assertAny(new MsgPredicate(String.format(DEPRECATED, type.getName())));
		}
	}
	
	/**
	 * @see SGenJavaValidator#checkDeprecatedParameters(GeneratorEntry)
	 */
	@Test
	public void checkDeprecatedParameters() {
		EObject model = parseExpression(
				"GeneratorModel for yakindu::java { statechart Example { feature Outlet {targetFolder = \"src-gen\"  targetProject = \"TestProject\" }}}",
				GeneratorModel.class.getSimpleName());
		if (!(model instanceof GeneratorModel)) {
			fail("Model is of the wrong type");
		} else {
			GeneratorModel genModel = (GeneratorModel) model;
			FeatureParameter featureParameter = genModel.getEntries().get(0).getFeatures().get(0).getType().getParameters().get(0);
			featureParameter.setDeprecated(true);
			AssertableDiagnostics result = tester.validate(genModel);
			result.assertAny(new MsgPredicate(String.format(DEPRECATED, featureParameter.getName())));
		}
	}
	
	/**
	 * @see SGenJavaValidator#checkDeprecatedParameters(GeneratorEntry)
	 */
	@Test
	public void checkEntriesExist() {
		EObject model = parseExpression("GeneratorModel for yakindu::java {}", GeneratorModel.class.getSimpleName());
		if (!(model instanceof GeneratorModel)) {
			fail("Model is of the wrong type");
		} else {
			GeneratorModel genModel = (GeneratorModel) model;
			AssertableDiagnostics result = tester.validate(genModel);
			result.assertAny(new MsgPredicate(EMPTY_SGEN));
		}
	}
	
	/**
	 * checks that each @Check method of {@link STextJavaValidator} has a @Test
	 * method in this class with the same name
	 */
	@Test
	public void testAllChecksHaveTests() throws Exception {
		Iterable<Method> methods = Lists.newArrayList(SGenJavaValidator.class.getDeclaredMethods());
		methods = Iterables.filter(methods, new Predicate<Method>() {
			@Override
			public boolean apply(Method input) {
				return input.getAnnotation(Check.class) != null;
			}
		});
		for (Method checkMethod : methods) {
			try {
				getClass().getMethod(checkMethod.getName());
			} catch (NoSuchMethodException ex) {
				fail("Missing @Test Annotation for method " + checkMethod.getName());
			}
		}
	}
	
	public static final class MsgPredicate implements AssertableDiagnostics.DiagnosticPredicate {
		
		private final String msg;
		
		public MsgPredicate(String msg) {
			this.msg = msg;
		}
		
		@Override
		public boolean apply(Diagnostic input) {
			return input.getMessage().contains(msg);
		}
		
	}
}
