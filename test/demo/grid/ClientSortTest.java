package demo.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import base.TestCaseBase;

public class ClientSortTest extends TestCaseBase{
	
	private static final String PAGE_URL = "/operamasks-ui/demos/grid/client-sort.html";
	
	/**
	 * 本例测试列排序
	 */
	@Test
	public void testClintSort()
	{
		selenium.open(PAGE_URL); //打开是否正确
		List<String> start = new ArrayList<String>();
		List<String> id = new ArrayList<String>();
		for(int i=1;i<=3;i++){
			start.add(selenium.getText("//table[@id='target-table']//tr["+i+"]//td[3]"));
		}
		assertEquals(true, assertSort(start));
		
		selenium.click("css=div.sasc");
		for(int i=1;i<=3;i++){
			id.add(selenium.getText("//table[@id='target-table']//tr["+i+"]//td[1]"));
		}
		boolean isSorted = assertSort(id);
		System.out.println("是否排序成功="+isSorted);
		assertEquals(true, isSorted);
	}
	
	/**
	 * 判断是否排好序的方法
	 * @param ls
	 * @return
	 */
	private static boolean assertSort(List<String> ls)
	{
	 List<String> templs = new ArrayList<String>();
	 if(ls != null && ls.size() > 0){
	      for (int i = 0; i < ls.size(); i++) {
              templs.add(ls.get(i));
          }
		  Collections.sort(ls);
		  boolean notequal = false;
		  for(int i=0;i<ls.size();i++){
			  if(!(ls.get(i).equals(templs.get(i)))){
				  notequal = true;
			      break;
			  }
		  }
		  return notequal?false:true;
	 }else{
		 return true;	
	 }
	}
	
}
