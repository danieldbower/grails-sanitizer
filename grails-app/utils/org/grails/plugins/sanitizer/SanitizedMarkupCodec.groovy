package org.grails.plugins.sanitizer

import grails.util.Holders

/**
 * Codec that allows you to sanitize a String
 * <br />def clean = myText.encodeAsSanitizedMarkup()
 * <p>Encoder only available, there is no decoder to create an unsanitary string</p>
 * @author daniel
 *
 */
class SanitizedMarkupCodec {

	MarkupSanitizerService markupSanitizerService

	/**
	 * Encode a string as sanitized Markup
	 */
	def encode = { dirtyMarkup ->

		if(!markupSanitizerService){
			markupSanitizerService = Holders.applicationContext.getBean("markupSanitizerService")
		}

		MarkupSanitizerResult result = markupSanitizerService.sanitize(dirtyMarkup)

		if(result.isInvalid() && !Holders.config?.sanitizer?.trustSanitizer){
			// just return empty string...
			return ""
		}else{
			return result.cleanString
		}
	}
	
	static decode = {
		throw new UnsupportedOperationException("Cannot make a dirty string.")
	}
}
