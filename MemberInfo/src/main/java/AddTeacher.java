

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddTeacher
 */
@WebServlet("/AddTeacher")
public class AddTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// add teacher in data base then shows The teahcer was added sucssfully button -> back to  
    	PrintWriter pw = response.getWriter();
    	response.setContentType("text/html");
    	
    	
    	 HttpSession session = request.getSession(false);
         if (session == null || session.getAttribute("manager") == null) {
             response.sendRedirect("manager.html"); // Redirect to manager login page
             return;
         }
         
    		pw.println("<html><body><form action='AddingTeacher' method='POST'>"
    				+ "Name:<input type='text' name='name' value='' />"
    				+ "<br><br>"
    				+ "Password:<input type='password' name='password' value='' />"
    				+ "<br><br>"
    				+ "Email:<input type='text' name='email' value='' />"
    				+ "<br><br>"
    				+ "Department:<input type='text' name='dep' value='' />"
    				+ "<br><br>"
    				+ "<a href='ViewDepartment'> View Department </a><br>"
    				+ "<br><br>"
    				+ "<input type='submit' value='Add Teacher' />"
    				+ "<br><br>"
    				+ "</form></html></body>");
    		
    		
    	
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
