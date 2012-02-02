package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class DateLimitTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/date-limit.html";

	@Test
	public void testDateLimit() {
		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);

		// 年份为2011年8月
		assertEquals(
				"2011年8月",
				selenium.getText("css=div.om-cal-box > div.om-cal-hd > a.om-title"));

		// 日期范围为5~15
		assertEquals(
				"5",
				selenium.getText("css=div.om-cal-box  div.om-dbd > a:not(.om-disabled):not(.om-null)"));
		assertEquals(
				"16",
				selenium.getText("css=div.om-cal-box  div.om-dbd > a:not(.om-disabled):not(.om-null) + a.om-disabled"));
	}
}
