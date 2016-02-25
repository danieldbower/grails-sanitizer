grails.project.work.dir = 'target'

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    //checksums true // Whether to verify checksums on resolve
    //legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	inherits 'global'
	log 'warn'
        repositories {
            mavenLocal()
            //grailsCentral()
            //mavenCentral()
	    mavenRepo "https://repository.sonatype.org/content/repositories/central"
            mavenRepo(id:"avisoapp", url:"https://build.avisoapp.com/artifactory/libs-release")
	}

	dependencies {
		compile('org.owasp.antisamy:antisamy:1.5.3')
		runtime('net.sourceforge.nekohtml:nekohtml:1.9.16') {
			excludes "xml-apis"
		}
	}

	plugins {
		build ':release:3.1.1', ':rest-client-builder:2.1.1', {
			export = false
		}
	}
}
