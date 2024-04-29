package TTBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AllStationServlet")
public class AllStationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> snames;
	DBConn dc;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> snames = new ArrayList<>();
		try {
			dc = new DBConn();
			snames = dc.getStationNames();
			String textResponse = String.join(",", snames);
			response.setContentType("text/plain");
			response.getWriter().print(textResponse);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

}
