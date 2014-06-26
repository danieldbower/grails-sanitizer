package com.bowerstudios.sanitizertest

class Post {

	String author
	String content
	Date dateCreated
	
	static constraints = {
		author nullable: false
		content
	}
}
