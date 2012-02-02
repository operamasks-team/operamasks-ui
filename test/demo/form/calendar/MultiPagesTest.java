package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class MultiPagesTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/multi-pages.html";

	@Test
	public void testMultiPages() {
		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		//如果有3个calendar则正确。
		assertTrue(selenium
				.isElementPresent("css=div.om-cal-call > div.om-cal-box:eq(2)"));
	}

}
