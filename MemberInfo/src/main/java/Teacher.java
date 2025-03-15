

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Servlet implementation class Teacher
 */
@WebServlet("/Teacher")
public class Teacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Teacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		PrintWriter pw = response.getWriter();
	    response.setContentType("text/html");
		
		
		pw.println("<html><body>");
		
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		
		Connection connect = null;
		PreparedStatement ps = null;
		
		String query="SELECT *FROM teacher WHERE email=?";
		
		if(email == null || email.isEmpty() || pass == null || pass.isEmpty()) {
			pw.println("Please Fill All Fields.");
		}else{
			try {
				
				connect = ConnectionToDb.connect();
				ps = connect.prepareStatement(query);
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					if(pass.equals(rs.getString("password"))){
						
						TeacherClass t = new TeacherClass();
						t.setEmail(email);
						t.setPassword(pass);
						t.setId(rs.getString("id"));
						
						HttpSession session = request.getSession();
						session.setAttribute("teacher", t);
						
						pw.println("<a href='AddStudent'> Add Student</a><br>");
						pw.println("<br>");
						pw.println("<a href='ViewMyStudents'> View My Students </a><br>");
						pw.println("<br>");
						pw.println("<a href='AddGradeForStudent'> Add Grade For Student </a><br>");
						pw.println("<br>");
						
						
						pw.println("<form action='LogOutTeacher'><input type='submit' value='Sign Out'></form>");
					}else {
					pw.println("Invaild Email or password ");
					}
				}else 
				{
					pw.println("Invaild Email or password ");
					
				}
			}catch(Exception e ) {
				pw.println("<h3>"+e.getMessage()+"</h3>");
			}finally {
				try {
					connect.close();
					ps.close();
				} catch (SQLException e) {
					pw.println("<h3>"+e.getMessage()+"</h3>");
				}
				
			}
		}
		
		pw.println("</html></body>");	
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
