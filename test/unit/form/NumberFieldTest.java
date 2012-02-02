package unit.form;

import org.junit.Test;

import base.TestCaseBase;
import base.util.TestUtils;

public class NumberFieldTest extends TestCaseBase {
    
    private static final String PAGE_URL = "/operamasks-ui/tests/unit/form/test-numberfield.html";
    
	@Test
    public void testAllowDecimals() {
        selenium.open(PAGE_URL);
        
        TestUtils.keyPressString(selenium, "id=numberfield1", "3.33");
		assertEquals("3.33", selenium.getValue("id=numberfield1"));
		
        TestUtils.keyPressString(selenium, "id=numberfield2", "3.33");
        assertEquals("3.33", selenium.getValue("id=numberfield2"));
        
        TestUtils.keyPressString(selenium, "id=numberfield3", "3.33");
        assertEquals("333", selenium.getValue("id=numberfield3"));
        
        selenium.runScript("changeAllowDecimals()");
        TestUtils.keyPressString(selenium, "id=numberfield3", "3.33");
        assertEquals("3.33", selenium.getValue("id=numberfield3"));
    }
	
    @Test
    public void testDecimalPrecision() {
        selenium.open(PAGE_URL);
        
        TestUtils.keyPressString(selenium, "id=numberfield4", "3.333");
        assertEquals("3.33", selenium.getValue("id=numberfield4"));
        
        TestUtils.keyPressString(selenium, "id=numberfield5", "3.333");
        assertEquals("3.3", selenium.getValue("id=numberfield5"));
        
        TestUtils.keyPressString(selenium, "id=numberfield6", "3.333");
        assertEquals("3.333", selenium.getValue("id=numberfield6"));
        
        selenium.runScript("changeDecimalPrecision()");
        TestUtils.keyPressString(selenium, "id=numberfield6", "3.333");
        assertEquals("3.3", selenium.getValue("id=numberfield6"));
    }

    @Test
    public void testAllowNegative() {
        selenium.open(PAGE_URL);
        
        TestUtils.keyPressString(selenium, "id=numberfield7", "-3.333");
        assertEquals("-3.33", selenium.getValue("id=numberfield7"));
        
        TestUtils.keyPressString(selenium, "id=numberfield8", "-3.33");
        assertEquals("3.33", selenium.getValue("id=numberfield8"));
        
        TestUtils.keyPressString(selenium, "id=numberfield9", "-3.33");
        assertEquals("-3.33", selenium.getValue("id=numberfield9"));
        
        selenium.runScript("changeAllowNegative()");
        TestUtils.keyPressString(selenium, "id=numberfield9", "-3.33");
        assertEquals("3.33", selenium.getValue("id=numberfield9"));
    }
    
    @Test
    public void testReadOnly() {
        selenium.open(PAGE_URL);
        
        TestUtils.keyPressString(selenium, "id=numberfield10", "323");
        assertEquals("323", selenium.getValue("id=numberfield10"));
        
        TestUtils.keyPressString(selenium, "id=numberfield11", "323");
        assertEquals("323", selenium.getValue("id=numberfield11"));
        
        TestUtils.keyPressString(selenium, "id=numberfield12", "323");
        assertEquals("", selenium.getValue("id=numberfield12"));
        
        selenium.runScript("changeReadOnly()");
        TestUtils.keyPressString(selenium, "id=numberfield12", "323");
        assertEquals("323", selenium.getValue("id=numberfield12"));
    }
    
    @Test
    public void testDisabled() {
        selenium.open(PAGE_URL);
        TestUtils.keyPressString(selenium, "id=numberfield13", "323");
        assertEquals("323", selenium.getValue("id=numberfield13"));
        
        TestUtils.keyPressString(selenium, "id=numberfield14", "323");
        assertEquals("323", selenium.getValue("id=numberfield14"));
        
        TestUtils.keyPressString(selenium, "id=numberfield15", "323");
        assertEquals("", selenium.getValue("id=numberfield15"));
        
        selenium.runScript("changeDisabled()");
        TestUtils.keyPressString(selenium, "id=numberfield15", "323");
        assertEquals("323", selenium.getValue("id=numberfield15"));
        
        selenium.runScript("testDisable()");
        TestUtils.keyPressString(selenium, "id=numberfield15", "323");
        assertEquals("", selenium.getValue("id=numberfield15"));
        
        selenium.runScript("testEnable()");
        TestUtils.keyPressString(selenium, "id=numberfield15", "323");
        assertEquals("323", selenium.getValue("id=numberfield15"));
    }
    
    @Test
    public void testInputChar() {
        selenium.open(PAGE_URL);
        
        TestUtils.keyPressString(selenium, "id=numberfield16", "3....s33");
        assertEquals("3.33", selenium.getValue("id=numberfield16"));
        
        TestUtils.keyPressString(selenium, "id=numberfield17", "-3-.-sz33");
        assertEquals("-3.33", selenium.getValue("id=numberfield17"));
        
        TestUtils.keyPressString(selenium, "id=numberfield18", "s3.sdrz..sads.33");
        assertEquals("3.33", selenium.getValue("id=numberfield18"));
    }

}
