package org.grails.plugins.sanitizer

import org.junit.Test
import org.springframework.core.io.FileSystemResource

class MarkupSanitizerServiceIntegrationTests extends GroovyTestCase {

	def markupSanitizerService

	protected void setUp() {
		super.setUp()
	}

	void testServiceIsAlive() {
		assertNotNull(markupSanitizerService)
	}

	/**
	 * Simple helper method for tests
	 * @param expectation
	 * @param testString
	 */
	void assertSanitized(String expectation, String testString){
		assertEquals(expectation, markupSanitizerService.sanitize(testString).cleanString)
	}

	/**
	 * Simple helper method for validate tests
	 * @param expectation
	 * @param testString
	 */
	void assertValidTrue(String testString){
		def result = markupSanitizerService.sanitize(testString)

		if(result.isInvalid()){
			println(result.errorMessages)
		}

		assertFalse(result.isInvalid())
	}

	void testSanitizeSanity(){
		assertSanitized("sanitize", "sanitize")
	}

	void testValidateSanity(){
		assertValidTrue("sanitize")
	}

	void testSanitizeHtml(){
		assertSanitized("<div>sanitize</div>", "<div>sanitize</div>")
	}

	void testValidateHtml(){
		assertValidTrue("<div>sanitize</div>")
	}

	void testSanitizeHtmlScriptTag(){
		assertSanitized("<div>sanitize</div>", "<script></script><div>sanitize</div>")
	}

	void testSanitizeHtmlScriptTagWithErrors(){
		MarkupSanitizerResult result = markupSanitizerService.sanitize("<script><script><div>sanitize</div>")
		assertTrue(result.isInvalid())
	}

	void testSanitizeNonHtmlWithReturns(){
		String contents = """I wonder if we can return some *bold* text.

And a carriage return.

   1. What
   2. about
   3. a
   4. list?

Thanks,
"""
		MarkupSanitizerResult result = markupSanitizerService.sanitize(contents)
		assertFalse(result.isInvalid())

		String cleanResult = """I wonder if we can return some *bold* text.\n\nAnd a carriage return.\n\n   1. What\n   2. about\n   3. a\n   4. list?\n\nThanks,\n\n"""
		new File('/tmp/original.txt').write(cleanResult)
		new File('/tmp/sanitized.txt').write(result.cleanString)

		assertEquals(cleanResult, result.cleanString)
	}

	void testMultiLineHtml(){
		String contents = '''<p>{studentFullName}</p>
<p>{studentId}</p>
<p>Dear {studentNickname},<br /> Thank you for registering for XXX XXX
  (section XX), an online collaborative course which begins on {begindate}.
  Instructor information and course content will be available in
  Blackboard LEARN the Friday before the course starts.</p>
<ul>
  <li>For textbook information go to: <a
      href="http://avisocoaching.com/bookstore"
    target="_blank">http://avisocoaching.com/bookstore</a>.  Under Books in
    the upper left corner, click on Textbooks &amp; Course Materials. In
    Select Your Program choose Online. Continue by selecting the term,
    department (course prefix or subject), course number, section.</li>
  <li>If you have questions about coursework or Blackboard
    LEARN availability and content, please contact your instructor.</li>
  <li>If you have technical difficulties with Blackboard LEARN, contact
    Support at <a href="mailto:support@avisocoaching.com" target="_blank">support@avisocoaching.com</a>.</li>
  <li>See the Online Student Handbook for information on how to register
    for or drop a course, library resources, financial aid, billing and
    payment, etc. Here is a link to the Handbook: <a
      href="https://avisocoaching.com/StudentHandbook.pdf" target="_blank">https://avisocoaching.com/StudentHandbook.pdf</a></li></ul>
<p>If you have any questions or concerns, please let me know!</p>
<p>
  <br />Thank you,</p>
<p>Daniel<br />
  <br />Daniel Bower</p>
<p>Aviso Developer<br /> Aviso Coaching | Columbus | 614.111.1111 | test@avisocoaching.com</p>
<p>http://avisocoaching.com</p>'''

		MarkupSanitizerResult result = markupSanitizerService.sanitize(contents)
		if(result.invalid){
			println result.errorMessages
		}
		assert !result.invalid

		assert contents == result.cleanString
	}


}
