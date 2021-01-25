package login;
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
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
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
		out.println("<title> Register</title>");
		out.println("</head><body><h1>You are now registered. Please login </h1>");
		String dbURL = "jdbc:mysql://localhost/fotobooth";

		String query = "SELECT password FROM users where email = ? AND password = ?;";

		String username = request.getParameter("username");

		String password = request.getParameter("password");
		

		try {

			Connection connection = DriverManager.getConnection(dbURL, "root", "Sharan123#");
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				out.println("<p> Welcome to Photobooth! </p> <br>");
				response.sendRedirect("profilePage.html");
			} else {
				out.println("<p> Username or Password is incorrect. Please try again. </p> <br>");
				response.sendRedirect("login.html");
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</body></html>");
		out.close();
	}

}