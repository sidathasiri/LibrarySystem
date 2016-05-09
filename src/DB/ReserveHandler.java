package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import librarysystem.LibrarySystem;

public class ReserveHandler {

    Statement st;
    Connection conn;
    BookHandler bookHanderObj = new BookHandler("admin");

    public ReserveHandler(String post) {
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

    public void addReservation(int bookId) {
        int memId = Integer.parseInt(LibrarySystem.loggedMember.getId());
        String query1 = "SELECT * FROM reservation WHERE Member_Id='" + memId + "' && Status='Active'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query1);

            java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());

            if (!rs.next()) {
                if (bookHanderObj.checkReservation(bookId).equalsIgnoreCase("false")) {
                    String query2 = "INSERT INTO reservation (Member_Id, Book_Id, reserved_date) VALUES('" + memId + "', '" + bookId + "', '" + now + "')";
                    stmt.executeUpdate(query2);
                    bookHanderObj.updateReservation(bookId, "true");
                } else {
                    JOptionPane.showMessageDialog(null, "Book is already reserved", "Book reservation", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "You have alredy reserved a book", "Book reservation", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReserveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int loadReservedBook(int memId) {
        String query = "SELECT * FROM reservation WHERE Member_Id='" + memId + "' && Status='Active'";
        Statement stmt;
        int bookId = 0;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                bookId = rs.getInt("Book_Id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReserveHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookId;

    }

    public void cancelReservation(int memId) {
        String query = "SELECT * FROM reservation WHERE Member_Id='" + memId + "' && Status='Active'";
        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateString("Status", "completed");
            rs.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateReservation(int memId, int bookId) {
        String query = "SELECT * FROM reservation WHERE Member_Id='" + memId + "' && Status='Active' && Book_Id='" + bookId + "'";
        Statement stmt;
        boolean check = false;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return check;
    }

    public void clearExpiredReservation() {

        java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
        String query1 = "SELECT * FROM reservation WHERE Status='Active'";

        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query1);

            while (rs.next()) {
                Date reservedDate = rs.getDate("reserved_date");
                java.sql.Date checkDate = new java.sql.Date(now.getTime() - 168 * 60 * 60 * 1000);
                
                
                if (reservedDate.before(checkDate)) {
                    rs.updateString("Status", "completed");
                    rs.updateRow();
                    bookHanderObj.updateReservation(rs.getInt("Book_Id"), "false");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
