import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;
    
    public void getConnection() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Elective?useSSL=false","aditi","aditi0111");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void insert(String name, int rollNo, String gender, int contactNum, String email, String elective1, String elective2) throws SQLException {
        
        ps = con.prepareStatement("INSERT into details(rollNo, name, gender, contactNum, email, elective1, elective2) values (?,?,?,?,?,?,?)");
        ps.setInt(1, rollNo);  
        ps.setString(2, name);  
        ps.setString(3, gender);  
        ps.setInt(4, contactNum);
        ps.setString(5, email);
        ps.setString(6, elective1);
        ps.setString(7, elective2);
        ps.executeUpdate();   
    }
    
    public void remove(int rollNo) throws SQLException {
        
        ps = con.prepareStatement("DELETE from details where rollNo = ?");
        ps.setInt(1, rollNo);
        ps.executeUpdate();
    }
    
    public void update(int rollNo, int contactNum, String email) throws SQLException {
        
        ps = con.prepareStatement("UPDATE details SET contactNum = ?, email = ? where rollNo = ?");
        ps.setInt(1, contactNum);
        ps.setString(2, email);
        ps.setInt(3, rollNo);
        ps.executeUpdate();
    }
    
    public ResultSet search(int rollNo) throws SQLException {
        
        ps = con.prepareStatement("SELECT * from details where rollNo = ?");
        ps.setInt(1, rollNo);
        rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet display() throws SQLException {
    
        ps = con.prepareStatement("SELECT * from details");
        rs = ps.executeQuery();
        return rs;
    }
}
