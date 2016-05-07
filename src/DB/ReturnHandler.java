package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import librarysystem.Member;
import librarysystem.Return;

public class ReturnHandler {

    Statement st;
    Connection conn;
    OrderHandler orderHandlerObj = new OrderHandler("member");

    public ReturnHandler(String post) {
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

    public void createReturn(Return returnObj, Member memObj, Date issueDate, Date dueDate, Date returnDate) {
        Statement stmt;
        String query1 = "SELECT * FROM book_return WHERE Member_Id='" + memObj.getId() + "' && Status='Active'";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query1);

            if (rs.next()) {
                rs.updateString("Book2", returnObj.getBook().getName());
                rs.updateString("Book2_Id", returnObj.getBook().getBookId());
                rs.updateDate("Book2_Issue_Date", issueDate);
                rs.updateDate("Book2_Due_Date", dueDate);
                rs.updateDate("Book2_Return_Date", returnDate);
                if (memObj.getTakenBooks() == 0) {
                    rs.updateString("Status", "completed");
                }
                rs.updateRow();
            } else {
                int orderId = orderHandlerObj.getOrderId(memObj);
                String query2 = "INSERT INTO book_return (Order_Id, member, Member_Id, Book1, Book1_Id, Book1_Issue_Date, Book1_Due_Date, Book1_Return_Date) VALUES('" + orderId + "', '" + memObj.getName() + "', '" + memObj.getId() + "', '" + returnObj.getBook().getName() + "', '" + returnObj.getBook().getBookId() + "', '" + issueDate + "', '" + dueDate + "','" + returnDate + "')";
                stmt = (Statement) conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.executeUpdate(query2);
                if (memObj.getTakenBooks() == 0) {
                    stmt = (Statement) conn.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);

                    rs = stmt.executeQuery(query1);
                    rs.absolute(1);
                    rs.updateString("Status", "completed");
                    rs.updateRow();
                }



            }
        } catch (SQLException ex) {
            Logger.getLogger(ReturnHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStatus(Member memObj) {
        Statement stmt;
        String query = "SELECT * FROM book_return WHERE Member_Id='" + memObj.getId() + "' && Status='active'";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            rs.absolute(1);
            rs.updateString("Status", "completed");
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(OrderHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
