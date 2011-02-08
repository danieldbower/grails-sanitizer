package org.grails.plugins.sanitizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of Markup Validation
 * @author daniel
 *
 */
public class MarkupValidatorResult {
	
	private String dirtyString = "";
	
	private List<String> errorMessages = new ArrayList<String>();
	                   
	/**
	 * Is the Markup invalid?
	 * @return
	 */
	public boolean isInvalidMarkup(){
		return (errorMessages!=null && errorMessages.size()>0);
	}
	
	/**
	 * The String that was submitted for sanitizing
	 */
	public String getDirtyString() {
		return dirtyString;
	}

	/**
	 * The String that was submitted for sanitizing
	 */
	public void setDirtyString(String dirtyString) {
		this.dirtyString = dirtyString;
	}

	/**
	 * If any error messages were generated, they are here
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
