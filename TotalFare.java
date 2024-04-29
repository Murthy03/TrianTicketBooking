package TTBooking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TotalFare
 */
@WebServlet("/TotalFare")
public class TotalFare extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBConn dc;

	public TotalFare() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<List<String>> pdata = new ArrayList<>();
		// TODO Auto-generated method stub
		String departure = request.getParameter("departure");
		String destination = request.getParameter("destination");
		String train = request.getParameter("train");
		String date = request.getParameter("date");
		String travelClass = request.getParameter("class");
		String len = request.getParameter("numRows");
		for (int i = 1; i <= Integer.parseInt(len) - 1; i++) {
			List<String> p = new ArrayList<>();
			p.add(request.getParameter("pname_" + i + ""));
			p.add(request.getParameter("page_" + i + ""));
			p.add(request.getParameter("gender_" + i + ""));
			p.add(request.getParameter("bpref_" + i + ""));
			pdata.add(p);
		}
		double totalFare = 0;
		double baseFare = 0;
		try {
			dc = new DBConn();
			baseFare = dc.getFare(departure, destination);
			totalFare = dc.getFare(departure, destination) * (Integer.parseInt(len) - 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		request.setAttribute("departure", departure);
		request.setAttribute("destination", destination);
		request.setAttribute("train", train);
		request.setAttribute("date", date);
		request.setAttribute("travelClass", travelClass);
		request.setAttribute("pdata", pdata);
		request.setAttribute("baseFare", baseFare);
		request.setAttribute("totalFare", totalFare);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ticket.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
