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
 * Servlet implementation class ViewAllMember
 */
@WebServlet("/ViewAllMember")
public class ViewAllMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAllMember() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if manager is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manager") == null) {
            response.sendRedirect("manager.html"); // Redirect to login page if not logged in
            return;
        }

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = null;

        try {
            
            query = "SELECT teacher.Name_Te, te_dep.Department_Te, student.Name_St, st_dep.Department_St "
                    + "FROM Teacher "
                    + "INNER JOIN te_st ON Teacher.id = te_st.Id_Te "
                    + "INNER JOIN student ON student.id = te_st.Id_St "
                    + "INNER JOIN te_dep ON teacher.Dep_Id = te_dep.Dep_Id "
                    + "INNER JOIN st_dep ON student.Dep_Sid = st_dep.Dep_Id";

            // HTML output
            pw.println("<html><body><h2>All Members Information</h2>"
                    + "<table border='1'>");

            // Table Header
            pw.println("<thead>"
                    + "<tr>"
                    + "<th>Teacher's Name</th>"
                    + "<th>Teacher's Department</th>"
                    + "<th>Student's Name</th>"
                    + "<th>Student's Department</th>"
                    + "</tr>"
                    + "</thead>"
                    + "<tbody>");

            // Establish connection and execute query
            connect = ConnectionToDb.connect();
            ps = connect.prepareStatement(query);
            rs = ps.executeQuery();

            // Display results in table rows
            while (rs.next()) {
                pw.println("<tr>"
                        + "<td>" + rs.getString("Name_Te") + "</td>"
                        + "<td>" + rs.getString("Department_Te") + "</td>"
                        + "<td>" + rs.getString("Name_St") + "</td>"
                        + "<td>" + rs.getString("Department_St") + "</td>"
                        + "</tr>");
            }

            pw.println("</tbody></table>");
        } catch (Exception e) {
            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                pw.println("<h3>Error closing resources: " + e.getMessage() + "</h3>");
            }
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
