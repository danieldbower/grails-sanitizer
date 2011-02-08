package org.grails.plugins.sanitizer

import org.codehaus.groovy.grails.commons.ApplicationHolder;

/**
 * Codec that allows you to sanitize a String
 * <br />def clean = myText.encodeAsSanitizedMarkup()
 * <p>Encoder only available, there is no decoder to create an unsanitary string</p>
 * @author daniel
 *
 */
class SanitizedMarkupCodec {

	def markupSanitizerService
	
	/**
	 * Encode a string as sanitized Markup
	 */
	def encode = { dirtyMarkup ->
	
		if(!markupSanitizerService){
			markupSanitizerService = ApplicationHolder.application.mainContext.getBean("markupSanitizerService")
		}
	
		MarkupSanitizerResult result = markupSanitizerService.sanitize(dirtyMarkup)
		
		if(result.isInvalidMarkup()){
			// :TODO  throw exception here??? or just return empty string...
			return "" 
		}else{
			return result.cleanString
		}
	}
}
