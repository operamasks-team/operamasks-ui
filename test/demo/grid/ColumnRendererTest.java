package demo.grid;

import org.junit.Test;

import base.TestCaseBase;

public class ColumnRendererTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/column-renderer.html";
	/**
	 * 本例测试列渲染属性
	 * 如果地址为电信的列
	 * 就渲染成红色
	 */
	@Test
	public void testRenderer()
	{
		selenium.open(PAGE_URL);
		String expContent = selenium.getText("//tr[3]/td[5]/div");//获取有斑纹的行
		String outHtml = "";
		if("电信".equals(expContent)){
		    outHtml = selenium.getAttribute("//tr[3]/td[5]/div/span@style");
			assertEquals(true, outHtml!=null && outHtml.indexOf("red")!=-1);//只要外围包含了red属性的span标签，则测试通过
		}
		System.out.println("表格的第三行第4列值为："+expContent+" 并且表格的字体颜色style为："+outHtml);
	}
	
}
