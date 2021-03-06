/**
 * Copyright (c) 2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.java;

import static org.yakindu.sct.model.sexec.transformation.IModelSequencer.ADD_TRACES;

import org.yakindu.sct.generator.core.IExecutionFlowGenerator;
import org.yakindu.sct.generator.core.IGeneratorModule;
import org.yakindu.sct.generator.core.extensions.AnnotationExtensions;
import org.yakindu.sct.generator.core.submodules.lifecycle.MicroStepCode;
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess;
import org.yakindu.sct.generator.java.eventdriven.JavaEventDrivenIncludeProvider;
import org.yakindu.sct.generator.java.features.IJavaFeatureConstants;
import org.yakindu.sct.generator.java.submodules.EventCode;
import org.yakindu.sct.generator.java.submodules.FieldDeclarationGenerator;
import org.yakindu.sct.generator.java.submodules.RunCycleMethodCode;
import org.yakindu.sct.generator.java.submodules.TimingFunctions;
import org.yakindu.sct.generator.java.submodules.eventdriven.EventDrivenEventCode;
import org.yakindu.sct.generator.java.submodules.eventdriven.EventDrivenFieldDeclarationGenerator;
import org.yakindu.sct.generator.java.submodules.eventdriven.EventDrivenTimingFunctions;
import org.yakindu.sct.generator.java.types.JavaTypeSystemAccess;
import org.yakindu.sct.generator.java.types.OldJavaTypeSystemAccess;
import org.yakindu.sct.model.sexec.naming.INamingService;
import org.yakindu.sct.model.sexec.transformation.BehaviorMapping;
import org.yakindu.sct.model.sexec.transformation.IModelSequencer;
import org.yakindu.sct.model.sexec.transformation.config.IFlowConfiguration;
import org.yakindu.sct.model.sexec.transformation.ng.ModelSequencer;
import org.yakindu.sct.model.sgen.FeatureParameterValue;
import org.yakindu.sct.model.sgen.GeneratorEntry;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 *
 * @author andreas muelder - Initial contribution and API
 * @author terfloth - extensions for event driven execution
 *
 */
public class JavaGeneratorModule implements IGeneratorModule {

	protected Multibinder<JavaIncludeProvider> includeProviderBinder;

	@Override
	public void configure(GeneratorEntry entry, Binder binder) {
		includeProviderBinder = Multibinder.newSetBinder(binder, JavaIncludeProvider.class);
		includeProviderBinder.addBinding().to(JavaStatechartIncludeProvider.class);
		configureGeneratorRoot(entry, binder);
		configureForExecutionStyle(entry, binder);
		configureServices(entry, binder);
		bindTracingProperty(entry, binder);
	}

	public void configureGeneratorRoot(GeneratorEntry entry, Binder binder) {
		binder.bind(IFlowConfiguration.class).to(JavaFlowConfiguration.class);
		binder.bind(MicroStepCode.class).to(RunCycleMethodCode.class);
		binder.bind(org.yakindu.sct.generator.core.submodules.lifecycle.EventCode.class).to(org.yakindu.sct.generator.java.submodules.EventCode.class);
		binder.bind(org.yakindu.sct.generator.core.submodules.lifecycle.TraceCode.class).to(org.yakindu.sct.generator.java.submodules.TraceCode.class);
		binder.bind(org.yakindu.sct.generator.core.submodules.lifecycle.InitializedCheckCode.class).to(org.yakindu.sct.generator.java.submodules.InitializedCheckCode.class);
		
		binder.bind(IModelSequencer.class).to(ModelSequencer.class);
		binder.bind(BehaviorMapping.class).to(org.yakindu.sct.model.sexec.transformation.ng.BehaviorMapping.class);
		binder.bind(IExecutionFlowGenerator.class).to(JavaGenerator.class);
		binder.bind(GeneratorEntry.class).toInstance(entry);
	}

	public void configureForExecutionStyle(GeneratorEntry entry, Binder binder) {
		if ((new AnnotationExtensions()).isEventDriven(entry)) {
			includeProviderBinder.addBinding().to(JavaEventDrivenIncludeProvider.class);
			binder.bind(TimingFunctions.class).to(EventDrivenTimingFunctions.class);
			binder.bind(EventCode.class).to(EventDrivenEventCode.class);
			binder.bind(FieldDeclarationGenerator.class).to(EventDrivenFieldDeclarationGenerator.class);
		}
	}

	public void configureServices(GeneratorEntry entry, Binder binder) {

		final GenmodelEntries entries = new GenmodelEntries();
		if (entries.useJavaInt(entry)) {
			binder.bind(ICodegenTypeSystemAccess.class).to(OldJavaTypeSystemAccess.class);
		} else {
			binder.bind(ICodegenTypeSystemAccess.class).to(JavaTypeSystemAccess.class);
		}
		binder.bind(INamingService.class).to(JavaNamingService.class).in(Scopes.SINGLETON);
	}

	protected void bindTracingProperty(GeneratorEntry entry, Binder binder) {
		FeatureParameterValue traceEnterFeature = entry.getFeatureParameterValue(IJavaFeatureConstants.FEATURE_TRACING,
				IJavaFeatureConstants.PARAMETER_TRACING_ENTER_STATE);
		FeatureParameterValue traceExitFeature = entry.getFeatureParameterValue(IJavaFeatureConstants.FEATURE_TRACING,
				IJavaFeatureConstants.PARAMETER_TRACING_EXIT_STATE);
		boolean traceEnter = traceEnterFeature != null ? traceEnterFeature.getBooleanValue() : false;
		boolean traceExit = traceExitFeature != null ? traceEnterFeature.getBooleanValue() : false;
		binder.bind(Boolean.class).annotatedWith(Names.named(ADD_TRACES)).toInstance(traceEnter || traceExit);
	}
}
