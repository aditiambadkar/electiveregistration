import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class crudServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        Database db = new Database();
        db.getConnection();
        
        if(request.getParameter("submitInsert") != null) {
            
            String name = request.getParameter("nameInsert");  
            int rollNo = Integer.parseInt(request.getParameter("rollNoInsert"));  
            String gender = request.getParameter("genderInsert");  
            int contactNum = Integer.parseInt(request.getParameter("numInsert"));
            String email = request.getParameter("emailInsert");
            String elective1 = request.getParameter("elective1Insert");
            String elective2 = request.getParameter("elective2Insert");
            
            try {
                db.insert(name, rollNo, gender, contactNum, email, elective1, elective2);
                out.print("<p>Record inserted successfully!</p>");  
                request.getRequestDispatcher("index.html").include(request, response);  
            } 
            catch (SQLException ex) {
                Logger.getLogger(crudServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else if(request.getParameter("submitDelete") != null) {
             
            int rollNo = Integer.parseInt(request.getParameter("rollNoDelete"));  
            
            try {
                db.remove(rollNo);
                out.print("<p>Record removed successfully!</p>");
                request.getRequestDispatcher("delete.html").include(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(crudServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(request.getParameter("submitUpdate") != null) {
             
            int rollNo = Integer.parseInt(request.getParameter("rollNoUpdate"));
            int contactNum = Integer.parseInt(request.getParameter("numUpdate"));
            String email = request.getParameter("emailUpdate");
            
            try {
                db.update(rollNo, contactNum, email);
                out.print("<p>Record updated successfully!</p>");
                request.getRequestDispatcher("update.html").include(request, response);
            } 
            catch (SQLException ex) {
                Logger.getLogger(crudServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(request.getParameter("submitSearch") != null) {
            
            int rollNo = Integer.parseInt(request.getParameter("rollNoSearch"));
            
            try {
                ResultSet rs = db.search(rollNo);
                out.println("<table border=1>");
                out.println("<tr><th>Roll No</th><th>Name</th><th>Gender</th><th>Contact Number</th><th>Email</th><th>Elective 1</th><th>Elective 2</th></tr>");
                
                while(rs.next())
                {
                    out.println("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getInt(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td></tr>");  
                }
                out.println("</table>");
                request.getRequestDispatcher("search.html").include(request, response);
            } 
            catch (SQLException ex) {
                Logger.getLogger(crudServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Database db = new Database();
        db.getConnection();
        
        if(request.getParameter("param") != null) {
        
             try {
                ResultSet rs = db.display();
                out.println("<table border=1>");
                out.println("<tr><th>Roll No</th><th>Name</th><th>Gender</th><th>Contact Number</th><th>Email</th><th>Elective 1</th><th>Elective 2</th></tr>");
                
                while(rs.next())
                {
                    out.println("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getInt(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td></tr>");  
                }
                out.println("</table>");
                request.getRequestDispatcher("display.html").include(request, response);
            } 
            catch (SQLException ex) {
                Logger.getLogger(crudServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
