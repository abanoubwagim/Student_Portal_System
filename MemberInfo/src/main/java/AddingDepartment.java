

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
 * Servlet implementation class AddingDepartment
 */
@WebServlet("/AddingDepartment")
public class AddingDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingDepartment() {
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
    	
    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("manager.html"); // Redirect to login page if not logged in
            return;
        }
    	
    	
    	
    	
    	
    	pw.println("<html><body>");
    	
    	Connection connect = null;
    	PreparedStatement ps = null;
    	String query= null ;
    	
    	
    	String departmentName = request.getParameter("name");
        if (departmentName == null || departmentName.trim().isEmpty()) {
            request.setAttribute("message", "Please enter a department name.");
            return;
        }
    	
    	else {
    		connect = ConnectionToDb.connect();
    		try{
        		query="SELECT Department_Te FROM te_dep WHERE Department_Te =? ";
        		ps = connect.prepareStatement(query);
        		ps.setString(1, departmentName);
        		ResultSet rs = ps.executeQuery();
        		if(rs.next()) {
        			pw.println("<h3>The department already exists.</h3>");
        		}else {
        			try{
                		query="INSERT INTO te_dep (Department_Te) VALUES (?)";
                		ps = connect.prepareStatement(query);
                		
                		ps.setString(1, departmentName);
                		
                		
                		
                		int out = ps.executeUpdate();
                		if(out == 1 ) {
                			pw.println("<h3>The department was added successfully.</h3>");
                			
                		}else {
                			pw.println("<h3>Failed to add department. Please try again.</h3>");
                		}
                		
                	}catch(Exception e) {
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
        		}catch(Exception e) {
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
