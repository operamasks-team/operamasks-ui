package unit.form;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class EditorTest extends TestCaseBase {
    
    private static final String PAGE_URL = "/operamasks-ui/tests/unit/form/test-editor.html";
    
    // 获取editor的value值
    private String getValue() {
        return selenium.getValue("id=testvalue");
    }
    
  
    
//    @Test
//    public void testReadOnly(){
//    	  selenium.open(PAGE_URL.concat("#readonly"));
//    	  TestUtils.sleep(2000);
//    	  selenium.click("//span[@id='cke_editor_readonly']//a[@id='cke_31']//span[@class='cke_icon']");
//    	  selenium.click("//button[@id='readonlyvalue']");
//    	  TestUtils.sleep(1000);
//    	  String initValue=getValue();
//          assertEquals(initValue, '1');
//          selenium.click("//button[@id='readonly']");
//          selenium.click("//span[@id='cke_editor_readonly']//a[@id='cke_31']//span[@class='cke_icon']");
//          selenium.click("//button[@id='readonlyvalue']");
//          TestUtils.sleep(2000);
//          assertEquals(initValue, getValue());
//    }
    
    @Test
    public void testInsertText(){
    	  selenium.open(PAGE_URL.concat("#inserttext"));
          TestUtils.sleep(2000);
          selenium.click("//button[@id='inserttext']");
          selenium.click("//button[@id='inserttextvalue']");
          TestUtils.sleep(2000);
          assertTrue(getValue().indexOf("test")>0);
    }
    
    @Test
    public void testInsertHtml(){
    	  selenium.open(PAGE_URL.concat("#inserthtml"));
          TestUtils.sleep(5000);
          selenium.click("//button[@id='inserthtml']");
          selenium.click("//button[@id='inserthtmlvalue']");
          TestUtils.sleep(2000);
          assertTrue(getValue().indexOf("<a href=\"#\">link </a>")>0);
    }
    
}
