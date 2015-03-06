grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
		mavenRepo "https://repository.sonatype.org/content/repositories/central"
	}

	dependencies {
		compile('org.owasp.antisamy:antisamy:1.5.3')
		runtime('net.sourceforge.nekohtml:nekohtml:1.9.16') {
			excludes "xml-apis"
		}
	}

	plugins {
		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
