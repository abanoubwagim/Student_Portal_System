

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
 * Servlet implementation class RemovingTeacher
 */
@WebServlet("/RemovingTeacher")
public class RemovingTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovingTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// if the teacher exists delete it , not the teacher doesn't exist  button -> back -> removeTeacher.html 
    	
    	HttpSession session = request.getSession(false);  
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("manager.html"); // Redirect to login page if manager is not logged in
            return;
        }
		
    	PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
    	 
    	 
    	pw.println("<html><body>");
    	
		String id = request.getParameter("id");
		
		Connection connect = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String query = null;
		String query1 = null;
		
		 if(id == null || id.isEmpty()) {
			 pw.println("<h3>Please provide a Teacher ID.</h3>");
		 }else {
			 try {
		    		query = "SELECT id FROM teacher WHERE id = ?";
		    		connect = ConnectionToDb.connect();
		    		ps = connect.prepareStatement(query);
		    		
		    		ps.setString(1, id);
		    		
		    		ResultSet rs = ps.executeQuery();
		    		if (rs.next()) {
		    			
		    			query = "DELETE FROM teacher WHERE id = ?";
						ps = connect.prepareStatement(query);
						ps.setString(1, id);
						
						query1 = "SELECT Id_Te FROM te_st WHERE Id_Te = ?";
						
				        ps1 = connect.prepareStatement(query1);
						ps1.setString(1, id);
						
						rs = ps1.executeQuery();
						
						while(rs.next()) {
							query1 = "DELETE FROM te_st WHERE ID_Te = ?";
			    			ps1 = connect.prepareStatement(query1);
			    			ps1.setString(1, id);
			    			ps1.executeUpdate();
						}
						
						
						int out = ps.executeUpdate();
						if(out == 1 ) {
							pw.println("<h3>The Teacher deleted sucessfully. </h3>");
						}else {
							pw.println("<h3>The Teacher doesn't exist.</h3>");
						}
		    			
					}else {
						pw.println("<h3>The Teacher doesn't exist.</h3>");
					}
			 }catch(Exception e ) {
				 pw.println("<h3>"+e.getMessage()+"</h3>");
			 }finally {
	        		try {
						if(connect != null )connect.close();
						if(ps != null ) ps.close();
					} catch (SQLException e) {
						pw.println("<h3>"+e.getMessage()+"</h3>");
					}
	        		
	        	}
		 }
		 pw.println("</html></body>");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
