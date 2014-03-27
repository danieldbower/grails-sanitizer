import grails.util.GrailsNameUtils


includeTargets << grailsScript('_GrailsBootstrap')

target(setup:'Install AntiSamy Policy'){
	
	depends(classpath, checkVersion, compile)
	
	String message = """
An Antisamy Configuration should be added to your project in the 
web-app/WEB-INF directory.  Antisamy comes with several base policies 
for filtering, please choose the level of sanitization you would like 
to use:

These files and descriptions come from the AntiSamy site.

1) strict -- antisamy-slashdot.xml - (Default)
Slashdot (http://www.slashdot.org/) is a techie news site that allows users to 
respond anonymously to news posts with very limited HTML markup.  Now Slashdot 
is not only one of the coolest sites around, it's also one that's been subject 
to many different successful attacks. Even more unfortunate is the fact that most 
of the attacks led users to the infamous goatse.cx picture (please don't go look 
it up). The rules for Slashdot are fairly strict: users can only submit the 
following HTML tags and no CSS: <b>, <u>, <i>, <a>, <blockquote>.
Accordingly, we've built a policy file that allows fairly similar functionality. 
All text-formatting tags that operate directly on the font, color or emphasis 
have been allowed.

2) moderate -- antisamy-ebay.xml
eBay (http://www.ebay.com/) is the most popular online auction site in the universe, 
as far as I can tell. It is a public site so anyone is allowed to post listings 
with rich HTML content. It's not surprising that given the attractiveness of 
eBay as a target that it has been subject to a few complex XSS attacks. Listings 
are allowed to contain much more rich content than, say, Slashdot- so it's attack 
surface is considerably larger. The following tags appear to be accepted by eBay 
(they don't publish rules): <a>,...

3) loose -- antisamy-myspace.xml
MySpace (http://www.myspace.com/) is arguably the most popular social networking 
site today. Users are allowed to submit pretty much all HTML and CSS they want - 
as long as it doesn't contain JavaScript. MySpace is currently using a word 
blacklist to validate users' HTML, which is why they were subject to the infamous 
Samy worm (http://namb.la/). The Samy worm, which used fragmentation attacks 
combined with a word that should have been blacklisted (eval) - was the inspiration 
for the project.

4) TinyMCE -- antisamy-tinymce.xml
TinyMCE is a popular javascript rich text editor used for input of html forms.  
See the Antisamy website for more information on this rule file.

Specify your choice with:
  grails antisamy 1
where 1 is the number of the policy above.
"""

	def choice = argsMap.params[0]
	if (!choice) {
		errorMessage message
		exit 1
	}
	
	String srcdir = "${sanitizerPluginDir}/scripts/antisamyConfigs/"
	String destFileName = "${basedir}/web-app/WEB-INF/antisamy-policy.xml"
	
	printMessage("Grabbing source antisamy configs from: $srcdir \n Installing to: $destFileName \n ")
	
	switch(choice){
	  case "2":
	    //moderate
	    printMessage "selected moderate"
	    ant.copy(file: (srcdir + "antisamy-ebay-1.4.4.xml"), tofile:destFileName, overwrite:true)
	  break

	  case "3":
	    //loose
	    printMessage "selected loose"
	    ant.copy(file: (srcdir + "antisamy-myspace-1.4.4.xml"), tofile:destFileName, overwrite:true)
	  break
		
	  case "4":
	    //tinymce
	    printMessage "selected tinymce"
	    ant.copy(file: (srcdir + "antisamy-myspace-1.4.4.xml"), tofile:destFileName, overwrite:true)
	  break

	  default:
	    //strict
	    printMessage "selected strict (default)"
	    ant.copy(file: (srcdir + "antisamy-slashdot-1.4.4.xml"), tofile:destFileName, overwrite:true)
	  break
	}	
}

void printMessage(String message){ 
	event('StatusUpdate', [message]) 
}

void errorMessage(String message){ 
	event('StatusError', [message]) 
}

setDefaultTarget setup