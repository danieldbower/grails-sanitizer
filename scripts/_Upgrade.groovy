//
// This script is executed by Grails during application upgrade ('grails upgrade'
// command). This script is a Gant script so you can use all special variables
// provided by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

println "Sorry folks, I haven't done enough research on upgrading a plugin.  I recommend you remove, and then install the plugin.  The only file to worry about is the antisamy config in WEB-INF if you have customized it."
