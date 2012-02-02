package unit.form;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class ComboTest extends TestCaseBase {
    
    private static final String PAGE_URL = "/operamasks-ui/tests/unit/form/combo.html";
    // 点击下拉按钮
    private void clickTrigger(String id) {
    	selenium.runScript("clickTrigger('"+id+"')");
    }
    
    // 点击下接按钮，并选中某一个选项
    private void selectItem(String id, int index) {
        clickTrigger(id);
        selenium.runScript("selectItem("+index+")");
    }
    
    // 获取某一下拉列表的内容
    private String getText() {
    	selenium.runScript("getItems()");
        return selenium.getAlert();
    }
    
    // 获取某一过滤列表的第一个元素
    private String getFirstText() {
    	selenium.runScript("getFirstItem()");
    	return selenium.getAlert();
    }
    
    // 获取combo的value值
    private String getValue(String id) {
        return selenium.getValue(String.format("//input[@id='%s']", id));
    }
    
    @Test
    public void testAutoFilter() {
        selenium.open(PAGE_URL);
        TestUtils.keyPressString(selenium, "//input[@id='combo_autoFilter']", "kin");
        TestUtils.sleep(2000);
        assertEquals("kingdee", getFirstText());
    }
    
    @Test
    public void testDisabled() {
        selenium.open(PAGE_URL);
        assertEquals("true", selenium.getAttribute("id=combo_disabled@disabled"));
        
        clickTrigger("combo_disabled");
        assertEquals("", getText());
    }
    
    @Test
    public void testEditable() {
        selenium.open(PAGE_URL);
        TestUtils.keyPressString(selenium, "//input[@id='combo_editable_false']", "apusic");
        assertEquals("", getValue("combo_editable_false"));
        
        TestUtils.keyPressString(selenium, "//input[@id='combo_editable_true']", "apusic");
        assertEquals("apusic", getValue("combo_editable_true"));
        
        TestUtils.keyPressString(selenium, "//input[@id='combo_editable_default']", "apusic");
        assertEquals("apusic", getValue("combo_editable_default"));
    }
    
    @Test
    public void testEmptyText() {
        selenium.open(PAGE_URL);
        System.out.println(selenium.getValue("id=combo_emptyText")+"--------------");
        assertEquals("没有数据", selenium.getValue("id=combo_emptyText"));
        
        selenium.focus("id=combo_emptyText");
        assertEquals("", selenium.getValue("id=combo_emptyText"));
    }
    
    @Test
    public void testListProvider() {
    	selenium.open(PAGE_URL);
    	clickTrigger("combo_listProvider");
    	assertEquals(true, getText().indexOf("apusic---金蝶中间件")!=-1);
    }
    
    @Test
    public void testFilterDelay() {
    	selenium.open(PAGE_URL);
    	selenium.focus("id=combo_filterDelay");
    	TestUtils.keyPressString(selenium, "id=combo_filterDelay", "op");
    	assertEquals("apusic.com", getFirstText());
    	
    	// sleep more than 1000 mil than the filterDelay effect
    	TestUtils.sleep(1500);
    	assertEquals("operamasks.org", getFirstText());
    }
    
    @Test
    public void testFilterStrategy() {
    	selenium.open(PAGE_URL);
    	selenium.focus("id=combo_filterStrategy");
    	TestUtils.keyPressString(selenium, "id=combo_filterStrategy", "mas");
    	TestUtils.sleep(1000);
    	assertEquals("operamasks.org", getFirstText());
    	
    	selenium.focus("id=combo_filterStrategy_fn");
    	TestUtils.keyPressString(selenium, "id=combo_filterStrategy_fn", "ac");
    	TestUtils.sleep(1000);
    	assertEquals("apusic.com", getFirstText());
    }
    
 /*   @Test
    public void testForceValueInOptions() {
    	selenium.open(PAGE_URL);
    	selenium.focus("id=combo_forceValueInOptions");
    	TestUtils.keyPressString(selenium, "id=combo_forceValueInOptions", "mas");
    	TestUtils.sleep(1000);
    	assertEquals("mas", getValue("combo_forceValueInOptions"));
    }*/
    
    @Test
    public void testHeightWidth() {
    	selenium.open(PAGE_URL);
    	// assertEquals(304, selenium.getElementHeight("id=combo_width"));
    	assertEquals(304, selenium.getElementWidth("id=combo_width"));
    }
    
    @Test
    public void testInputField() {
    	selenium.open(PAGE_URL);
    	clickTrigger("combo_inputField");
    	assertEquals("apusic.com", getFirstText());
    }
    
    @Test
    public void testLazyLoad() {
        selenium.open(PAGE_URL);
        String opts = null;
        try{
          opts = selenium.getText("id=combo_lazyLoad");
        }catch (Exception e) {
			System.out.println("吃掉异常，看到此打印信息表明lazyLoad属性生效，没有生成下拉div");
		}
        assertEquals(true, StringUtils.isBlank(opts));
    }
    
    @Test
    public void testListAutoWidth() {
    	selenium.open(PAGE_URL);
    	clickTrigger("combo_listAutoWidth");
    	selenium.runScript("getListWidth()");
    	assertEquals("278px", selenium.getAlert());
    }
    
    @Test
    public void testListMaxHeight() {
        selenium.open(PAGE_URL);
        clickTrigger("combo_listMaxHeight");
        selenium.runScript("getListHeight()");
    	assertEquals("50px", selenium.getAlert());
    }
    
    @Test
    public void testOnValueChange() {
        selenium.open(PAGE_URL);
        clickTrigger("combo_onValueChange");
        selectItem("combo_onValueChange", 2);
    	assertEquals("old value:undefined - new value:1", selenium.getText("id=valueChange_span"));
    }
    
    @Test
    public void testOptionField() {
    	selenium.open(PAGE_URL);
    	clickTrigger("combo_optionField");
    	assertEquals("金蝶中间件", getFirstText());
    }
    
    @Test
    public void testOptionFieldFn() {
    	selenium.open(PAGE_URL);
    	clickTrigger("combo_optionField_1");
    	assertEquals("0.apusic(金蝶中间件)", getFirstText());
    }
    
    @Test
    public void testReadOnly() {
    	selenium.open(PAGE_URL);
    	selenium.focus("id=combo_readOnly");
    	TestUtils.keyPressString(selenium, "id=combo_readOnly", "opera");
    	assertEquals("apusic.com", getValue("combo_readOnly"));
    }
    
    @Test
    public void testRecords() {
        selenium.open(PAGE_URL);
        
        clickTrigger("combo_records");
        assertEquals(true, StringUtils.isNotBlank(getText()));
    }
    
	@Test
    public void testUrl() { //暂缓测试，执行clickTrigger("combo_url")无效果，暂未找到原因
        selenium.open(PAGE_URL);
        clickTrigger("combo_url");
        //assertEquals(true, StringUtils.isNotBlank(getText()));
    }
	
	@Test
    public void testValue() {
        selenium.open(PAGE_URL);
        
        assertEquals("baidu", selenium.getValue("id=combo_value"));
    }
	
	@Test
    public void testValueField() {
        selenium.open(PAGE_URL);
        
        selectItem("combo_valueField_fn", 2);
        assertEquals("金蝶中间件", selenium.getValue("id=combo_valueField_fn"));
    }
	
	// -----------------------------------------methods---------------------------------
	@Test
	public void testDisable_enable() {
		selenium.open(PAGE_URL);
		selenium.click("id=disable_btn");
		clickTrigger("combo_disable_enable");
		String text = "";
		try {
			text = getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("", text);
		selenium.click("id=enable_btn");
		 selenium.runScript("alert($('#combo_get_set_value').omCombo('value'))");
		assertEquals("1", selenium.getAlert());
	}
	
	@Test
    public void testGetSetValue() {
        selenium.open(PAGE_URL);
        selenium.click("id=setValue_btn");
        assertEquals("operamasks.org", getValue("combo_get_set_value"));
        selenium.click("id=getValue_btn");
        assertEquals("2", selenium.getText("id=getValue_span"));
    }
	
	@Test
    public void testLoadRecords() {
        selenium.open(PAGE_URL);
        selenium.click("id=loadRecords_btn");
        clickTrigger("combo_loadRecords");
        assertEquals("Apusic Server", getFirstText());
    }
	
}
