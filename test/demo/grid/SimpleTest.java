package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class SimpleTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/simple.html";
	
	/**
	 * 主要测试grid是否渲染正确
	 * 可以通过点击grid里面的分页
	 * 上一页下一页是否正确点击来
	 * 测试grid的渲染情况
	 */
	@Test
	public void testGrid()
	{
		selenium.open(PAGE_URL); //打开是否正确
		
		selenium.click("css=div.pNext.pButton > span");   //点击下一页按钮 证明已经渲染出正确的grid
		
		selenium.click("css=div.pNext.pButton > span");   //点击下一页按钮 证明取数正确
		selenium.click("css=div.pLast.pButton > span");   //点击最后一页按钮
		String pageNum = selenium.getValue("css=span.pcontrol > input[type=text]"); //获取输入框页码数
		String pageNum_bar = selenium.getText("css=div.pGroup > span.pageLink > a:last"); //分页工具条上面的页码记录
		System.out.println("Grid总共有数据 "+pageNum_bar+" 页，实际渲染出来 "+pageNum+" 页 ");
		
		assertEquals(pageNum, pageNum_bar);  //点击最后一页按钮之后将两个值对比。
		
		selenium.click("css=div.pReload.pButton > span");  //点击刷新按钮重新reload数据
	}
}
