package org.grails.plugins.sanitizer;

/**
 * Sanitize a string of Markup
 * @author daniel
 *
 */
public interface MarkupSanitizer extends MarkupValidator {
	/**
	 * Sanitize a string of Markup
	 *
	 * @param dirtyString
	 * @return
	 */
	MarkupSanitizerResult sanitize(String dirtyString);
}
