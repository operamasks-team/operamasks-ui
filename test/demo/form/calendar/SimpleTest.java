package demo.form.calendar;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class SimpleTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/form/calendar/simple.html";
	
	@Test
	public void testSimple(){
		selenium.open(PAGE_URL);
		
		selenium.click("css=input.om-select-calendar");
		TestUtils.sleep(200);
		
		//判断元素是否存在
		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-cal-hd > a.om-title"));
		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-cal-bd > div.om-whd"));
		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-cal-bd > div.om-dbd"));

		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-setime.hidden"));
		
		//点击title，弹出设置年月的div
		selenium.click("css=div.om-cal-box > div.om-cal-hd > a.om-title");
		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-setime"));
		assertTrue(!selenium.isElementPresent("css=div.om-cal-box > div.om-setime.hidden"));
		
		//输入年份，点击确定按钮，年月div被隐藏
		selenium.type("css=div.om-cal-box > div.om-setime > p > input", "2012");
		TestUtils.sleep(200);
		selenium.click("css=div.om-cal-box > div.om-setime > p > button.ok");
		assertTrue(selenium.isElementPresent("css=div.om-cal-box > div.om-setime.hidden"));
		assertEquals("2012", selenium.getText("css=div.om-cal-box > div.om-cal-hd > a.om-title").substring(0, 4));
		
		
	}
	
}
