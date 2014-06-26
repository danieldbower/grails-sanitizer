package org.grails.plugins.sanitizer

import org.apache.commons.logging.LogFactory
import org.owasp.validator.html.AntiSamy
import org.owasp.validator.html.CleanResults
import org.owasp.validator.html.Policy
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

/**
 * A service for sanitizing Html Text in a string.
 * @see http://www.owasp.org/index.php/AntiSamy
 * @author daniel
 */
class MarkupSanitizerService {

	/**
	 * Provide a policy file for antisamy
	 * @param policyFile
	 * 
	 * By default use the antisamy policy in WEB-INF
	 */
	MarkupSanitizerService(Resource policyFile){
		log.info('Initializing MarkupSanitizerService with ' + policyFile.filename)
		policy = Policy.getInstance(policyFile.getFile())
	}
	
	MarkupSanitizerService(){
		Resource policyFile = new FileSystemResource("antisamyconfigs/antisamy-slashdot-1.4.4.xml")
		
		log.info('Initializing MarkupSanitizerService with ' + policyFile.filename)
		policy = Policy.getInstance(policyFile.getFile())
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
		
		log.debug "Dirty: $dirtyString, Cleaned: ${result.cleanString}, Number Erros: ${cr.errorMessages?.size()}"

		return result
	}
}
