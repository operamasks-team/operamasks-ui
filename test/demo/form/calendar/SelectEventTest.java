package demo.form.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class SelectEventTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/select-event.html";
	
	@Test
	public void testSelectEvent() {
		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
		Date today = new Date();
		String tomorrow = sdf.format(new Date(today.getTime() + 1 * 24 * 60 * 60 * 1000));
		
		//选择明天的日期
		selenium.click("css=div.om-cal-call > div.om-cal-box > div.om-cal-bd > div.om-dbd > a.om-today + a");
		assertEquals(tomorrow, selenium.getAlert());
		
	}
}
