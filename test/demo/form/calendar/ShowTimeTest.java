package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class ShowTimeTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/show-time.html";

	@Test
	public void testShowTime() {

		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		//显示时间div
		assertTrue(selenium
				.isElementPresent("css=div.om-cal-time > span.h + span.m + span.s"));
		//不显示选择时间的div
		assertTrue(selenium
				.isElementPresent("css=div.om-cal-box > div.om-selectime.hidden"));
		//点击分钟
		selenium.click("css=div.om-cal-time > span.m");
		//弹出选择时间的div
		assertFalse(selenium
				.isElementPresent("css=div.om-cal-box > div.om-selectime.hidden"));
		//选择了50分钟
		selenium.click("css=div.om-selectime > a.item:nth-child(6)");
		//将50显示在分钟栏目
		assertEquals(
				"50",
				selenium.getText("css=div.om-cal-box div.om-cal-time > span.m.on"));

	}

}
