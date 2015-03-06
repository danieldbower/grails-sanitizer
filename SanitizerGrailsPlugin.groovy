import org.codehaus.groovy.grails.validation.ApplicationContextAwareConstraintFactory
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.grails.plugins.sanitizer.MarkupConstraint
import org.grails.plugins.sanitizer.MarkupSanitizerService
import org.springframework.core.io.ClassPathResource

class SanitizerGrailsPlugin {
	def version = "0.9.1"
	def grailsVersion = "2.2.0 > *"

	def author = "Daniel Bower"
	def authorEmail = "daniel@bowerstudios.com"
	def title = "Grails Markup Sanitizer Plugin"
	def description = '''\
Plugin for Sanitizing Markup(HTML, XHTML, CSS) using OWASP AntiSamy.
Filters malicious content from User generated content (such as that entered through Rich Text boxes).

Features -
* Constraint "markup"
  - can be added to domain/command classes to validate that a string is valid and safe markup
  - important note:  The constraint is for validation only, it does not sanitize the string
* Encoding-only Codec "myText.encodeAsSanitizedMarkup()"
  - use the codec or the service to sanitize the string
  - (the codec uses the service, too)
* MarkupSanitizerService
  - use the codec or the service to sanitize the string
  - access in your controllers/services via
    	def markupSanitizerService
  - method MarkupSanitizerResult sanitize(String dirtyString)
  - effectively a singleton, which means the ruleset only needs to be read once on startup

This module does not sanitize a string that does not contain valid markup.  If it does not contain
valid markup, it will simply return an empty string.
'''

	def documentation = "http://grails.org/plugin/sanitizer"

	def doWithSpring = {
		if(application.config.sanitizer.config){
			policyFileResource(ClassPathResource, application.config.sanitizer.config)
			markupSanitizerService(MarkupSanitizerService, ref('policyFileResource'))
		}else{
			markupSanitizerService(MarkupSanitizerService)
		}
	}
	
	def doWithApplicationContext = { applicationContext ->
		def factory = new ApplicationContextAwareConstraintFactory(
			applicationContext, MarkupConstraint, ["markupSanitizerService"])
		ConstrainedProperty.registerNewConstraint(MarkupConstraint.MARKUP_CONSTRAINT, factory)
	}
}
