package org.grails.plugins.sanitizer;

/**
 * results of the markup cleaning process 
 * @author daniel
 *
 */
public class MarkupSanitizerResult extends MarkupValidatorResult {
	
	public MarkupSanitizerResult(){}
	
	/**
	 * MarkupSanitizerResult extends MarkupValidatorResult, this constructor enables you to create a sanitizer result object from a validator result object
	 * @param validator
	 */
	public MarkupSanitizerResult(MarkupValidatorResult validator){
		setDirtyString(validator.getDirtyString());
		getErrorMessages().addAll(validator.getErrorMessages());
	}
	
	private String cleanString = "";

	/**
	 * The cleaned/sanitized version of the dirtyString
	 */
	public String getCleanString() {
		return cleanString;
	}

	/**
	 * The cleaned/sanitized version of the dirtyString
	 */
	public void setCleanString(String cleanString) {
		this.cleanString = cleanString;
	}
}
