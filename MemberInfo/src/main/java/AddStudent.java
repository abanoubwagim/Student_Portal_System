

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddStudent
 */
@WebServlet("/AddStudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	response.setContentType("text/html");
    	PrintWriter pw = response.getWriter();
    	HttpSession session = request.getSession();
    	
    	
    	if (session.getAttribute("teacher") == null) {
    		response.sendRedirect("teacher.html");
            return;
        }
    	
		
		
		pw.println("<html><body><form action='AddingStudent' method='POST'>"
				+ "Name:<input type='text' name='name' value='' />"
				+ "<br><br>"
				+ "Password:<input type='password' name='password' value='' />"
				+ "<br><br>"
				+ "Email:<input type='text' name='email' value='' />"
				+ "<br><br>"
				+ "Department's Id :<input type='text' name='idD' value='' />"
				+ "<br><br>"
				+ "<a href='ViewDepartmentForStudent'> View Department </a><br>"
				+ "<br><br>"
				+ "<input type='submit' value='Add Student' />"
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
