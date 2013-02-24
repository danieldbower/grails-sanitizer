package org.grails.plugins.sanitizer

import grails.test.*

class SanitizedMarkupCodecTests extends GrailsUnitTestCase {

	def service
	def codec
	def result

	protected void setUp() {
		super.setUp()

		codec = new SanitizedMarkupCodec()
		result = new MarkupSanitizerResult()

		service = [sanitize: { result }]
		codec.markupSanitizerService = service

		result.dirtyString = "test"
		result.cleanString = "test"
	}

	void testValid() {
		assertEquals "test", codec.encode("test")
	}

	void testInValid() {
		result.errorMessages.add "error1"

		assertEquals "", codec.encode("test")
	}
}
