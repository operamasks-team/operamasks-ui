package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class ShowCheckboxTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/checkbox-column.html";	
	/**
	 * 本例测试行的checkbox是否渲染正确
	 * 功能是否正常，本测试案例先选中
	 * 全部数据，然后取消选择一些，然后
	 * 去判断没有取消的任然处于选中状态
	 */
	@Test
	public void testCheckbox()
	{
		selenium.open(PAGE_URL);
		selenium.click("css=th > div > span.checkbox");   //先全选所有数据
		selenium.click("//table[@id='target-table']/tbody/tr/td[2]/div/span"); //取消选择
		selenium.click("//table[@id='target-table']/tbody/tr[2]/td[2]/div/span"); //取消选择
		selenium.click("//table[@id='target-table']/tbody/tr[3]/td[2]/div/span"); //取消选择
		selenium.click("//table[@id='target-table']/tbody/tr[8]/td[2]/div/span"); //取消选择
		selenium.click("//table[@id='target-table']/tbody/tr[7]/td[2]/div/span"); //取消选择
		selenium.click("//table[@id='target-table']/tbody/tr[5]/td[2]/div/span"); //取消选择
		
		//判断没有取消选择的checkbox是否处于选中状态
		String tr4Class = selenium.getAttribute("//table[@id='target-table']/tbody/tr[4]/@class");
		String tr6Class = selenium.getAttribute("//table[@id='target-table']/tbody/tr[6]/@class");
		assertEquals(true, tr4Class.indexOf("trSelected")!=-1);//是选中状态
		assertEquals(true, tr6Class.indexOf("trSelected")!=-1);//是选中状态
	}
	
}
