//
// This script is executed by Grails when the plugin is uninstalled from project.
// Use this script if you intend to do any additional clean-up on uninstall, but
// beware of messing up SVN directories!
//

/*
 * 
 * Start
 * 
 */

chooseConfigRetention()

/*
 * 
 * Done
 * 
 */




//Should we delete config file?
def chooseConfigRetention(){
	def message = "Delete web-app/WEB-INF/antisamy-policy.xml\n\nchoices: "
		def propCode = "my.code"
		def choices = "y,n"

		String choice = userChoiceIs(message, propCode, choices)
		switch(choice){
		  case "y":
		    //moderate
		    println "deleting web-app/WEB-INF/antisamy-policy.xml "
		    ant.delete(dir:"${basedir}/web-app/WEB-INF/antisamy-policy.xml")
		    break

		  default:
		    //strict
		    println "leaving web-app/WEB-INF/antisamy-policy.xml intact"
		    break
		}
}

String userChoiceIs(message, propCode, choices){
    ant.input( message: message, addproperty: propCode, validargs= choices)
    ant.antProject.properties[propCode]
}



