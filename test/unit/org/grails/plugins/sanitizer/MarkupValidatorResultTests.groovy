package org.grails.plugins.sanitizer

import grails.test.*

class MarkupValidatorResultTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIsInvalidMarkup() {
    	def val = new MarkupValidatorResult()
    	simplePropertyTests(val)
    }
    
    public static void simplePropertyTests(val){

    	assertFalse(val.isInvalidMarkup())
    	
    	val.dirtyString = "test"
    		
    	assertFalse(val.isInvalidMarkup())
    	
    	val.errorMessages.add("error1")
    	
    	assertTrue(val.isInvalidMarkup())
    	
    	val.errorMessages.add("error2")
    	
    	assertTrue(val.isInvalidMarkup())
    	
    	val.errorMessages.clear()
    	
    	assertFalse(val.isInvalidMarkup())	
    }
}
