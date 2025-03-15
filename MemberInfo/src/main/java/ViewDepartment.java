

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class ViewDepartment
 */
@WebServlet("/ViewDepartment")
public class ViewDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDepartment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	PrintWriter pw = response.getWriter();
    	
    	Connection connect = null;
    	PreparedStatement ps = null;
    	String query = null;
    	
    	
    	try {
    		query="SELECT Dep_Id, Department_Te FROM te_Dep";
    		connect = ConnectionToDb.connect();
    		ps = connect.prepareStatement(query);
    		ResultSet rs = ps.executeQuery();
    		
    		pw.println("<html><body><table border='1'>");
			
    		// Table Header
    	    pw.println("<thead>"
    	            + "<tr>"
    	            + "<th> Dep_Id </th>"
    	            + "<th> Department </th>"
    	            + "</tr>"
    	            + "</thead>");
    	    
    		while(rs.next()) {
    			
    			pw.println("<tbody>"
						+ "<tr>"
						+ "<td>"+rs.getString("Dep_Id")+"</td>"
						+ "<td>"+rs.getString("Department_Te")+"</td>"
						+ "</tr>"
						+ "</tbody>");
    			
    		}
    		
    	}catch(Exception e ) {
    		pw.println("<h3>"+e.getMessage()+"</h3>");
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
