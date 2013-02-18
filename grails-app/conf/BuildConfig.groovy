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
		runtime('org.owasp.antisamy:antisamy:1.5.1')
	}

	plugins {
		build ':release:2.2.0', ':rest-client-builder:1.0.3', {
			export = false
		}
	}
}
