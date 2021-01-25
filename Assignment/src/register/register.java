package register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public register() {
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
		out.println("</head><body>");
		String dbURL = "jdbc:mysql://localhost/fotobooth";
		
		String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

		String password = request.getParameter("password");
		System.out.println(password);

		String confirm = request.getParameter("confirm");
		System.out.println(confirm);
		
		if (password.equals(confirm)) {
			try {
				String name = request.getParameter("name");
				
				
				String email = request.getParameter("email");
				
				Connection connection = DriverManager.getConnection(dbURL, "root", "Sharan123#");
				
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, password);

				pstmt.executeUpdate();

				out.println("<p> You are now registered. Please login. </p> <br>");
				response.sendRedirect("login.html");
				
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			out.println("<p>Passwords do not match. Please try again.</p> <br>");
		}

		

		out.println("</body></html>");
		out.close();
	}

}