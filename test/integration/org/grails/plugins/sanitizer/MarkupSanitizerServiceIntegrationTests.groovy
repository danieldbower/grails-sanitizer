package org.grails.plugins.sanitizer

import org.springframework.core.io.FileSystemResource

class MarkupSanitizerServiceIntegrationTests extends GroovyTestCase {

	def markupSanitizerService

	protected void setUp() {
		super.setUp()
	}

	void testServiceIsAlive() {
		assertNotNull(markupSanitizerService)
	}

	/**
	 * Simple helper method for tests
	 * @param expectation
	 * @param testString
	 */
	void assertSanitized(String expectation, String testString){
		assertEquals(expectation, markupSanitizerService.sanitize(testString).cleanString)
	}

	/**
	 * Simple helper method for validate tests
	 * @param expectation
	 * @param testString
	 */
	void assertValidTrue(String testString){
		def result = markupSanitizerService.validate(testString)

		if(result.isInvalid()){
			println(result.errorMessages)
		}

		assertFalse(result.isInvalid())
	}

	void testSanitizeSanity(){
		assertSanitized("sanitize", "sanitize")
	}

	void testValidateSanity(){
		assertValidTrue("sanitize")
	}

	void testSanitizeHtml(){
		assertSanitized("<div>sanitize</div>", "<div>sanitize</div>")
	}

	void testValidateHtml(){
		assertValidTrue("<div>sanitize</div>")
	}

	void testSanitizeHtmlScriptTag(){
		assertSanitized("<div>sanitize</div>", "<script></script><div>sanitize</div>")
	}

	void testSanitizeHtmlScriptTagWithErrors(){
		MarkupSanitizerResult result = markupSanitizerService.sanitize("<script><script><div>sanitize</div>")
		assertTrue(result.isInvalid())
	}

}
