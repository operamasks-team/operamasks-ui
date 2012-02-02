package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.operamasks.data.Ip;
import org.operamasks.data.IpService;
import org.operamasks.model.GridDataModel;

public class OmGridTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1987495630722171190L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		String method = request.getParameter("method");
		String testOption = request.getParameter("testOption");
		
		if ("fast".equals(method)) {
			fast(request, response);
		} else if ("lazy".equals(method)) {
			lazy(request, response);
		} else if ("lazyTotal".equals(method)) {
			lazyTotal(request, response);
		} 
		
		if ("block".equals(testOption)) {
			block(request, response);
		} else if ("emptyMsg".equals(testOption)){
			emptyMsg(request, response);
		} else if ("loadingMsg".equals(testOption)) {
			loadingMsg(request, response);
		} else if ("query".equals(testOption)) {
			query(request, response);
		} else if ("qtype".equals(testOption)) {
			qtype(request, response);
		} else if ("url".equals(testOption)) {
			url(request,response);
		} else if ("method".equals(testOption)) {
			method(request, response);
		} else if ("blockOpacity".equals(testOption)) {
			blockOpacity(request, response);
		} else if ("fillEmptyRows".equals(testOption)) {
			fillEmptyRows(request, response);
		} else if ("lazyTotalUrl".equals(testOption)) {
			lazyTotalUrl(request, response);
		} else if ("loadMask".equals(testOption)) {
			loadMask(request, response);
		}
	}



	private void loadMask(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fast(request, response);
		
	}



	private void lazyTotalUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		writer.write("{\"total\":12358}");
	}



	private void blockOpacity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fast(request, response);
	}



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void block(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		fast(request, response);
	}

	protected void fast(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		
		
		int start = Integer.parseInt(startStr);
		int limit = Integer.parseInt(limitStr);

		PrintWriter writer = response.getWriter();
		

		GridDataModel<Ip> model = new GridDataModel<Ip>();
		int end = start + limit;
		int total = IpService.ipInfos.size();
		end = end > total ? total : end;
		if (start <= end) {
			model.setRows(IpService.ipInfos.subList(start, end));
		}
		model.setTotal(total);
		writer.write(JSONObject.fromObject(model).toString());

	}
	
	protected void loadingMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			Thread.sleep(2000);//睡眠5秒钟
		} catch (Exception e) {
		}
		fast(request , response);
		
	}

	protected void lazy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");

		int start = Integer.parseInt(startStr);
		int limit = Integer.parseInt(limitStr);

		PrintWriter writer = response.getWriter();

		GridDataModel<Ip> model = new GridDataModel<Ip>();
		int end = start + limit;
		// 由用户提供当前页显示的数据，并设置当前页码
		// 用户此时无法预知总数目，应该根据start和limit查找相应的数据
		// begin
		int total = IpService.ipInfos.size();
		end = end > total ? total : end;
		// 若start已经大于end，则用户可以进行显示完整的total信息
		if (start > end) {
			model.setTotal(total);
		}
		for (; start > end;) {
			start -= limit;
		}
		model.setRows(IpService.ipInfos.subList(start, end));
		writer.write(JSONObject.fromObject(model).toString());
	}

	protected void lazyTotal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {
			Thread.sleep(5000);// 模拟求大概总数要很长时间
		} catch (Exception e) {
			// do nothing
		}
		int total = IpService.ipInfos.size();

		String json = String.format("{\"total\": %d}", total);

		writer.write(json);

	}
	
	protected void emptyMsg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		renderText(request, response, null);
	}
	
	//test option 'query' 
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String q = request.getParameter("query");
		renderText(request, response, q);
	}
	
	//test option  'qtype'
	protected void qtype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String q = request.getParameter("qtype");
		renderText(request, response, q);
	}
	
	//test option 'url'
	protected void url(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String text = "DataFromUrlMethod"; 
		renderText(request, response, text);
	}
	
	//test option 'method'
	private void method(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String text = request.getMethod();
		renderText(request, response, text);
	}
	
	//test option 'fillEmptyRows'
	private void fillEmptyRows(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		renderText(request, response, "TheOnlyRow");
	}


	private void renderText(HttpServletRequest request, HttpServletResponse response, String text) throws IOException {
		GridDataModel<Ip> model = new GridDataModel<Ip>();
		model.setTotal(text == null ? 0: 1);
		List<Ip> rows = new ArrayList<Ip>();
		Ip row = new Ip((int)(Math.random() * 1000),text,text,text,text);
		rows.add(text == null ? null : row);
		model.setRows(rows);
		
		PrintWriter writer = response.getWriter();
		writer.write(JSONObject.fromObject(model).toString());
	}
		
	
	

}
