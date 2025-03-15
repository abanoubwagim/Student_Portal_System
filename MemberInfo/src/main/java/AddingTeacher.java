

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
 * Servlet implementation class AddingTeacher
 */
@WebServlet("/AddingTeacher")
public class AddingTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
    	if(session == null || session.getAttribute("manager") == null ) {
    		 response.sendRedirect("manager.html"); // Redirect to manager login page
             return;
    	}
		
		
    	PrintWriter pw = response.getWriter();
    	
    	String name = request.getParameter("name");
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String dep = request.getParameter("dep");
    	
    	
    	
    	pw.println("<html><body>");
    	
    	Connection connect = null;
    	PreparedStatement ps = null;
    	String query= null ;
    	
    	
    	if(name.isEmpty() || email.isEmpty() || password.isEmpty() || dep.isEmpty()) {
    		pw.println("<h3>Please fill All fields.</h3>");
    	}
    	
    	else {
    		connect = ConnectionToDb.connect();
    		try{
        		query="SELECT email FROM teacher WHERE email =? ";
        		ps = connect.prepareStatement(query);
        		ps.setString(1, email);
        		ResultSet rs = ps.executeQuery();
        		if(rs.next()) {
        			pw.println("<h3>The Teacher is Exists</h3>");
        		}else {
        			try{
                		query="INSERT INTO teacher (Name_Te,email,password,Dep_Id) VALUES (?,?,?,?)";
                		ps = connect.prepareStatement(query);
                		
                		ps.setString(1, name);
                		ps.setString(2, email);
                		ps.setString(3, password);
                		ps.setString(4, dep);
                		
                		
                		
                		int out = ps.executeUpdate();
                		if(out == 1 ) {
                			pw.println("<h3>The Teacher was Adding Successfully.</h3>");
                			
                		}else {
                			pw.println("<h3>You cannot Add Teacher now, Please try Again.</h3>");
                		}
                		
                	}catch(Exception e) {
                		pw.println("<h3> There is no id's department</h3>");
                	}finally {
                		try {
            				connect.close();
            				ps.close();
            			} catch (SQLException e) {
            				pw.println("<h3>"+e.getMessage()+"</h3>");
            			}
                		
                	}
        		}}catch(Exception e) {
        		pw.println("<h3>"+e.getMessage()+"</h3>");
        	}finally {
        		try {
    				connect.close();
    				ps.close();
    			} catch (SQLException e) {
    				pw.println("<h3>"+e.getMessage()+"</h3>");
    			}
        		
        	}
    		
    	}pw.println("</html></body>");
		
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
