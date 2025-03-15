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
 * Servlet implementation class ViewMyTeacher
 */
@WebServlet("/ViewMyTeacher")
public class ViewMyTeacher extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewMyTeacher() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        // Get the student from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("student.html");
            return;
        }

        Connection connect = null;
        PreparedStatement ps = null;

        try {
        	StudentClass student = (StudentClass) session.getAttribute("student");
            
            String query = "SELECT teacher.Name_Te, te_dep.Department_Te " +
                           "FROM teacher " +
                           "JOIN te_dep ON teacher.Dep_Id = te_dep.Dep_Id " +
                           "JOIN te_st ON teacher.id = te_st.Id_Te " + 
                           "WHERE te_st.Id_St = ?";  

            connect = ConnectionToDb.connect();
            ps = connect.prepareStatement(query);
            ps.setString(1, student.getId()); 

            ResultSet rs = ps.executeQuery();

            // Starting the HTML table
            pw.println("<html><body><h2>My Teacher Information</h2>");
            pw.println("<table border='1'>");

            // Table header
            pw.println("<thead>"
                    + "<tr>"
                    + "<th>Teacher Name</th>"
                    + "<th>Department</th>"
                    + "</tr>"
                    + "</thead>");

            
            while (rs.next()) {
                pw.println("<tbody>"
                        + "<tr>"
                        + "<td>" + rs.getString("Name_Te") + "</td>"  // Corrected to get String value
                        + "<td>" + rs.getString("Department_Te") + "</td>"
                        + "</tr>"
                        + "</tbody>");
            }

            pw.println("</table>");
            
            pw.println("</body></html>");
        } catch (Exception e) {
            pw.println("<html><body><h3>Error retrieving data. Please try again later.</h3></body></html>");
        } finally {
            try {
                if (ps != null) ps.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                pw.println("<h3>Error closing resources: " + e.getMessage() + "</h3>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
