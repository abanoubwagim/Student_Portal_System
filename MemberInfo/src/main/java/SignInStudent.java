

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
 * Servlet implementation class SignInStudent
 */
@WebServlet("/SignInStudent")
public class SignInStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	PrintWriter pw = response.getWriter();
    	String email = request.getParameter("email");
    	String pass = request.getParameter("password");
    	
    	
    	Connection connect= null;
    	PreparedStatement ps = null;
    	String query = null;
    	
    	pw.println("<html><body>");
    	
    	try {
    			query = "SELECT email,password FROM student WHERE email=?";
    			connect = ConnectionToDb.connect();
    			ps = connect.prepareStatement(query);
    			
    			ps.setString(1, email);
    			
    			ResultSet rs = ps.executeQuery();
    			
    			if(rs.next()) {
    				
    				if(rs.getString("password").equals(pass)) {
    					
    					query= "SELECT id FROM student WHERE email =? ";
						ps = connect.prepareStatement(query);
						ps.setString(1, email);
						
						rs = ps.executeQuery();
						rs.next();
						String id = rs.getString("id");
						
						StudentClass s = new StudentClass();
    					s.setId(id);
    					s.setEmail(email);
						s.setPassword(pass);
						
						HttpSession session = request.getSession();
						session.setAttribute("student", s);
						
						
						pw.println("<form action ='student.html' method='POST'>"
    							+ "<a href='ViewMyTeacher'>My Teachers</a>"
    							+ "<br><br>"
    							+ "<a href='MyGrade'>My Grade</a>"
    							+ "<br><br>"
    							+ "<input type= 'submit' value='Log Out' />"
    							+ "</form>");
    					
    				}else {
    					pw.println("<h3>Email or password is invaild.</h3>");
    				}
    			}else {
    				pw.println("<h3>The Student Doesn't exist</h3>");
    			}
    		
    	}catch(Exception e ) 
    	{
    		pw.println("<h3>Something went wrong. Please try again later.</h3>");
    	}finally {
    		try {
				connect.close();
				ps.close();
			} catch (SQLException e) {
				pw.println("<h3>"+e.getMessage()+"</h3>");
			}
    		
    	}
    	
    
    	pw.println("</body></html>");
    	
    	
    	
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
