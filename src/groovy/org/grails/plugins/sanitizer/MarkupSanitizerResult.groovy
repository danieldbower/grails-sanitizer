package org.grails.plugins.sanitizer;

/**
 * results of the markup cleaning process
 * @author daniel
 *
 */
public class MarkupSanitizerResult {

	/**
	 * The String that was submitted for sanitizing
	 */
	String dirtyString = ""
	
	/**
	 * If any error messages were generated, they are here
	 */
	List<String> errorMessages = []
	
	/**
	 * The cleaned/sanitized version of the dirtyString
	 */
	String cleanString = ""

	/**
	 * Is the Markup invalid?
	 * @return
	 */
	boolean isInvalid(){
		return !(errorMessages?.isEmpty());
	}
}
