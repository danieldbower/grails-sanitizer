package org.grails.plugins.sanitizer

import org.junit.Test

class MarkupSanitizerServiceIntegrationTests {

	def markupSanitizerService

	@Test
	void testServiceIsAlive() {
		assert(markupSanitizerService)
	}

	/**
	 * Simple helper method for tests
	 * @param expectation
	 * @param testString
	 */
	void assertSanitized(String expectation, String testString){
		assert(expectation == markupSanitizerService.sanitize(testString).cleanString)
	}

	/**
	 * Simple helper method for validate tests
	 * @param expectation
	 * @param testString
	 */
	void assertValidTrue(String testString){
		def result = markupSanitizerService.sanitize(testString)

		if(result.isInvalid()){
			println(result.errorMessages)
		}

		assert(!result.isInvalid())
	}

	@Test
	void testSanitizeSanity(){
		assertSanitized("sanitize", "sanitize")
	}

	@Test
	void testValidateSanity(){
		assertValidTrue("sanitize")
	}

	@Test
	void testSanitizeHtml(){
		assertSanitized("<div>sanitize</div>", "<div>sanitize</div>")
	}

	@Test
	void testValidateHtml(){
		assertValidTrue("<div>sanitize</div>")
	}

	@Test
	void testSanitizeHtmlScriptTag(){
		assertSanitized("<div>sanitize</div>", "<script></script><div>sanitize</div>")
	}

	@Test
	void testSanitizeHtmlScriptTagWithErrors(){
		MarkupSanitizerResult result = markupSanitizerService.sanitize("<script><script><div>sanitize</div>")
		assert(result.isInvalid())
	}

}
