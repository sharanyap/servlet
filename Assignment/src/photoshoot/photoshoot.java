package photoshoot;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class photoshoot
 */
@WebServlet("/photoshoot")
public class photoshoot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public photoshoot() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<title> Your Photographer</title>");
		out.println("</head><body><h1>Your Photographer is: </h1>");
		String dbURL = "jdbc:mysql://localhost/fotobooth";
		String query = "SELECT * FROM photographers,available WHERE photographers.id = timeId AND available.month =? AND available.day = ? AND available.status = 'Open'";

		String month_r = request.getParameter("month");

		String day_r = request.getParameter("day");

		try {
			Connection connection = DriverManager.getConnection(dbURL, "root", "Sharan123#");
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, month_r);
			pstmt.setString(2, day_r);

			ResultSet rs = pstmt.executeQuery();

			out.println("<p> Based on your availability, the photographer who will work with you is: </p> <br>");
			if (rs.next()) {	
				
					String insert = "INSERT INTO requests (name,photo_type, quantity, month, day, userId, availableId) VALUES (?, ?, ?, ?, ?, ?, ?)";
					String name_r = request.getParameter("name");
					
					String type_r = request.getParameter("photo_type");
					

					String quantity_r = request.getParameter("quantity");
					

					String email_r = request.getParameter("email");
					
					String userId_str = "SELECT id FROM users WHERE email=?";
					PreparedStatement pstmtuser = connection.prepareStatement(userId_str);
					pstmtuser.setString(1, email_r);
					ResultSet rs_user = pstmtuser.executeQuery();
					rs_user.next();
					int userId = rs_user.getInt(1);
					
					
					String availableId_str = "SELECT id FROM available WHERE month=? AND day=? limit 1";
					PreparedStatement pstmtavail = connection.prepareStatement(availableId_str);
					pstmtavail.setString(1, month_r);
					pstmtavail.setString(2, day_r);
					ResultSet rs_avail = pstmtavail.executeQuery();
					rs_avail.next();
					int availableId = rs_avail.getInt(1);
					
					
					PreparedStatement pstmt2 = connection.prepareStatement(insert);
					pstmt2.setString(1, name_r);
					pstmt2.setString(2, type_r);
					pstmt2.setString(3, quantity_r);
					pstmt2.setString(4, month_r);
					pstmt2.setString(5, day_r);
					pstmt2.setInt(6, userId);
					pstmt2.setInt(7, availableId);

					pstmt2.executeUpdate();

					out.println("<p>" + rs.getString(2) + "</p>");
					out.println("<br> You can contact him at: " + rs.getString(3) + "<br>");
					
					
					String deleteq = "UPDATE available SET status = 'Closed' WHERE id=?";
					PreparedStatement pd = connection.prepareStatement(deleteq);
					pd.setInt(1, availableId);
					pd.executeUpdate();
				
			} else {
				String display = "SELECT * FROM photographers, available where photographers.id = timeId AND available.status = 'Open'";
				PreparedStatement pstmt3 = connection.prepareStatement(display);
				ResultSet rs1 = pstmt3.executeQuery();
				out.println(
						"Sorry no available photographers found! Available times for the photographers are as follows: <br>");
				while (rs1.next()) {
					out.println("<p> Name: " + rs1.getString(2) + "| Email: " + rs1.getString(3) + "| Day: "
							+ rs1.getString(6) + " " + rs1.getString(7) + "</p> <br>");
				}
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</body></html>");
		out.close();
	}

}
