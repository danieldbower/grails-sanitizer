package org.grails.plugins.sanitizer

import grails.test.*

class MarkupSanitizerResultTests extends GrailsUnitTestCase {

	void testSpecialConstructor() {
		def vResult = new MarkupValidatorResult()
		vResult.dirtyString = "vTest"
		vResult.errorMessages.add "vError1"

		MarkupSanitizerResult sResult = new MarkupSanitizerResult(vResult)
		assertEquals("vTest", sResult.dirtyString)
		assertEquals("vError1", sResult.errorMessages[0])
	}

	void testSimplePropertyTest() {
		def vResult = new MarkupSanitizerResult()
		MarkupValidatorResultTests.simplePropertyTests vResult
	}
}
