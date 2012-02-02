package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class StartDayTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/start-day.html";

	@Test
	public void testStartDay() {
		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		//判断是不是从星期一开始
		assertEquals("一", selenium.getText("css=div.om-cal-box div.om-whd > span:nth-child(1)"));
	}
}
