

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

/**
 * Servlet implementation class MyGrade
 */
@WebServlet("/MyGrade")
public class MyGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyGrade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student.html");
            return;
        }
        
        StudentClass student = (StudentClass) session.getAttribute("student");
		
		Connection connect = null;
		PreparedStatement ps = null;
		
		pw.println("<html><body>");
		
    	try {
    		String query = "SELECT grade FROM student WHERE id = ?";
    		
    		connect = ConnectionToDb.connect();
    		ps = connect.prepareStatement(query);
    		ps.setString(1, student.getId());
    		ResultSet rs = ps.executeQuery();
    		rs.next();
    		
    		String grade = rs.getString("grade");
    		if(grade == null) {
    			pw.println("<h3>Your Grade doesn't exist</h3>");
    			pw.println("<br><br>");
    			
    		}else {
    			pw.println("<h3>Your Grade is : "+grade+"</h3>");
    			pw.println("<br><br>");
    		}
    	
    	}catch(Exception e ) {
    		pw.println("<h3>"+e.getMessage()+"</h3>");
    	}
    	
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
