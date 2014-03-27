package org.grails.plugins.sanitizer

import grails.test.*

import org.codehaus.groovy.grails.commons.GrailsApplication

class SanitizedMarkupCodecIntegrationTests extends GroovyTestCase {

	GrailsApplication grailsApplication
	
	void testHappyPath() {
		String my = "<p>my paragraph</p>"

		assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())
	}

	void testTrustedSanitizerFixTag() {
		grailsApplication.config?.sanitizer= [trustSanitizer: true]

		String my = "<p>my paragraph<p>"
		assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())
	}

	void testTrustedSanitizerStripScript() {
		grailsApplication.config?.sanitizer= [trustSanitizer: true]

		String my = "<script></script><p>my paragraph</p>"
		assertEquals("<p>my paragraph</p>", my.encodeAsSanitizedMarkup())
	}
}
