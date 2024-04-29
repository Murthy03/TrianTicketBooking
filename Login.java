package TTBooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBConn dc;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uORg = request.getParameter("username");
		String pswd = request.getParameter("password");
		HttpSession obj = request.getSession();
		obj.setAttribute("user", uORg);
		dc = new DBConn();
		String res = "";
		try {
			res = dc.UserVerfication(uORg, pswd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res.equals("pswdCoreect")) {
			response.sendRedirect("TicketBooking.html");
		} else if (res.equals("pswdWrong")) {
			String errorMessage = "Invalid password!";
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + errorMessage + "');");
			out.println("window.location.href='login.html';");
			out.println("</script>");
		} else {
			String errorMessage = "Invalid User";
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('" + errorMessage + "');");
			out.println("window.location.href='login.html';");
			out.println("</script>");
		}
	}
}
