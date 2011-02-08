package org.grails.plugins.sanitizer;

/**
 * Check whether a string of Markup is valid and safe
 * @author daniel
 *
 */
public interface MarkupValidator {
	/**
	 * Validate a string of Markup
	 * 
	 * @param dirtyString
	 * @return
	 */
	MarkupValidatorResult validateMarkup(String dirtyString);
}
