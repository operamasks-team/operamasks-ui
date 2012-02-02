package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class BlockOpacityTest extends TestCaseBase{
	
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/block-opacity.html";
	
	@Test
	public void testGoogle()
	{
		selenium.open(PAGE_URL); //打开是否正确
		System.out.println("暂时不测试");  //$(".gBlock").attr('style')通过获取style再获取opacity ，但是由于测试案例时间太短，无法捕获到蒙板状态
	}

}
