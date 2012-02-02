package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class DisabledDaysTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/disabled-days.html";

	@Test
	public void testDisabledDays() {
		selenium.open(PAGE_URL);

		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		//1~5号是可用的,6~7号是不可用的
		assertEquals(
				"1",
				selenium.getText("css=div.om-cal-box  div.om-dbd > a:not(.om-disabled):not(.om-null)"));
		assertEquals(
				"6",
				selenium.getText("css=div.om-cal-box  div.om-dbd > a:not(.om-disabled):not(.om-null) + a.om-disabled"));
		assertEquals(
				"8",
				selenium.getText("css=div.om-cal-box  div.om-dbd > a:not(.om-disabled):not(.om-null) + a.om-disabled + a + a"));
		
		//今天是可用的
		assertTrue(selenium.isElementPresent("css=div.om-cal-box  div.om-dbd > a.om-today:not(.om-disabled)"));
		
		//今天之后是不可用的
		assertFalse(selenium.isElementPresent("css=div.om-cal-box  div.om-dbd > a.om-today:not(.om-disabled) + a:not(.om-disabled):not(.om-null)"));
		
		
		
	}

}
