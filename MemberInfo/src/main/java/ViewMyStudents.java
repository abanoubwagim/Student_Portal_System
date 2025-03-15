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
 * Servlet implementation class ViewMyStudents
 */
@WebServlet("/ViewMyStudents")
public class ViewMyStudents extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewMyStudents() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        
        // Get teacher session
        HttpSession session = request.getSession();
        TeacherClass t = (TeacherClass) session.getAttribute("teacher");
        
        if (t == null) {
            response.sendRedirect("teacher.html");
            return;
        }
        
        String teacherId = t.getId();
        Connection connect = null;
        PreparedStatement ps = null;
        String query = "";

        try {
            // SQL query to fetch students associated with the teacher
            query = "SELECT student.id, student.Name_St, st_dep.Department_St " +
                    "FROM student " +
                    "INNER JOIN st_dep ON student.Dep_Sid = st_dep.Dep_Id " +
                    "INNER JOIN te_st ON student.id = te_st.Id_St " +
                    "WHERE te_st.Id_Te = ?";
            
            connect = ConnectionToDb.connect();
            ps = connect.prepareStatement(query);
            ps.setString(1, teacherId);

            // Start of HTML output
            pw.println("<html><body><h2>List of Students</h2>");
            pw.println("<table border='1'>");

            // Table header
            pw.println("<thead>"
                    + "<tr>"
                    + "<th>Id</th>"
                    + "<th>Name</th>"
                    + "<th>Department</th>"
                    + "<th>Action</th>"
                    + "</tr>"
                    + "</thead>");

            ResultSet rs = ps.executeQuery();

            // Loop through the result set and display students
            while (rs.next()) {
                String studentId = rs.getString("id");
                String studentName = rs.getString("Name_St");
                String departmentName = rs.getString("Department_St");

                pw.println("<tbody>"
                        + "<tr>"
                        + "<td>" + studentId + "</td>"
                        + "<td>" + studentName + "</td>"
                        + "<td>" + departmentName + "</td>"
                        + "<td>"
                        + "<form action='RemoveMyStudent' method='POST'>"
                        + "<input type='hidden' name='studentId' value='" + studentId + "'>"
                        + "<input type='submit' value='Delete'>"
                        + "</form>"
                        + "</td>"
                        + "</tr>"
                        + "</tbody>");
            }

            pw.println("</table>");
            pw.println("</body></html>");
        } catch (Exception e) {
            pw.println("<html><body><h3>Error: " + e.getMessage() + "</h3></body></html>");
        } finally {
            // Ensure resources are closed
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
