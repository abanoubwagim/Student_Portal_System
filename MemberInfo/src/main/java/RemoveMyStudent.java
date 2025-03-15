

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class RemoveMyStudent
 */
@WebServlet("/RemoveMyStudent")
public class RemoveMyStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveMyStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		// add id's student 
		
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("teacher") == null) {
			response.sendRedirect("teacher.html");
			return;
		}
		
		pw.println("<html><body>"
				+ "<form action='RemovingStudent' method='POST'>"
				+ "Id's Student: <input type='text' name='id' value=''/><br>"
				+ "<input type= 'submit' value='Delete'>"
				+ "</body>"
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
