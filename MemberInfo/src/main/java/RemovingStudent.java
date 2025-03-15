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

@WebServlet("/RemovingStudent")
public class RemovingStudent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RemovingStudent() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        
        HttpSession session = request.getSession();
        TeacherClass t = (TeacherClass) session.getAttribute("teacher");
        
        if(t == null) {
            response.sendRedirect("teacher.html");
            return;
        }
        
        String id = request.getParameter("id");
        String idTech = t.getId();
        
        if (id == null || id.isEmpty()) {
            pw.println("<html><body>");
            pw.println("<h3>Please provide a valid student ID to remove.</h3>");
            pw.println("</body></html>");
            return;
        }

        Connection connect = null;
        PreparedStatement ps = null;
        String query = null;
        ResultSet rs = null;

        try {
            connect = ConnectionToDb.connect();
            
            // Step 1: Check if the student exists in the 'student' table
            query = "SELECT id FROM student WHERE id = ?";
            ps = connect.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // Step 2: Check if the student exists in the 'te_st' (Teacher-Student) table
                query = "SELECT Id_St FROM te_st WHERE Id_St = ? AND Id_Te = ?";
                ps = connect.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, idTech);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    // If the student is linked to the teacher, delete the relationship first
                    query = "DELETE FROM te_st WHERE Id_St = ? AND Id_Te = ?";
                    ps = connect.prepareStatement(query);
                    ps.setString(1, id);
                    ps.setString(2, idTech);
                    ps.executeUpdate();
                    
                    // Step 3: Now delete the student from the 'student' table
                    query = "DELETE FROM student WHERE id = ?";
                    ps = connect.prepareStatement(query);
                    ps.setString(1, id);
                    int out = ps.executeUpdate();

                    if (out == 1) {
                        pw.println("<html><body>");
                        pw.println("<h3>Student deleted successfully.</h3>");
                    } else {
                        pw.println("<html><body>");
                        pw.println("<h3>Error: Unable to delete student.</h3>");
                        pw.println("</body></html>");
                    }
                } else {
                    pw.println("<html><body>");
                    pw.println("<h3>This student is not assigned to you.</h3>");
                    pw.println("</body></html>");
                }
            } else {
                pw.println("<html><body>");
                pw.println("<h3>Error: Student not found.</h3>");
                pw.println("</body></html>");
            }
        } catch (SQLException e) {
            pw.println("<html><body>");
            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
            pw.println("</body></html>");
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                pw.println("<html><body>");
                pw.println("<h3>Error closing resources: " + e.getMessage() + "</h3>");
                pw.println("</body></html>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
