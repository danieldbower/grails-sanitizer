package org.grails.plugins.sanitizer

import grails.test.*

import org.springframework.core.io.FileSystemResource

class AntiSamyMarkupSanitizerTests extends GrailsUnitTestCase {

	private AntiSamyMarkupSanitizer sanitizer

	protected void setUp() {
		super.setUp()

		sanitizer = new AntiSamyMarkupSanitizer(
			new FileSystemResource("scripts/antisamyConfigs/antisamy-myspace-1.4.4.xml"))
	}

	/**
	 * Simple helper method for sanitize tests
	 * @param expectation
	 * @param testString
	 */
	void assertSanitized(String expectation, String testString){
		assertEquals(expectation, sanitizer.sanitize(testString).cleanString)
	}

	/**
	 * Simple helper method for validate tests
	 * @param expectation
	 * @param testString
	 */
	void assertValidTrue(String testString){
		assertFalse(sanitizer.validateMarkup(testString).isInvalidMarkup())
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
		MarkupSanitizerResult result = sanitizer.sanitize("<script><script><div>sanitize</div>")
		assertTrue(result.isInvalidMarkup())
	}

	void testValidateHtmlScriptTagWithErrors(){
		MarkupValidatorResult result = sanitizer.validateMarkup("<script><script><div>sanitize</div>")
		assertTrue(result.isInvalidMarkup())
	}
}
