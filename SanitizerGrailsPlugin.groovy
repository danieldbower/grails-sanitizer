import org.codehaus.groovy.grails.validation.ApplicationContextAwareConstraintFactory
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.grails.plugins.sanitizer.MarkupConstraint

class SanitizerGrailsPlugin {
	def version = "0.9.0"
	def grailsVersion = "2.0.0 > *"

	def author = "Daniel Bower"
	def authorEmail = "daniel@bowerstudios.com"
	def title = "Grails Markup Sanitizer Plugin"
	def description = '''\
Plugin for Sanitizing Markup(HTML, XHTML, CSS) using OWASP AntiSamy.
Filters malicious content from User generated content (such as that entered through Rich Text boxes).

Features -
* Ruleset in web-app/WEB-INF/antisamy-policy.xml
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
  - method MarkupValidatorResult validateMarkup(String htmlString)
  - effectively a singleton, which means the ruleset only needs to be read once on startup

Please note the beta nature of the version number.  This plugin has not been extensively tested.  Please feel
free to send me any results of any testing you may do.

This module does not sanitize a string that does not contain valid markup.  If it does not contain
valid markup, it will simply return an empty string.
'''

	def documentation = "http://grails.org/plugin/sanitizer"

	def doWithApplicationContext = { applicationContext ->
		// Implement post initialization spring config (optional)
		def factory = new ApplicationContextAwareConstraintFactory(
			applicationContext, MarkupConstraint, ["markupSanitizerService"])
		ConstrainedProperty.registerNewConstraint(MarkupConstraint.MARKUP_CONSTRAINT, factory)
	}
}
