<%@ page import="com.bowerstudios.sanitizertest.Post" %>



<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'author', 'error')} ">
	<label for="author">
		<g:message code="post.author.label" default="Author" />
		
	</label>
	<g:textField name="author" value="${postInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'content', 'error')} ">
	<label for="content">
		<g:message code="post.content.label" default="Content" />
		
	</label>
	<g:textField name="content" value="${postInstance?.content}"/>
</div>

