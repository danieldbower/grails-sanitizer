package org.grails.plugins.sanitizer

import org.springframework.core.io.FileSystemResource;
import grails.test.*

class MarkupSanitizerServiceIntegrationTests extends GrailsUnitTestCase {
    def markupSanitizerService
	
	protected void setUp() {
        super.setUp()
        
        markupSanitizerService.markupSanitizer = new AntiSamyMarkupSanitizer(new FileSystemResource("scripts/antisamyConfigs/antisamy-myspace-1.4.4.xml"))
    }

    protected void tearDown() {
        super.tearDown()
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
		def result = markupSanitizerService.validateMarkup(testString)
		
		if(result.isInvalidMarkup()){
			println(result.errorMessages)
		}
		
    	assertFalse(result.isInvalidMarkup())
    }
    
    void testSanitizeHtmlScriptTag(){
    	assertSanitized("<div>sanitize</div>", "<script></script><div>sanitize</div>")
    }
    
    void testSanitizeHtmlScriptTagWithErrors(){
    	MarkupSanitizerResult result = markupSanitizerService.sanitize("<script><script><div>sanitize</div>")
    	assertTrue(result.isInvalidMarkup())
    }
    
    void testValidateHtmlScriptTagWithErrors(){
    	MarkupValidatorResult result = markupSanitizerService.validateMarkup("<script><script><div>sanitize</div>")
    	assertTrue(result.isInvalidMarkup())
    }
}
