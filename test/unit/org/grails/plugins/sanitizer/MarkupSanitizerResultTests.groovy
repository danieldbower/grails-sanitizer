package org.grails.plugins.sanitizer

import grails.test.*

class MarkupSanitizerResultTests {

	@Test
	void testSimpleProperties(){

		def val = new MarkupSanitizerResult()
		assert(!val.isInvalid())

		val.dirtyString = "test"
		assert(!val.isInvalid())

		val.errorMessages.add("error1")
		assert(val.isInvalid())

		val.errorMessages.add("error2")
		assert(val.isInvalid())

		val.errorMessages.clear()
		assert(!val.isInvalid())
	}
}
