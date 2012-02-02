package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class DoubleClickTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/row-dblclick.html";
	
	
	/**
	 * 本例测试表格的行双击事件
	 * 双击之后弹出本行元素的值。
	 * @throws InterruptedException 
	 */
	@Test
	public void testDblClick()
	{
		selenium.open(PAGE_URL); //打开是否正确
		
		//双击3行4列
		selenium.doubleClick("//tr[3]/td[3]/div");
		if(selenium.isAlertPresent()){
			String alterVal = selenium.getAlert();
			System.out.println("双击之后弹出的值为： "+alterVal);
			assertEquals(true, alterVal.indexOf("双击事件")!=-1);  //点击最后一页按钮之后将两个值对比。
		}else{
			System.out.println("双击事件失败");
			assertEquals(true,false);
		}
	}

}
