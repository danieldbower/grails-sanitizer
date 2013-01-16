import org.codehaus.groovy.grails.validation.ApplicationContextAwareConstraintFactory;
import org.codehaus.groovy.grails.validation.ConstrainedProperty;
import org.grails.plugins.sanitizer.*;

class SanitizerGrailsPlugin {
    // the plugin version
    def version = "0.8.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Daniel Bower"
    def authorEmail = "daniel@bowerstudios.com"
    def title = "Grails Markup Sanitizer Plugin"
    def description = '''\\
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

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/sanitizer"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    	// Implement post initialization spring config (optional)    
    	def factory = new ApplicationContextAwareConstraintFactory(
	    		applicationContext, MarkupConstraint.class, ["markupSanitizerService"])
	    ConstrainedProperty.registerNewConstraint(MarkupConstraint.MARKUP_CONSTRAINT, factory);
    }

    def onChange = { event ->
        // Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
        
        // TODO need to reload the antisamyservice only...
    }

    def onConfigChange = { event ->
        // Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
