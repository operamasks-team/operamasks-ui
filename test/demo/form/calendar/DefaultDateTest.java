package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class DefaultDateTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/default-date.html";

	@Test
	public void testDefaultDate() {
		selenium.open(PAGE_URL);

		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		// 默认日期是2010年8月15日
		
		assertEquals(
				"2010年8月",
				selenium.getText("css=div.om-cal-box > div.om-cal-hd > a.om-title"));
		assertEquals(
				"15",
				selenium.getText("css=div.om-cal-box > div.om-cal-bd > div.om-dbd.om-clearfix > a.om-selected"));

	}

}
