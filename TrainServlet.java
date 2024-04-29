package TTBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TrainServlet
 */
@WebServlet("/TrainServlet")
public class TrainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<TrainDetails> td;
	DBConn dc;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dept = request.getParameter("p1");
		String dest = request.getParameter("p2");
		try {
			dc = new DBConn();
			td = dc.getTrainDetails(dept, dest);
			ArrayList<String> res = new ArrayList<>();
			for (TrainDetails t : td) {
				res.add(t.getTrain_no() + "                           " + t.getTrain_name());
			}

			String tn = String.join(",", res);
			response.setContentType("text");
			response.getWriter().print(tn);
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

}
