package org.grails.plugins.sanitizer;

import java.io.File;

import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.springframework.core.io.FileSystemResource;

/**
 * A service for sanitizing Html Text in a string.  
 * @see http://www.owasp.org/index.php/AntiSamy
 * @author daniel
 *
 */
class MarkupSanitizerService{
	
	protected MarkupSanitizer markupSanitizer

	/**
	 * By default use the antisamy policy in WEB-INF
	 */
	MarkupSanitizer getSanitizer(){
		if(!markupSanitizer){
			markupSanitizer = new AntiSamyMarkupSanitizer(
				ApplicationHolder.application.mainContext.getResource( File.separator + "WEB-INF" + File.separator + "antisamy-policy.xml"))
		}
		return markupSanitizer
	}
	
	/**
	 * Sanitize a string.
	 * <p>Inject the Markup Sanitizer Service into your controller with:</p>
	 * <pre>def markupSanitizerService</pre>
	 * <p>Then use it with:</p>
	 * <pre>MarkupSanitizerResult result = markupSanitizerService.sanitize(mydirtyString)
	 * if(!result.isInvalidMarkup()){
	 *   mydomainclass.property = result.cleanString
	 * }else{
	 *   //thing to do with result.errorMessages
	 * }</pre>
	 * @param dirtyString
	 * @return markupSanitizerResult
	 */
    MarkupSanitizerResult sanitize(String dirtyString){
        return getSanitizer().sanitize (dirtyString)
    }
    
    
    /**
	 * validate a string.
	 * <p>Inject the Markup Sanitizer Service into your controller with:</p>
	 * <pre>def markupSanitizerService</pre>
	 * <p>Then use it with:</p>
	 * <pre>MarkupValidatorResult result = markupSanitizerService.validateMarkup(mydirtyString)
	 * if(!result.isInvalidMarkup()){
	 *   //thing to do if valid
	 * }else{
	 *   //thing to do with result.errorMessages
	 * }</pre>
	 * @param dirtyString
	 * @return markupValidatorResult
	 */
    MarkupValidatorResult validateMarkup(String htmlString){
    	return getSanitizer().validateMarkup(htmlString)
    }

}
