package org.grails.plugins.sanitizer

import grails.test.*

class MarkupSanitizerResultTests extends GrailsUnitTestCase {

	void testSimpleProperties(){

		def val = new MarkupSanitizerResult()
		assertFalse(val.isInvalid())

		val.dirtyString = "test"
		assertFalse(val.isInvalid())

		val.errorMessages.add("error1")
		assertTrue(val.isInvalid())

		val.errorMessages.add("error2")
		assertTrue(val.isInvalid())

		val.errorMessages.clear()
		assertFalse(val.isInvalid())
	}
}
