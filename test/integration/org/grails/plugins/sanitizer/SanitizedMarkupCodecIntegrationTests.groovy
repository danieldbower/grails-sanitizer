package org.grails.plugins.sanitizer

import grails.test.*

class SanitizedMarkupCodecIntegrationTests extends GrailsUnitTestCase {
    
	protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHappyPath() {
    	String my = "<p>my paragraph</p>"
    
        assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())

    }
}
