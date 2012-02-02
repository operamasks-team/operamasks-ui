package base.util;

import com.thoughtworks.selenium.Selenium;

public class TestUtils {
    
    /**
     * 将char转换成Ascii的字符串.
     * @param c
     * @return
     */
    private static String charToAsciiStr(char c) {
        // 在selenium中输入小写的a到小写的y时，需要将asiic码减去32，很奇怪
        if(c >= 97 && c <= 121) {
            return Integer.toString(c - 32);
        }
        return Integer.toString(c - 0);
    }
    
    /**
     * 在selenium中，无法使用keyPress方法来输入一个Dot符号，此方法用于临时解决输入一个Dot符号的问题.
     * @param selenium
     * @param locator
     * @param c
     */
    private static void keyPressNativeChar(Selenium selenium, String locator, char c) {
        selenium.focus(locator);
        selenium.keyPressNative(charToAsciiStr(c));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 线程睡眠millis毫秒
     * @param millis
     */
    public static void sleep(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    /**
     * 按顺序将str分解成每一个char输入到指定的locator位置中去.
     * @param selenium
     * @param locator
     * @param str
     */
    public static void keyPressString(Selenium selenium, String locator,
            String str) {
        for (char c : str.toCharArray()) {
            keyPressNativeChar(selenium, locator, c);
        }
    }
    
    /**
     * 获取当前浏览器窗口左边到屏幕的距离
     * @return
     */
    public static int getCurrentWindowScreenX(Selenium selenium) {
    	return new Integer(selenium.getEval("selenium.browserbot.getCurrentWindow().innerHeight")).intValue();
    }
    /**
     * 获取当前浏览器窗口上边到屏幕的距离
     * @return
     */
    public static int getCurrentWindowScreenY(Selenium selenium) {
    	return new Integer(selenium.getEval("selenium.browserbot.getCurrentWindow().innerHeight")).intValue();
    }

}
