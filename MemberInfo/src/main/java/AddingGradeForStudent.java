

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
 * Servlet implementation class AddingGradeForStudent
 */
@WebServlet("/AddingGradeForStudent")
public class AddingGradeForStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingGradeForStudent() {
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
		TeacherClass t = (TeacherClass) session.getAttribute("teacher");
    	
    	
        if(t  == null) {
        	response.sendRedirect("teacher.html");
        	return;
        }
		
		
    	
    	String id = request.getParameter("id");
    	String idTec = t.getId();
    	String grade = request.getParameter("grade");
		
		Connection connect = null;
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		if(id.isEmpty() || grade.isEmpty()) {
			pw.println("<h3>Please fill all fields</h3>");
			
		}else {
		
		try {
			query= "SELECT id FROM student WHERE id = ?";
			connect = ConnectionToDb.connect();
			ps = connect.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				try {
					query="SELECT *FROM te_st WHERE Id_Te = ? AND Id_St = ?";
					ps = connect.prepareStatement(query);
					ps.setString(1, idTec);
					ps.setString(2, id);
					
					rs = ps.executeQuery();
					
					if(rs.next()) {
						query= "UPDATE student set Grade = ? WHERE id = ?";
						ps = connect.prepareStatement(query);
						ps.setString(1, grade);
						ps.setString(2, id);
						int out = ps.executeUpdate();
						if(out == 1 ) {
							pw.print("<h3>Updated grade.</h3>");
						}else {
							pw.print("<h3>Can't update grade.</h3>");
						}

					}else {
						pw.print("<h3>The Student doesn't with you </h3>");
					}
					
				}catch(Exception e) {
					pw.print("<h3>"+e.getMessage()+"</h3>");
				}
			}else {
				pw.println("<h3>The Student doesn't exist.</h3>");
			}
			
		}catch(Exception e) {
			pw.print("<h3>"+e.getMessage()+"</h3>");
		}finally {
			try {
				connect.close();
				ps.close();
			} catch (SQLException e) {
				pw.print("<h3>"+e.getMessage()+"</h3>");
			}
			
		}
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
