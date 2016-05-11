package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import librarysystem.Book;

public class BookHandler {

    Statement st;
    Connection conn;

    public BookHandler(String post) {
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

    public void addBook(Book bk) {
        try {
            String query = "INSERT INTO book (Name, Author, ISBN, No_Of_Pages, Category, Published_Date, Issue_No) VALUES ('" + bk.getName() + "', '" + bk.getAuthor() + "', '" + bk.getISBN() + "', '" + bk.getNoOfPages() + "', '" + bk.getCategory() + "', '" + bk.getPublishedDate() + "', '" + bk.getIssueNo() + "')";
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchBook(String name, String author, String cat, JTable table) {
        String query;
        Statement stmt;
        ArrayList<String> searchDetails = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ResultSet rs = null;
        boolean check = false;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);


            if (!name.equals("") && author.equals("") && cat.equals("No Category")) {
                //search only by name
                query = "SELECT * from book where Name='" + name + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (name.equals("") && !author.equals("") && cat.equals("No Category")) {
                // onmy by author
                query = "SELECT * from book where Author='" + author + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (name.equals("") && author.equals("") && !cat.equals("No Category")) {
                //by category
                query = "SELECT * from book where Category='" + cat + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (!name.equals("") && !author.equals("") && cat.equals("No Category")) {
                //by name & author
                query = "SELECT * from book where Name='" + name + "' && Author='" + author + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (!name.equals("") && author.equals("") && !cat.equals("No Category")) {
                //by name & category
                query = "SELECT * from book where Name='" + name + "' && Category='" + cat + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (name.equals("") && !author.equals("") && !cat.equals("No Category")) {
                //by author & category 
                query = "SELECT * from book where Author='" + author + "' && Category='" + cat + "'";
                rs = stmt.executeQuery(query);
                check = true;


            } else if (name.equals("") && author.equals("") && cat.equals("No Category")) {
                JOptionPane.showMessageDialog(null, "Enter valid data!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (check == true) {
                if (rs.next()) {
                    rs.beforeFirst();
                    while (rs.next()) {
                        searchDetails.add(rs.getInt("Id") + "");
                        searchDetails.add(rs.getString("Name"));
                        searchDetails.add(rs.getString("Author"));
                        searchDetails.add(rs.getString("ISBN"));
                        searchDetails.add(rs.getString("No_Of_Pages"));
                        searchDetails.add(rs.getString("Category"));
                        searchDetails.add(rs.getDate("Published_Date") + "");
                        searchDetails.add(rs.getString("Issue_No"));
                        searchDetails.add(rs.getString("Status"));
                        searchDetails.add(rs.getString("isReserved"));
                        Object row[] = new Object[10];


                        for (int i = 0; i < searchDetails.size(); i++) {
                            row[i] = searchDetails.get(i);

                        }
                        model.addRow(row);

                        searchDetails.clear();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No results found", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //       return searchDetails;
    }

    public ArrayList<String> loadOrderedBookData(int id) {

        ArrayList<String> bookData = new ArrayList<>();
        String query = "SELECT * FROM book_order WHERE Member_Id='" + id + "' && Status='Active'";
        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                bookData.add(rs.getString("Book1"));
                bookData.add(String.valueOf(rs.getInt("Book1_Id")));
                bookData.add(rs.getDate("Book1_Issue_Date") + "");
                bookData.add(rs.getDate("Book1_Due_Date") + "");
                bookData.add(rs.getString("Book2"));
                bookData.add(rs.getInt("Book2_Id") + "");
                bookData.add(rs.getDate("Book2_Issue_Date") + "");
                bookData.add(rs.getDate("Book2_Due_Date") + "");
                bookData.add(rs.getString("Status"));
                bookData.add(rs.getString("No_of_Books"));

            }


        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookData;
    }

    public ArrayList<String> loadBookData(int id) {
        ArrayList<String> bookData = new ArrayList<>();
        String query = "SELECT * FROM book WHERE Id='" + id + "'";
        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                bookData.add(rs.getString("Name"));
                bookData.add(String.valueOf(rs.getInt("Id")));
                bookData.add(rs.getString("Author"));
                bookData.add(rs.getString("Status"));
                bookData.add(rs.getString("ISBN"));
                bookData.add(rs.getString("No_Of_Pages"));
                bookData.add(rs.getString("Category"));
                bookData.add(rs.getString("Issue_No"));
                bookData.add(rs.getString("isReserved"));
            }


        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookData;
    }

    public String checkStatus(int bookId) {
        String status = null;
        String query = "SELECT * FROM book WHERE Id='" + bookId + "'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                status = rs.getString("Status");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public void issueBook(Book bookObj) {
        Statement stmt;
        String query = "SELECT * FROM book WHERE Id='" + bookObj.getBookId() + "'";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateString("Status", "Unavailable");
            rs.updateDate("Final_Due_Date", bookObj.getFinalDueDate());
            rs.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> loadCategory() {
        Statement stmt;
        ArrayList<String> categoryArray = new ArrayList<>();
        String query = "SELECT * FROM category";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                categoryArray.add(rs.getString("Category"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categoryArray;
    }

    public void addNewCategory(String x) {
        Statement stmt;
        String query = "INSERT INTO category VALUES ('" + x + "')";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void returnBook(Book bookObj) {
        Statement stmt;
        String query = "SELECT * FROM book WHERE Id='" + bookObj.getBookId() + "'";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateString("Status", "Available");
            rs.updateDate("Final_Due_Date", null);
            rs.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String checkReservation(int bookId) {
        String result = null;
        String query = "SELECT * FROM book WHERE Id='" + bookId + "'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                result = rs.getString("isReserved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void updateReservation(int bookId, String entry) {
        String query = "SELECT * FROM book WHERE Id='" + bookId + "'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateString("isReserved", entry);
            rs.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getFinalDueDate(String bookId) {
        String query = "SELECT FROM book WHERE Id='" + bookId + "'";
        Statement stmt;
        Date date = null;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                date = rs.getDate("Final_Due_Date");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;

    }
}
