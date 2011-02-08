package org.grails.plugins.sanitizer

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

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
	
	void testTrustedSanitizerFixTag() {
		ConfigurationHolder.config?.sanitizer= [trustSanitizer: true]
		
		String my = "<p>my paragraph<p>"
		assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())
	}
	
	void testTrustedSanitizerStripScript() {
		ConfigurationHolder.config?.sanitizer= [trustSanitizer: true]
		
		String my = "<script></script><p>my paragraph</p>"
		assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())
	}
}
