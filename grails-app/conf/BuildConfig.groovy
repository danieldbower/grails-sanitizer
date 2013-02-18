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
		runtime('org.owasp.antisamy:antisamy:1.4.5') {
			excludes "xml-apis"
		}
		runtime('net.sourceforge.nekohtml:nekohtml:1.9.15') {
			excludes "xml-apis"
		}
	}

	plugins {
		build ':release:2.2.0', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
