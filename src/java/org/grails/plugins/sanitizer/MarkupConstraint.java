package org.grails.plugins.sanitizer;

import org.codehaus.groovy.grails.validation.AbstractConstraint;
import org.springframework.validation.Errors;

/**
 * This constraint will trigger an error only if the html that is input is invalid.
 * It will return an error message with the problem that it found.
 * 
 * <p>Be sure to still use the sanitizer service or codec in order to actually remove content
 * not on the whitelist.
 * </p>
 */
public class MarkupConstraint extends AbstractConstraint {
	/**
	 * Injected service for sanitizing the Markup
	 */
	MarkupSanitizerService markupSanitizerService;
 
	/**
	 * Name of the Constraint for use in Domain classes
	 */
	public static final String MARKUP_CONSTRAINT = "markup";
	 	
	/**
	 * Checks the input to verify that we can attempt to validate it.
	 */
	public boolean supports(Class type) {
	        return type != null && String.class.isAssignableFrom(type);
    }
	
	public void processValidate(Object target, Object propertyValue, Errors errors){
		MarkupValidatorResult result = markupSanitizerService.validateMarkup((String) propertyValue);
    	
		if (result.isInvalidMarkup()) {
            
			String errorMesg = (null!=result.getErrorMessages()) ? result.getErrorMessages().toString() : "Invalid Markup entered";
			
			Object[] args = {constraintPropertyName, constraintOwningClass, propertyValue};
            
            rejectValueWithDefaultMessage(target, errors, errorMesg, new String[0], args);
        }
	}
	
	/**
	 * Name of the constraint
	 */
    public String getName() {
        return MARKUP_CONSTRAINT;
    }


}
