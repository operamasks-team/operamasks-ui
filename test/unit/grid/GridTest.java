package unit.grid;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keyboard;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.util.TestUtils;

import com.google.common.base.Predicate;
/**
 * OmGrid的组件属性测试.
 * @author chenjay
 *
 */
public class GridTest extends Assert{
    private  static final String PAGE_URL = "http://localhost:8888/operamasks-ui/tests/unit/grid/test-grid.html";
    public static final String SPECIAL_KEY = "spec_keys_in_class_GridTest";
    private  static WebDriver driver;
    
    /*@Test 
    public void testStart() {
    	height();		//DONE
    	width();		//DONE
    	paged();		//DONE		
    	query();		//DONE
    	qtype();		//DONE
    	resizable();	//DONE
    	rpOptions();	//DONE
    	showToggleBtn();//DONE
    	striped();		//DONE
    	title();		//DONE
    	toolbar();		//DONE
    	url();			//DONE
    	useRp();		//DONE
    	method();		//DONE
    	novstripe();	//DONE
    	blockOpacity();	//DONE
    	fillEmptyRows();//DONE
    	lazyTotalUrl();	//DONE
    	loadMask();		//DONE
    	minColToggle();	//DONE
    	minHeight();	//DONE
    	minWidth();		//DONE
    	buttons();		//DONE
    	colModel();		//DONE
    	defaultColWidth();	//DONE
    	emptyMsg(); 	//DONE
    	errorMsg();		//DONE
    	limit();		//DONE
    	loadingMsg();	//DONE
    	noWrap();		//DONE
    	pageStat();		//DONE
    	pageText();		//DONE
    	showCheckbox();	//DONE
    	showIndex();	//DONE
//    	scrollLoad();	//怎么模拟滚动条下拉？TODO
//    	singleSelect();	//TODO
//    	total();		//貌似不需要这个  TODO
//    	page();			//TODO
    	
    	
    	
    	
    	//method
    	preProcess();	//DONE
    	
    	
    	//events
    	clientSort();	//DONE
    	onDragCol();	//DONE
    	onError();		//DONE
    	onRowClick();	//DONE
    	onRowDblClick();//DONE
    	onRowSelect();	//DONE
    	onSubmit();		//DONE
    	onSuccess();	//DONE
    	onToggleCol();	//DONE
    	
    	
    	
    }*/

    
    
     
   @Test public void onRowDblClick() {
    	driver.get(PAGE_URL);
		final String row = "table#onRowDblClick tbody tr:nth-child(1)";
		final String rowDblClickMsg = "row double clicked!";
		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		
		WebElement rowEle = driver.findElement(By.cssSelector(row));
		new Actions(driver).click(rowEle).build().perform();
		
		String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	jse.executeScript("window.alert = window.oldalert;");
    	
    	assertEquals(rowDblClickMsg, alertMsg);
    	
	}




	@Test public  void onToggleCol() {
		driver.get(PAGE_URL);
		
		final String toggleMsg = "Column 0 has been  hidden !";
		final String thId = "div#testOnToggleCol div.om-grid div.hDiv div.hDivBox th[abbr='id']";
		final String divNBtn = "div#testOnToggleCol div.om-grid div.nBtn";
		final String showIdCheck = "div#testOnToggleCol div.om-grid div.nDiv table tbody td.ndcol1 input";

		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		
		WebElement thIdEle = driver.findElement(By.cssSelector(thId));
		WebElement divNBtnEle = driver.findElement(By.cssSelector(divNBtn));
		WebElement showIdCheckEle = driver.findElement(By.cssSelector(showIdCheck));
		
		new Actions(driver).moveToElement(thIdEle).build().perform();//鼠标移上去
		assertTrue(divNBtnEle.isDisplayed());//"显示列"按钮出现
		//将id列隐藏，id列序号为0
		new Actions(driver).click(divNBtnEle).click(showIdCheckEle).build().perform();
		
		String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	jse.executeScript("window.alert = window.oldalert;");
    	
    	assertEquals(toggleMsg, alertMsg);
    	
    	
	}




	@Test public  void onSuccess() {
		driver.get(PAGE_URL);
		final String spanScsMsg = "div#testOnSuccess div.pGroup > span.pPageStat";
		final String errorText = "Msg From OnSuccess Func";
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String onScsText = driver.findElement(By.cssSelector(spanScsMsg)).getText();
				return errorText.equals(onScsText);
			}
		});
	}




	@Test public  void onSubmit() {
		driver.get(PAGE_URL);
		
		final String refresh = "div#testOnSubmit div.pReload.pButton span";
		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		//更改alert
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		//覆盖onSubmit回调
		jse.executeScript("$('#onSubmit').omGrid('omOptions',{onSubmit : alert('" + SPECIAL_KEY + "')});");
		//点击刷新
		new Actions(driver).click(driver.findElement(By.cssSelector(refresh))).build().perform();
		//获取alert的内容
		String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
		jse.executeScript("window.alert = window.oldalert;");
		assertEquals(SPECIAL_KEY, alertMsg);
	}




	@Test public  void onRowSelect() {
		driver.get(PAGE_URL);
		final String row = "table#onRowSelect tbody tr:nth-child(1)";
		final String rowSelectMsg = "row selected!";
		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		
		WebElement rowEle = driver.findElement(By.cssSelector(row));
		new Actions(driver).click(rowEle).build().perform();
		
		String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	jse.executeScript("window.alert = window.oldalert;");
    	
    	assertEquals(rowSelectMsg, alertMsg);
		
	}




	@Test public  void onRowClick() {
		driver.get(PAGE_URL);
		final String row = "table#onRowClick tbody tr:nth-child(1)";
		final String rowClickMsg = "row clicked!";
		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		
		WebElement rowEle = driver.findElement(By.cssSelector(row));
		new Actions(driver).click(rowEle).build().perform();
		
		String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	jse.executeScript("window.alert = window.oldalert;");
    	
    	assertEquals(rowClickMsg, alertMsg);
	}




	@Test public  void onError() {
		driver.get(PAGE_URL);
		final String spanErrMsg = "div#testOnError div.pGroup > span.pPageStat";
		final String errorText = "Msg From OnError Func";
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String onErrorText = driver.findElement(By.cssSelector(spanErrMsg)).getText();
				return errorText.equals(onErrorText);
			}
		});
	}

	@Test public  void onDragCol() {
		driver.get(PAGE_URL);
		
		final String cityHead = "div#testOnDragCol div.hDiv table thead tr th[abbr=city]";
		final String idHead = "div#testOnDragCol div.hDiv table thead tr th[abbr=id]";
		
		final String onDragMsg = "Column dragged from column 2 to 1";
		
		JavascriptExecutor jse = ((JavascriptExecutor)driver);
		jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
		
		WebElement cityHeadEle = driver.findElement(By.cssSelector(cityHead));
		WebElement idHeadEle = driver.findElement(By.cssSelector(idHead));
		new Actions(driver).clickAndHold(cityHeadEle).build().perform();
		new Actions(driver).moveToElement(idHeadEle).build().perform();
		new Actions(driver).release(idHeadEle).build().perform();
		
    	String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	jse.executeScript("window.alert = window.oldalert;");
    	assertEquals(alertMsg, onDragMsg);
		
	}

	@Test public  void clientSort() {
		driver.get(PAGE_URL);
		
		final String idHead = "div#testClientSort div.hDiv table thead tr th[abbr='id']";
		final String idDiv = "table#clientSort tbody tr:nth-child(1) td[abbr='id'] div";
		
		int firstValue = Integer.parseInt(driver.findElement(By.cssSelector(idDiv)).getText());
		new Actions(driver).click(driver.findElement(By.cssSelector(idHead))).build().perform();
		int secondValue = Integer.parseInt(driver.findElement(By.cssSelector(idDiv)).getText());
		assertTrue(firstValue != 4);
		assertTrue(secondValue == 4);
		
	}




	@Test public  void preProcess() {
		driver.get(PAGE_URL);
		final String value = "OperaMasksUI";
		final String cityTd = "table#preProcess tbody tr td[abbr='city'] div";
		List<WebElement> cityEleLst = driver.findElements(By.cssSelector(cityTd));
		for (WebElement cityEle : cityEleLst) {
			String cityValue = cityEle.getText().trim();
			if (cityValue != null && cityValue.length() != 0) {
				assertTrue(cityValue.equals(value));
			}
		}
	}




	@Test public  void minHeight() {
    	driver.get(PAGE_URL);
		
		final String omGrid = "div#testMinHeight div.om-grid";
		final String vGrip = "div#testMinHeight div.vGrip";
		final WebElement omGridEle = driver.findElement(By.cssSelector(omGrid));
		WebElement vGripEle = driver.findElement(By.cssSelector(vGrip));
		new Actions(driver).clickAndHold(vGripEle).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				new Actions(driver).moveByOffset(0, -5).build().perform();
				int newHeight = omGridEle.getSize().height;
				return newHeight >= 250 && newHeight <= 255;
			}
		});
		new Actions(driver).release(vGripEle).build().perform();
	}

	@Test public  void minWidth() {
		driver.get(PAGE_URL);
		
		final String idCityDrag = "div#testMinWidth div.om-grid div.cDrag div:nth-child(2)";
		final String idCol = "div#testMinWidth div.hDiv table thead tr th[abbr='id']";
		final WebElement vDragEle = driver.findElement(By.cssSelector(idCityDrag));
		final WebElement idColEle = driver.findElement(By.cssSelector(idCol));
		
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				new Actions(driver).clickAndHold(vDragEle).build().perform();
				new Actions(driver).moveByOffset(-5, 0).build().perform();
				new Actions(driver).release(vDragEle).build().perform();
				int newWidth = idColEle.getSize().width;
				return newWidth >= 40 && newWidth <= 80;  //误差在+-15px
			}
		});
	}




	@Test public  void minColToggle() {
		driver.get(PAGE_URL);
		
		final String thHidden = "div#testMinColToggle div.om-grid div.hDiv div.hDivBox th[hidden]";
		final String thId = "div#testMinColToggle div.om-grid div.hDiv div.hDivBox th[abbr='id']";
		final String divNBtn = "div#testMinColToggle div.om-grid div.nBtn";
		final String showIdCheck = "div#testMinColToggle div.om-grid div.nDiv table tbody td.ndcol1 input";

		List<WebElement> thEle = driver.findElements(By.cssSelector(thHidden));
		WebElement thIdEle = driver.findElement(By.cssSelector(thId));
		WebElement divNBtnEle = driver.findElement(By.cssSelector(divNBtn));
		WebElement showIdCheckEle = driver.findElement(By.cssSelector(showIdCheck));
		
		final int oldThSize = thEle.size();
		assertFalse(divNBtnEle.isDisplayed());//"显示列"按钮不出现
		new Actions(driver).moveToElement(thIdEle).build().perform();//鼠标移上去
		assertTrue(divNBtnEle.isDisplayed());//"显示列"按钮出现
		new Actions(driver).click(divNBtnEle).click(showIdCheckEle).build().perform();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				int newThSize = driver.findElements(By.cssSelector(thHidden)).size();
				return oldThSize != newThSize;
			}
		});
		
	}




	@Test public  void loadMask() {
		driver.get(PAGE_URL);
		String blockDiv = "div#testLoadMask div.om-grid div.gBlock";
		String refreshSpan = "div#testLoadMask div.pDiv div.pGroup div.pReload.pButton span";
		
		new Actions(driver).click(driver.findElement(By.cssSelector(refreshSpan))).build().perform();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement blockDivEle = driver.findElement(By.cssSelector(blockDiv));
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
	}

	@Test public  void lazyTotalUrl() {
		driver.get(PAGE_URL);
		final String totalSpan = "div#testLazyTotalUrl div.om-grid div.pDiv div.pGroup span.pPageStat";
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String totalCount = driver.findElement(By.cssSelector(totalSpan)).getText();
				return totalCount.contains("12358"); //约定好的数字
			}
		});
	}

	@Test public  void fillEmptyRows() {
		driver.get(PAGE_URL);
		String emptyTr = "table#fillEmptyRows tr.emptyTr";
		assertTrue(driver.findElement(By.cssSelector(emptyTr)) != null);
	}

	@Test public  void blockOpacity() {
		driver.get(PAGE_URL);
		
		String blockDiv = "div#testBlockOpacity div.om-grid div.gBlock";
		String refreshSpan = "div#testBlockOpacity div.pDiv div.pGroup div.pReload.pButton span";
		new Actions(driver).click(driver.findElement(By.cssSelector(refreshSpan))).build().perform();
		String opacity = driver.findElement(By.cssSelector(blockDiv)).getCssValue("opacity");
		assertTrue("0.5".equals(opacity));
	}




	@Test public  void novstripe() {
		driver.get(PAGE_URL);
		String td = "table#novstripe tbody tr td";
		WebElement tdEle = driver.findElement(By.cssSelector(td));
		String bdRightClr = tdEle.getCssValue("border-right-color");
		assertTrue("rgb(255, 255, 255)".equals(bdRightClr));
		
	}

	@Test public  void method() {
		driver.get(PAGE_URL);
		final String cityTd = "table#method tbody tr td[abbr='city'] div";
		WebDriverWait wait = new WebDriverWait(driver, /*seconds = */5);
		wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String cityValue = driver.findElement(By.cssSelector(cityTd)).getText();
				return cityValue.contains("POST");
			}
		});
	}

	@Test public  void useRp() {
		driver.get(PAGE_URL);
		
		String select = "div#testUseRp div.pDiv select";
		WebElement selectEle = driver.findElement(By.cssSelector(select));
		
		new Actions(driver).click(selectEle).build().perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER).build().perform();
		
	}




	@Test public  void url() {
		driver.get(PAGE_URL);
		String tdDiv = "table#url tbody tr td[abbr='city'] div";
		WebElement tdDivEle = driver.findElement(By.cssSelector(tdDiv));
		assertTrue(tdDivEle.getText().contains("DataFromUrlMethod"));
	}




	@Test public  void total() {
		// TODO Auto-generated method stub
		
	}




	@Test public  void toolbar() {
		driver.get(PAGE_URL);
		String buttons = "div#testToolbar div.tDiv button";
		List<WebElement> btnLst = driver.findElements(By.cssSelector(buttons));
		assertEquals(3, btnLst.size());
	}

	@Test public  void title() {
		driver.get(PAGE_URL);
		String title = "div#testTitle div.mDiv div.ftitle";
		assertEquals("TableOfNet",driver.findElement(By.cssSelector(title)).getText());
	}

	@Test public  void striped() {
		driver.get(PAGE_URL);
		
		String trEven = "div#testStriped table#striped tbody tr:nth-child(even)";
		String trOdd = "div#testStriped table#striped tbody tr:nth-child(odd)";
		
		List<WebElement> evenLst =  driver.findElements(By.cssSelector(trEven));
		List<WebElement> oddLst =  driver.findElements(By.cssSelector(trOdd));
		
		for (WebElement e : evenLst) {
			assertTrue(e.getAttribute("class").contains("erow"));
		}
		for (WebElement e : oddLst) {
			assertFalse(e.getAttribute("class").contains("erow"));
		}		
	}

	/*@Test public  void singleSelect() {
		driver.get(PAGE_URL);
//		Selenium selenium = new WebDriverBackedSelenium(driver, PAGE_URL);
		
		String tr1 = "div#testSingleSelect table#singleSelect tbody tr:nth-child(1)";
		String tr2 = "div#testSingleSelect table#singleSelect tbody tr:nth-child(2)";
		String trSelected = "div#testSingleSelect table#singleSelect tbody tr.trSelected";
		
		WebElement tr1Ele = driver.findElement(By.cssSelector(tr1));
		WebElement tr2Ele = driver.findElement(By.cssSelector(tr2));

//		new Actions(driver).keyDown(Keys.CONTROL);
//		//点击第一行
//		new Actions(driver).click(tr1Ele).build().perform();
//		int selectedSize = driver.findElements(By.cssSelector(trSelected)).size();
//		//按住ctrl键，点击第二行
//		new Actions(driver).click(tr2Ele).build().perform();
//		int selectedSizeNew = driver.findElements(By.cssSelector(trSelected)).size();
		
		
		Keyboard kb = ((ChromeDriver)driver).getKeyboard();
		kb.pressKey(Keys.CONTROL);
		new Actions(driver).click(tr1Ele).build().perform();
		new Actions(driver).click(tr2Ele).build().perform();
		
		TestUtils.sleep(10000);
//		assertEquals(1, selectedSize);
//		assertEquals(2, selectedSizeNew);
		
	}*/

	
	@Test public  void showToggleBtn() {
		driver.get(PAGE_URL);
		
		final String th = "div#testShowToggleBtn div.om-grid div.hDiv div.hDivBox th[abbr='id']";
		final String divNBtn = "div#testShowToggleBtn div.om-grid div.nBtn";
		assertFalse(driver.findElement(By.cssSelector(divNBtn)).isDisplayed());//"显示列"按钮不出现
		new Actions(driver).moveToElement(driver.findElement(By.cssSelector(th))).build().perform();//鼠标移上去
		assertTrue(driver.findElement(By.cssSelector(divNBtn)).isDisplayed());//"显示列"按钮出现
	}


	@Test public  void scrollLoad() {
		
	}
	@Test
	public void resizable() {
		driver.get(PAGE_URL);
		
		String omGrid = "div#testResizable div.om-grid";
		String hGrip = "div#testResizable div.hGrip";
		String vGrip = "div#testResizable div.vGrip";
		
		WebElement omGridEle = driver.findElement(By.cssSelector(omGrid));
		WebElement hGripEle = driver.findElement(By.cssSelector(hGrip));
		WebElement vGripEle = driver.findElement(By.cssSelector(vGrip));
		Dimension oldDim = omGridEle.getSize();
		
		new Actions(driver).clickAndHold(hGripEle).build().perform();
		new Actions(driver).moveByOffset(20, 0).build().perform();
		new Actions(driver).release(hGripEle).build().perform();
		
		new Actions(driver).clickAndHold(vGripEle).build().perform();
		new Actions(driver).moveByOffset(0, 30).build().perform();
		new Actions(driver).release(vGripEle).build().perform();
		
		Dimension newDim = omGridEle.getSize();
		
		assertEquals(20, newDim.getWidth() - oldDim.getWidth());
		assertEquals(30, newDim.getHeight() - oldDim.getHeight());
		
		TestUtils.sleep(4000);
		
	}

	@Test 
	public void paged() {
		driver.get(PAGE_URL);
		String pDiv = "div#testPaged div.pDiv";
		//有pDiv分页条
		assertTrue(driver.findElement(By.cssSelector(pDiv)).isDisplayed());
    }
    
	@Test
    public void query() {
    	driver.get(PAGE_URL);
    	
    	final String refresh = "div#testQuery div.pReload.pButton span";
    	final String tdDiv = "table#query tbody tr td[abbr='city'] div";
    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("$('#query').omGrid('omOptions',{query : '" + SPECIAL_KEY + "'});");//修改query的值
    	new Actions(driver).click(driver.findElement(By.cssSelector(refresh))).build().perform(); //点击刷新
    	WebDriverWait wait = new WebDriverWait(driver, 2);
    	wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String tdDivText = driver.findElement(By.cssSelector(tdDiv)).getText();
				return tdDivText != null && SPECIAL_KEY.equals(tdDivText);
			}
		});
    }
    
   @Test public void qtype() {
    	driver.get(PAGE_URL);
    	
    	final String refresh = "div#testQtype div.pReload.pButton span";
    	final String tdDiv = "table#qtype tbody tr td[abbr='city'] div";
    	
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("$('#qtype').omGrid('omOptions',{qtype : '" + SPECIAL_KEY + "'});");//修改qtype的值
    	new Actions(driver).click(driver.findElement(By.cssSelector(refresh))).build().perform(); //点击刷新
    	WebDriverWait wait = new WebDriverWait(driver, 2);
    	wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				String tdDivText = driver.findElement(By.cssSelector(tdDiv)).getText();
				return tdDivText != null && SPECIAL_KEY.equals(tdDivText);
			}
		});
    	
	}
    
   @Test
    public void rpOptions() {
    	driver.get(PAGE_URL);
    	
    	final String select = "div#testRpOptions div.pGroup select[name='rp']";
    	final String trs = "div#testRpOptions div.bDiv table tbody tr";
    	WebElement selectEle = driver.findElement(By.cssSelector(select));
    	List<WebElement> options = selectEle.findElements(By.tagName("option"));
    	for (WebElement option : options) {
    		if (option.getText().equals("每页15条")) {
    			option.click();
    			break;
    		}
    	}
    	
    	WebDriverWait wait = new WebDriverWait(driver, 4);
    	wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver driver) {
				int trSize = driver.findElements(By.cssSelector(trs)).size();
				return 15 == trSize;
			}
		});
    }
    
   @Test 
    public void buttons(){
    	driver.get(PAGE_URL);
    	String dltBtn = "div#testButtons span.tbText.deleteClass";
    	String crtBtn = "div#testButtons span.tbText.createClass";
    	String dldBtn = "div#testButtons span.tbText.downloadClass";
    	
    	JavascriptExecutor jse = (JavascriptExecutor) driver;
    	jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
    	new Actions(driver).click(driver.findElement(By.cssSelector(dltBtn))).build().perform();
    	assertEquals("delete", jse.executeScript("return document.lastAlertMsg"));
    	new Actions(driver).click(driver.findElement(By.cssSelector(crtBtn))).build().perform();
    	assertEquals("create", jse.executeScript("return document.lastAlertMsg"));
    	new Actions(driver).click(driver.findElement(By.cssSelector(dldBtn))).build().perform();
    	assertEquals("download", jse.executeScript("return document.lastAlertMsg"));
    	jse.executeScript("window.alert = window.oldalert;");
    	
    }
   @Test 
    public void colModel(){
    	driver.get(PAGE_URL);
    	String tr = "div#testColModel div.hDiv > div.hDivBox > table > thead > tr";
    	String idTh = "th[abbr = 'id'] > div.checkboxheader";
    	String cityTh = "th[abbr = 'city'] > div.checkboxheader";
    	String addressTh = "th[abbr = 'address'] > div.checkboxheader";
    	
    	assertEquals("ID", driver.findElement(By.cssSelector(tr + " > " + idTh)).getText());
    	assertEquals("地区", driver.findElement(By.cssSelector(tr + " > " + cityTh)).getText());
    	assertEquals("地址", driver.findElement(By.cssSelector(tr + " > " + addressTh)).getText());
    }
    
   @Test
    public void defaultColWidth(){
    	driver.get(PAGE_URL);
    	
    	String tr = "div#testDefaultColWidth div.hDiv > div.hDivBox > table > thead > tr";
    	String idTh = "th[abbr = 'id'] > div";
    	String cityTh = "th[abbr = 'city'] > div";
    	String addressTh = "th[abbr = 'address'] > div";
    	
        //defaultColWidth : 60 如果不指定列宽，则列宽被置为60
    	assertEquals("60px", driver.findElement(By.cssSelector(tr + " " + idTh)).getCssValue("width"));
    	assertEquals("60px", driver.findElement(By.cssSelector(tr + " " + cityTh)).getCssValue("width"));
    	assertEquals("60px", driver.findElement(By.cssSelector(tr + " " + addressTh)).getCssValue("width"));
    } 
    
   @Test 
    public void emptyMsg(){
    	driver.get(PAGE_URL);
    	String spanPageStat = "div#testEmptyMsg div.pGroup > span.pPageStat";
    	assertEquals("no data at all", driver.findElement(By.cssSelector(spanPageStat)).getText());
    }
    
   @Test
    public void errorMsg(){
    	driver.get(PAGE_URL);
    	
    	final String spanErrMsg = "div#testErrorMsg div.pGroup > span.pPageStat";
		assertEquals("error happended!", driver.findElement(By.cssSelector(spanErrMsg)).getText());
    }
   @Test
    public void height(){
    	driver.get(PAGE_URL);
    	String divOmGrid = "div#testHeight div.om-grid";
    	int height = driver.findElement(By.cssSelector(divOmGrid)).getSize().getHeight();
    	//为了兼容ie和firefox和模型不一致的实际情况，此处只能使用一个范围来做判断实行允许的值为+-5
    	boolean isPass = height >= 395 || height <= 405 ;
    	assertEquals(true, isPass);
    	TestUtils.sleep(10000);
    }
    
   @Test
    public void limit(){
    	driver.get(PAGE_URL);
    	String trs = "table#limit tbody tr";
    	assertEquals(20, driver.findElements(By.cssSelector(trs)).size());
    }
    
   @Test
    public void loadingMsg(){
    	driver.get(PAGE_URL);
    	String spanPageStat = "div#testLoadingMsg div.pGroup > span.pPageStat";	
    	String refreshSpan = "div#testLoadingMsg div.pDiv div.pGroup div.pReload.pButton span";
		new Actions(driver).click(driver.findElement(By.cssSelector(refreshSpan))).build().perform();
    	assertEquals("I am loading", driver.findElement(By.cssSelector(spanPageStat)).getText());
    }
   @Test
    public void noWrap(){
    	driver.get(PAGE_URL);
    	JavascriptExecutor jse = ((JavascriptExecutor)driver);
    	jse.executeScript("window.oldalert = window.alert; window.alert=function(msg){document.lastAlertMsg = msg ;}");
    	jse.executeScript("rowHeight();");
    	String alertMsg = jse.executeScript("return document.lastAlertMsg;").toString();
    	assertEquals(true, Integer.valueOf(alertMsg.replace("px", "")) > 16);
    	jse.executeScript("window.alert = window.oldalert;");
    }
   @Test
    public void page(){
    	driver.get(PAGE_URL);

    }
    
   @Test
    public void pageStat(){
    	driver.get(PAGE_URL);
    	String spanPageStat = "div#testPageStat div.pGroup > span.pPageStat";
    	String stat = driver.findElement(By.cssSelector(spanPageStat)).getText();
    	boolean found = Pattern.compile("^.*total.*from.*to.*$").matcher(stat).find();
    	assertEquals(true, found);
    }
    
   @Test
    public void pageText(){
    	driver.get(PAGE_URL);
    	String spanPControl = "div#testPageText div.pGroup span.pcontrol";
    	String control = driver.findElement(By.cssSelector(spanPControl)).getText();
    	boolean found = Pattern.compile("^.*total.*jump.*page.*$").matcher(control).find();
    	assertEquals(true, found);
    	
    }
   @Test
    public void showCheckbox(){
    	driver.get(PAGE_URL);
    	String spanCkb = "div#testShowCheckbox div.bDiv span.checkbox";
    	boolean isExist = driver.findElement(By.cssSelector(spanCkb)).isDisplayed();
    	assertEquals(true, isExist);
    }
   @Test
    public void showIndex(){
    	driver.get(PAGE_URL);
    	String spanInd = "div#testShowIndex div.hDivBox th[axis='colindex']";
    	boolean isExist = driver.findElement(By.cssSelector(spanInd)).isDisplayed();
    	assertEquals(true, isExist);
    }
   @Test
    public void width(){
    	driver.get(PAGE_URL);
    	String divOmGrid = "div#testHeight div.om-grid";
    	int width = driver.findElement(By.cssSelector(divOmGrid)).getSize().getWidth();
    	//为了兼容ie和firefox和模型不一致的实际情况，此处只能使用一个范围来做判断实行允许的值为+-5
    	assertEquals(true, width > 795 || width < 805);
    }
    
    
    
    
    @BeforeClass
    public static void beforeClass(){
    	DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("webdriver.chrome.driver", "D:/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
		try {
			driver =  new ChromeDriver(caps);
//			driver = new InternetExplorerDriver();
		} catch (Exception e) {
			e.printStackTrace();
    		System.out.println("new driver() encountered an exception in @AfterClass");
		}
    }
    @AfterClass
    public static void afterClass(){
    	try{
    		driver.quit();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("driver.close() encountered an exception in @AfterClass");
		}
    }
}
