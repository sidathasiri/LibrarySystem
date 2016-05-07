/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import librarysystem.Order;

/**
 *
 * @author Sidath
 */
public class OrderHandler {

    Statement st;
    Connection conn;

    public OrderHandler(String post) {
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

    public void createOrder(Order orderObj) {
        Statement stmt;

        if (orderObj.getMember().getTakenBooks() == 2) {
            String query = "SELECT * FROM book_order WHERE Member_Id='" + orderObj.getMember().getId() + "'";

            try {
                stmt = (Statement) conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                ResultSet rs = stmt.executeQuery(query);
                rs.absolute(1);
                rs.updateString("Book2", orderObj.getBook1().getName());
                rs.updateString("Book2_Id", orderObj.getBook1().getBookId());
                rs.updateDate("Book2_Issue_Date", orderObj.getIssueDate());
                rs.updateDate("Book2_Due_Date", orderObj.getDueDate());
                rs.updateInt("No_Of_Books", orderObj.getMember().getTakenBooks());
                rs.updateRow();

            } catch (SQLException ex) {
                Logger.getLogger(OrderHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            String query = "INSERT INTO book_order (Member_Id, Book1, No_of_Books, Book1_id, Book1_Issue_Date, Book1_Due_Date) VALUES ('" + orderObj.getMember().getId() + "','" + orderObj.getBook1().getName() + "', '" + orderObj.getMember().getTakenBooks() + "', '" + orderObj.getBook1().getBookId() + "', '" + orderObj.getIssueDate() + "','" + orderObj.getDueDate() + "')";
            try {
                stmt = (Statement) conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.executeUpdate(query);

            } catch (SQLException ex) {
                Logger.getLogger(OrderHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
