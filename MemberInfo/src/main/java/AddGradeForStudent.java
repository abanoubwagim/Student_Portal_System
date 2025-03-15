

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddGradeForStudent
 */
@WebServlet("/AddGradeForStudent")
public class AddGradeForStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGradeForStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
    	
    	PrintWriter pw = response.getWriter();
    	response.setContentType("text/html;charset=UTF-8");

    	HttpSession session = request.getSession();
		
		
		if(session.getAttribute("teacher") == null ) {
			response.sendRedirect("teacher.html");
            return;
		}
		
		
		pw.println("<html><body>"
				+ "<form action='AddingGradeForStudent' method='POST' >"
				+ "Id:<input type= 'text' name='id' value='' /><br>"
				+ "<br><br>"
				+ "Grade:<input type= 'text' name='grade' value='' /><br>"
				+ "<br><br>"
				+ "<input type='submit' value='Apply'/>"
				+ "</form></body></html>");
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
