package org.grails.plugins.sanitizer

import grails.test.*

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.Test

class SanitizedMarkupCodecIntegrationTests {

	GrailsApplication grailsApplication

	@Test
	void testHappyPath() {
		String my = "<p>my paragraph</p>"
		assert("<p>my paragraph</p>" == my.encodeAsSanitizedMarkup())
	}

	@Test
	void testFailure() {
		grailsApplication.config?.sanitizer= [trustSanitizer: false]

		String my = "<script></script><p>my paragraph</p>"
		assert("" == my.encodeAsSanitizedMarkup())
	}

	@Test
	void testTrustedSanitizerFixTag() {
		//Holders.config?.sanitizer= [trustSanitizer: true]
		grailsApplication.config?.sanitizer= [trustSanitizer: true]

		String my = "<p>my paragraph<p>"
		assert("<p>my paragraph</p>" == my.encodeAsSanitizedMarkup())
	}

	@Test
	void testTrustedSanitizerStripScript() {
		//Holders.config?.sanitizer= [trustSanitizer: true]
		grailsApplication.config?.sanitizer= [trustSanitizer: true]

		String my = "<script></script><p>my paragraph</p>"
		assert("<p>my paragraph</p>" == my.encodeAsSanitizedMarkup())
	}
}
