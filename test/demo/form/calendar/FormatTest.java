package demo.form.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class FormatTest extends TestCaseBase {

	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/format.html";

	@Test
	public void testFormatt() {
		
		selenium.open(PAGE_URL);

		// 今天
		Date today = new Date();
		
		// 格式(中国)
		//java: yyyy年MM月dd日
		//js:	yy年mm月dd日
		selenium.click("css=input#chinese.om-select-calendar");
		TestUtils.sleep(200);
		//点击今天
		selenium.click("css=body > div.om-cal-call:nth-child(3)  div.om-dbd a.om-today");
		assertEquals(new SimpleDateFormat("yyyy年MM月dd日").format(today),
				selenium.getValue("css=input#chinese.om-select-calendar"));
		
		// 格式(ISO 8601)
		//java: yyyy-MM-dd
		//js:	yy-mm-dd
		selenium.click("css=input#iso8601.om-select-calendar");
		TestUtils.sleep(200);
		//点击今天
		selenium.click("css=body > div.om-cal-call:nth-child(4)  div.om-dbd a.om-today");
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(today),
				selenium.getValue("css=input#iso8601.om-select-calendar"));
		
		// 格式(简短)
		//java: d MMM, yy
		//js:	d M, y
		selenium.click("css=input#short.om-select-calendar");
		TestUtils.sleep(200);
		//点击今天
		selenium.click("css=body > div.om-cal-call:nth-child(5) div.om-cal-box  div.om-dbd a.om-today");
		assertEquals(new SimpleDateFormat("d MMM, yy", Locale.ENGLISH).format(today),
				selenium.getValue("css=input#short.om-select-calendar"));
	
		// 格式(全写)
		//java: EEEEE, d MMMMM, yyyy
		//js:	DD, d MM, yy
		selenium.click("css=input#full.om-select-calendar");
		TestUtils.sleep(200);
		//点击今天
		selenium.click("css=body > div.om-cal-call:nth-child(6) div.om-cal-box  div.om-dbd a.om-today");
		assertEquals(new SimpleDateFormat("EEEEE, d MMMMM, yyyy", Locale.ENGLISH).format(today),
				selenium.getValue("css=input#full.om-select-calendar"));
		
		
		// 格式(显示时间)
		//java: yyyy-MM-dd H:m:s a
		//js:	yy-mm-dd H:i:s A
		selenium.click("css=input#showtime.om-select-calendar");
		TestUtils.sleep(200);
		//选择今天的 11:10:10
		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-cal-ft span.h");
		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-selectime a.item:nth-child(12)");

		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-cal-ft span.m");
		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-selectime a.item:nth-child(2)");

		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-cal-ft span.s");
		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-selectime a.item:nth-child(2)");
		//点确定
		selenium.click("css=body > div.om-cal-call:nth-child(7) div.om-cal-box div.om-cal-ft button.ct-ok");
		
		today.setHours(11);
		today.setMinutes(10);
		today.setSeconds(10);
		
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH).format(today),
				selenium.getValue("css=input#showtime.om-select-calendar"));
		

	}
}
