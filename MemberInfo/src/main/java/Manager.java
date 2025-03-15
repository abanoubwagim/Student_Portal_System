

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
 * Servlet implementation class Manager
 */
@WebServlet("/Manager")
public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// check the manger exist or not if yes can go to add teacher or go to  managing.html button -> sign out -> manager.html
    	PrintWriter pw = response.getWriter();
    	
    	pw.println("<html><body>");
    	
    	String email = request.getParameter("email");
    	String pass = request.getParameter("password");
    	
    	
    	Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    	if(email.isEmpty() || pass.isEmpty()) {
    		pw.println("Please Fill All Fields.");
    	}else{
    		try {
    			
            String query="SELECT email, password FROM manager WHERE email=?";
                        
        	connect = ConnectionToDb.connect();
        		ps = connect.prepareStatement(query);
        		ps.setString(1, email);
        		rs = ps.executeQuery();
        		if(rs.next()) {
        			if(pass.equals(rs.getString("password"))){
        				ManagerClass m = new ManagerClass();
        				m.setEmail(email);
        				
                        HttpSession session = request.getSession();
        				session.setAttribute("manager", m);
        				session.getAttribute("manager");
        				
        				pw.println("<a href='AddTeacher'> Add Teacher</a><br>");
        				pw.println("<br>");
        				pw.println("<a href='RemoveTeacher'> Remove Teacher</a><br>");
        				pw.println("<br>");
        				pw.println("<a href='AddDepartment'> Add Department </a><br>");
        				pw.println("<br>");
        				pw.println("<a href='ViewAllMember'> View All Techer and Student </a><br>");
        				pw.println("<br>");
        				pw.println("<form action='LogOutManager'><input type='submit' value='Sign Out'></form>");
        			
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
                        rs.close();
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
