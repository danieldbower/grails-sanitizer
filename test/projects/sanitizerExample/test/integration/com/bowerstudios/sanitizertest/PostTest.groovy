package com.bowerstudios.sanitizertest

class PostTest extends GroovyTestCase {

	void testSuccessfulSave(){
		Post post = new Post(author:'Daniel', content:'<p>testing</p>')
		if(!post.save()){
			assert false
			return
		}
		assert true
	}
	
	void testFailedSave(){
		Post post = new Post(author:'Daniel', content:'<script></script><p>testing</p>')
		if(!post.save()){
			assert true
			return
		}
		assert false
	}
}
