package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import librarysystem.LibrarySystem;

public class ExtendHandler {

    Connection conn;
    Statement st;

    public ExtendHandler(String post) {
        String host = "jdbc:mysql://localhost:3306/library_system";

        try {

            if (post.equalsIgnoreCase("admin")) {
                conn = (Connection) DriverManager.getConnection(host, "Administrator", "12345");
            } else {
                conn = (Connection) DriverManager.getConnection(host, "Member", "12345");
            }

            st = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date addExtend(String bookId, String issueD, String dueD) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        Statement stmt;
        java.sql.Date newDate=null;
        try {
            try {

            date = sdf1.parse(issueD);
            java.sql.Date sqlIssueDate = new Date(date.getTime());

            date = sdf1.parse(dueD);
            java.sql.Date sqlDueDate = new Date(date.getTime());

            newDate = new java.sql.Date(date.getTime() + 168 * 60 * 60 * 1000);

            String query = "INSERT INTO extends (Member_Id, Book_Id, Issue_Date, Due_Date, New_Due_Date) VALUES ('" + Integer.parseInt(LibrarySystem.loggedMember.getId()) + "','" + Integer.parseInt(bookId) + "','" + sqlIssueDate + "','" + sqlDueDate + "','" + newDate + "')";

            
                stmt = (Statement) conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.executeUpdate(query);
                
                 
            } catch (SQLException ex) {
                Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParseException ex) {
            Logger.getLogger(ExtendHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       return newDate;
    }
}
