package base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class TestCaseBase extends Assert {
	/** Use this object to run all of your selenium tests */
	protected Selenium selenium;
	protected static SeleniumServer server;

	private Properties initConfig() {
		InputStream in = TestCaseBase.class.getClassLoader()
				.getResourceAsStream("build.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("in setup");
		String navigation = initConfig().getProperty("test.current.browser");
		String localServiceAddress = initConfig().getProperty(
				"test.service.address");
		String navigationCnName = "";
		if (navigation.indexOf("iexplore") != -1) {
			navigationCnName = "IE浏览器";
		} else if (navigation.indexOf("firefox") != -1) {
			navigationCnName = "火狐浏览器";
		}
		System.out.println("使用的浏览器为：" + navigationCnName);

		selenium = new DefaultSelenium("localhost", 4444, navigation,
				localServiceAddress);
		selenium.start();
	}

	@After
	public void tearDown() throws Exception {
		selenium.close();
		selenium.stop();
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("in beforeClass");
		
		try {
			server = new SeleniumServer();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("in afterClass");
		server.stop();
	}
}
