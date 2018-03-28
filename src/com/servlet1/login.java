
#package com.servlet1;
import javax.servlet.http.*;
import java.io.*;

public class login extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res) {
		try {
			
			res.setContentType("text/html;charset=utf-8");
			PrintWriter pw = res.getWriter();
			
			pw.println("<img src=imgs/index.jpg><br>");
			
			pw.println("<html>");
			pw.println("<body>");
			String info=req.getParameter("info");
			
			if(info !=null) {
				pw.println("<h1>你的用户名或者密码错误</h1>");
			}
			pw.println("<h1>登陆界面</h1>");
			pw.println("<form action=logincheck method=post>");
			pw.println("用户名：<input type=text name=username><br>");
			pw.println("密码：<input type=password name=passwd><br>");
			pw.println("input：<input type=submit value=login><br>");
			pw.println("</form>");
			pw.println("</body>");
			pw.println("</html>");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req,HttpServletResponse res) {
		this.doGet(req, res);
	}

}

