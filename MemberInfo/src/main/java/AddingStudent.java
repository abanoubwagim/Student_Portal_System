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
 * Servlet implementation class AddingStudent
 */
@WebServlet("/AddingStudent")
public class AddingStudent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddingStudent() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        // Get session and check if the teacher is logged in
        
        HttpSession session = request.getSession();
        TeacherClass t = (TeacherClass) session.getAttribute("teacher");
        if (t == null) {
            response.sendRedirect("teacher.html");
            return;
        }
        

        // Get parameters
        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        String idD = request.getParameter("idD");

        PrintWriter pw = response.getWriter();
        Connection connect = null;
        PreparedStatement ps = null;
        String query = null;

        try {
            if (name.isEmpty() || pass.isEmpty() || email.isEmpty() || idD.isEmpty()) {
                pw.println("<html><body>");
                pw.println("<h3>Please provide all the required details.</h3>");
                pw.println("</body></html>");
                return;
            }

            
            connect = ConnectionToDb.connect();
            query = "SELECT email FROM student WHERE email = ?";
            ps = connect.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            
            
            if (rs.next()) {
                pw.println("<html><body>");
                pw.println("<h3>The student with this email already exists.</h3>"); 
                pw.println("</body></html>");
            } else {
            	
            	query = "SELECT Dep_Id FROM st_dep WHERE Dep_Id = ?";
            	ps = connect.prepareStatement(query);
            	ps.setString(1, idD);
            	rs = ps.executeQuery();
            	
               if(!rs.next()) {
            	   pw.println("<html><body>");
                   pw.println("<h3>The Department with this Id doesn't exists.</h3>"); 
                   pw.println("</body></html>");
               }else {
            	   query = "INSERT INTO student (Name_St, password, email, Dep_Sid) VALUES (?,?,?,?)";
                   ps = connect.prepareStatement(query);
                   ps.setString(1, name);
                   ps.setString(2, pass);
                   ps.setString(3, email);
                   ps.setString(4, idD);
                   

                   int out = ps.executeUpdate();
                   if (out == 1) {
                	   
                	   query="SELECT id FROM student WHERE Email=?";
                	   ps = connect.prepareStatement(query);
                	   ps.setString(1, email);
                	   rs = ps.executeQuery();
                	   if(rs.next()) {
                		   String idSt = rs.getString("id");
                		   query="INSERT INTO te_st VALUES (?,?)";
                    	   ps = connect.prepareStatement(query);
                    	   ps.setString(1, t.getId());
                    	   ps.setString(2, idSt);
                    	   
                    	   ps.executeUpdate();
                    	   
                    	   pw.println("<html><body>");
                           pw.println("<h3>Student added successfully.</h3>");
                           pw.println("</body></html>");
                    	   
                	   }
                	   
                	   
                       
                   } else {
                       pw.println("<html><body>");
                       pw.println("<h3>Unable to add student. Please try again later.</h3>");
                       pw.println("</body></html>");
                   }
               }
               }
                
        } catch (SQLException e) {
            pw.println("<html><body>");
            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
            pw.println("</body></html>");
        } finally {
            // Close resources
            try {
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
