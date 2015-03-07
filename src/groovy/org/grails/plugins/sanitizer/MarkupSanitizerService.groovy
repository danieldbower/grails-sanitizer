package org.grails.plugins.sanitizer

import org.apache.commons.logging.LogFactory
import org.owasp.validator.html.AntiSamy
import org.owasp.validator.html.CleanResults
import org.owasp.validator.html.Policy
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

/**
 * A service for sanitizing Html Text in a string.
 * @see http://www.owasp.org/index.php/AntiSamy
 * @author daniel
 */
class MarkupSanitizerService {

	/**
	 * Selects a policy file for antisamy
	 * 
	 * By default use the slashdot antisamy process, use custom path if defined,
	 * or antisamyconfigs/antisamy-policy.com if it exists
	 */
	MarkupSanitizerService(Resource policyFile){
		//first, set to default
		Resource selectedPolicyFile = new ClassPathResource("antisamyconfigs/antisamy-slashdot-1.4.4.xml")

		Resource customPolicy = new ClassPathResource("antisamyconfigs/antisamy-policy.xml")
		// if a custom policy exists use that
		if(customPolicy.exists()){
			selectedPolicyFile = customPolicy
		}

		//If there is a custom path use that one
		if(policyFile.exists()){
			selectedPolicyFile = policyFile
		}

		log.info('Initializing MarkupSanitizerService with ' + selectedPolicyFile.filename)
		policy = Policy.getInstance(selectedPolicyFile.getFile())
	}
	
	/**
	 * The policy file to use for sanitizing the markup text
	 */
	private Policy policy
	
	private static final log = LogFactory.getLog(this)

	/**
	 * Sanitize a string.
	 * <p>Inject the Markup Sanitizer Service into your controller with:</p>
	 * <pre>def markupSanitizerService</pre>
	 * <p>Then use it with:</p>
	 * <pre>MarkupSanitizerResult result = markupSanitizerService.sanitize(mydirtyString)
	 * if(!result.isInvalid()){
	 *   mydomainclass.property = result.cleanString
	 * }else{
	 *   //thing to do with result.errorMessages
	 * }</pre>
	 * @param dirtyString
	 * @return markupSanitizerResult
	 */
	MarkupSanitizerResult sanitize(String dirtyString){
		AntiSamy scanner = new AntiSamy()
		
		CleanResults cr = scanner.scan(dirtyString, policy)
		
		MarkupSanitizerResult result = new MarkupSanitizerResult(dirtyString: dirtyString)
		
		//any error messages?
		if(cr.getErrorMessages()){
			result.errorMessages.addAll(cr.getErrorMessages())
		}
		
		//the clean result
		if(cr.getCleanHTML()){
			result.cleanString = cr.getCleanHTML()
		}
		
		log.debug "Dirty: $dirtyString, Cleaned: ${result.cleanString}, Number Errors: ${cr.errorMessages?.size()}"

		return result
	}
}
