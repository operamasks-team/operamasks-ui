package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class StrippedColorTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/striped.html";
	
	/**
	 * 本例测试table是否有隔行
	 * 斑纹，测试案例设置为false，
	 * 则不会出现斑纹。
	 */
	@Test
	public void testStriped()
	{
		selenium.open(PAGE_URL); //table[@id='columnRenderer_table']/tr[@class='']
		String expContent = selenium.getText("//table[@id='target-table']  | //tr[@class='']");//获取有斑纹的行
		String[] astr = expContent.split("\n");
		System.out.println("如何没有斑纹的行超过了7列，则说明此案例测试成功，没有斑纹的行为："+astr.length+"列");
		assertEquals(true,astr.length >=7 ); //如果为空的话说明案例正确
	}

}
