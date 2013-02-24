package org.grails.plugins.sanitizer

import org.owasp.validator.html.AntiSamy
import org.owasp.validator.html.CleanResults
import org.owasp.validator.html.Policy
import org.springframework.core.io.Resource

/**
 * Clean a String of markup using OWASP Antisamy
 * @author daniel
 *
 */
class AntiSamyMarkupSanitizer implements MarkupSanitizer{

	/**
	 * The policy file to use for sanitizing the markup text
	 */
	private Policy policy

	/**
	 * Provide a policy file for antisamy
	 * @param policyFile
	 */
	AntiSamyMarkupSanitizer(Resource policyFile){
		setPolicyFromFile(policyFile)
	}

	private void setPolicyFromFile(Resource policyFile){
		policy = Policy.getInstance(policyFile.getFile())
	}

	private CleanResults runAntisamy(String dirtyString){
		AntiSamy scanner = new AntiSamy()
		return scanner.scan(dirtyString, policy)
	}

	/**
	 * Validate a string of markup
	 */
	MarkupValidatorResult validateMarkup(String dirtyString){
		CleanResults cr = runAntisamy(dirtyString)
		return validateMarkupWithCleanResults(cr, dirtyString)
	}

	private MarkupValidatorResult validateMarkupWithCleanResults(CleanResults cr, String dirtyString){
		MarkupValidatorResult result = new MarkupValidatorResult(dirtyString: dirtyString)

		//any error messages?
		if(cr.getErrorMessages() && cr.getErrorMessages().size()>0){
			result.errorMessages.addAll(cr.getErrorMessages())
		}

		return result
	}

	/**
	 * Sanitize a string of markup.
	 *
	 * @param dirtyString
	 */
	MarkupSanitizerResult sanitize(String dirtyString){
		CleanResults cr = runAntisamy(dirtyString)

		//Create the default result to return
		MarkupSanitizerResult result = new MarkupSanitizerResult(validateMarkupWithCleanResults(cr, dirtyString))

		//the clean result
		if(cr.getCleanHTML()){
			result.cleanString = cr.getCleanHTML()
		}
		return result
	}
}
