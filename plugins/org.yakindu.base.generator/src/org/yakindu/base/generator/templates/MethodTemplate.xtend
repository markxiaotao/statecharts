package org.yakindu.base.generator.templates

import com.google.inject.Inject
import org.yakindu.base.generator.MethodGen
import org.yakindu.base.generator.templates.util.TypeSpecifierTemplate

class MethodTemplate extends ContentTemplate {
	@Inject extension TypeSpecifierTemplate
	
	def generate(MethodGen it) {'''
		«generateVisibility»«generateReturnType»«methodName»(«generateParameters») {
			«generateContent»
		}
		'''
	}
	
	def generateParameters(MethodGen it) {
		if(!parameters.nullOrEmpty) {
			return parameters?.map([generate]).join(', ')
		}
		return ""
	}
	
	def generateVisibility(MethodGen it) {
		if(visibility.nullOrEmpty) {
			""
		} else {
			'''«visibility» '''
		}
	}
	
	def generateReturnType(MethodGen it) {
		if(returnType != null) {
			generateTypeSpecifier(returnType) + " "
		} else {
			""
		}
	}
}