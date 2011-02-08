//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

/*
 * 
 * Start
 * 
 */

//create sanitizer configuration directory
//ant.mkdir(dir:"${basedir}/grails-app/conf/sanitizer")

//Which configuration would you like?
chooseSanitizingLevel()

println "\n\nCreating Sanitizer Plugin Config file at:  ${basedir}/grails-app/conf/SanitizerConfig.groovy"
ant.copy(file:"${pluginBasedir}/grails-app/conf/sanitizer/SanitizerConfig.groovy",
		 todir:"${basedir}/grails-app/conf")
/*
 * 
 * Done
 * 
 */




def chooseSanitizingLevel(){
	def descriptions = """
		1) strict -- antisamy-slashdot.xml
		Slashdot (http://www.slashdot.org/) is a techie news site that allows users to respond anonymously to news posts with very limited HTML markup. Now Slashdot is not only one of the coolest sites around, it's also one that's been subject to many different successful attacks. Even more unfortunate is the fact that most of the attacks led users to the infamous goatse.cx picture (please don't go look it up). The rules for Slashdot are fairly strict: users can only submit the following HTML tags and no CSS: <b>, <u>, <i>, <a>, <blockquote>.
		Accordingly, we've built a policy file that allows fairly similar functionality. All text-formatting tags that operate directly on the font, color or emphasis have been allowed.

		2) moderate -- antisamy-ebay.xml
		eBay (http://www.ebay.com/) is the most popular online auction site in the universe, as far as I can tell. It is a public site so anyone is allowed to post listings with rich HTML content. It's not surprising that given the attractiveness of eBay as a target that it has been subject to a few complex XSS attacks. Listings are allowed to contain much more rich content than, say, Slashdot- so it's attack surface is considerably larger. The following tags appear to be accepted by eBay (they don't publish rules): <a>,...

		3) loose -- antisamy-myspace.xml
		MySpace (http://www.myspace.com/) is arguably the most popular social networking site today. Users are allowed to submit pretty much all HTML and CSS they want - as long as it doesn't contain JavaScript. MySpace is currently using a word blacklist to validate users' HTML, which is why they were subject to the infamous Samy worm (http://namb.la/). The Samy worm, which used fragmentation attacks combined with a word that should have been blacklisted (eval) - was the inspiration for the project.

		4)TinyMCE -- antisamy-tinymce.xml
		TinyMCE is a popular javascript rich text editor used for input of html forms.  See the Antisamy website for more information on this rule file.
		
		5) do not write config, I will do it myself at web-app/WEB-INF/antisamy-policy.xml
		"""

	def message = "An Antisamy Configuration will be added to your project's web-app/WEB-INF directory.  Antisamy comes with several base policies for filtering, please choose the level of sanitization you would like to use:\n${descriptions}\n\nchoices: "
	def propCode = "my.code"
	def choices = "1,2,3,4,5"

	String choice = userChoiceIs(message, propCode, choices)
	String destfile = "${basedir}/web-app/WEB-INF/antisamy-policy.xml"
	String srcdir = "${sanitizerPluginDir}/grails-app/conf/sanitizer/"
	switch(choice){
	  case "2":
	    //moderate
	    println "selected moderate"
	    ant.copy(file: (srcdir + "antisamy-ebay-1.4.3.xml"), tofile:destfile, overwrite:true)
	    break

	  case "3":
	    //loose
	    println "selected loose"
	    ant.copy(file: (srcdir + "antisamy-myspace-1.4.3.xml"), tofile:destfile, overwrite:true)
	    break
		
	case "4":
	    //tinymce
	    println "selected tinymce"
	    ant.copy(file: (srcdir + "antisamy-myspace-1.4.3.xml"), tofile:destfile, overwrite:true)
	 break
  
	  case "5":
		//leave alone
		println "do nothing"  
	   break

	  default:
	    //strict
	    println "selected strict"
	    ant.copy(file: (srcdir + "antisamy-slashdot-1.4.3.xml"), tofile:destfile, overwrite:true)
	    break
	}	
	
}

String userChoiceIs(message, propCode, choices){
    ant.input( message: message, addproperty: propCode, validargs= choices)
    ant.antProject.properties[propCode]
}
