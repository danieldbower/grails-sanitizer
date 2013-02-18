package org.grails.plugins.sanitizer

import grails.test.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SanitizedMarkupCodecIntegrationTests extends GroovyTestCase {

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
