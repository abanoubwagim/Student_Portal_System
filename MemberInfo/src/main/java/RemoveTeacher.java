

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class RemoveTeacher
 */
@WebServlet("/RemoveTeacher")
public class RemoveTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("manager.html"); // Redirect to login page if manager is not logged in
            return;
        }
		
    	PrintWriter pw = response.getWriter();
    	response.setContentType("text/html");
    	
		
    	pw.println("<html><body>"
    			+ "<form action='RemovingTeacher' method='POST'>"
    			+ "Id's Teacher: <input type='text' name='id' value=''/><br>"
    			+ "<input type= 'submit' value='Delete'>"
    			+ "</form>"
    			+ "<br>"
    			+ "</html>");
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
