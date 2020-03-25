package cn.tedu.store.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;

public class HtmlAccessFilter implements Filter{
	private List<String> whileList = new ArrayList<String>();
			
	public void init(FilterConfig filterConfig) throws ServletException {
		whileList.add("login.html");
		whileList.add("register.html");
		whileList.add("footerTemplate.html");
		whileList.add("leftTemplate.html");
		whileList.add("topTemplate.html");
	}
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String pathUri = req.getRequestURI();
		String fileNmae = pathUri.substring(pathUri.lastIndexOf("/")+1);
		System.out.println(fileNmae);
		
		if(whileList.contains(fileNmae)) {
			chain.doFilter(request,response);
			return;
		}
		HttpSession session = req.getSession();
		System.out.println(session);
		Object id = session.getAttribute("uid");
		if(id!=null) {
			chain.doFilter(request,response);
			return;
		}
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.sendRedirect("../web/login.html");
		
		
	}

	

}
