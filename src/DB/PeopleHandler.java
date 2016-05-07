package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import librarysystem.Admin;
import librarysystem.CoLibrarian;
import librarysystem.Librarian;
import librarysystem.Member;
import librarysystem.Person;
import librarysystem.StaffMember;

public class PeopleHandler {

    Statement st;
    Connection conn;

    public PeopleHandler(String post) {
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

    public void addPerson(Person m, String post) {
        if (post.equalsIgnoreCase("member")) {
            String query = "INSERT INTO member (Name, Address, Contact_Number, Username, Password) VALUES ('" + m.getName() + "', '" + m.getAddress() + "', '" + m.getContactNumber() + "', '" + m.getUsername() + "', '" + m.getPassword() + "')";
            try {
                st.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String query = "INSERT INTO staff (Name, Address, Contact_Number, Post, Username, Password) VALUES ('" + m.getName() + "', '" + m.getAddress() + "', '" + m.getContactNumber() + "', '" + post + "', '" + m.getUsername() + "', '" + m.getPassword() + "')";
            try {
                st.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(BookHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void blockPeoson(String memId) {
        String query = "SELECT * FROM member WHERE Id= '" + Integer.parseInt(memId) + "'";

        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            rs.absolute(1);
            rs.updateString("Status", "Blocked");
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("no such user");
        }
    }

    public void activatePerson(String memId) {
        String query = "SELECT * FROM member WHERE Id= '" + Integer.parseInt(memId) + "'";

        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            rs.absolute(1);
            rs.updateString("Status", "Active");
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("no such user");
        }
    }

    public void issueBook(String memId, String bookId) {
        String query1 = "SELECT * FROM member WHERE Id= '" + Integer.parseInt(memId) + "'";
        String query2 = "SELECT * FROM book WHERE Id= '" + Integer.parseInt(bookId) + "'";

        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rsMem = stmt.executeQuery(query1);
            ResultSet rsBook = stmt.executeQuery(query2);

            rsMem.absolute(1);
            rsBook.absolute(1);



        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("no such user");
        }
    }

    public void resetPassword(String memId) {
        String query = "SELECT * FROM member WHERE Id= '" + Integer.parseInt(memId) + "'";

        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            rs.absolute(1);
            rs.updateString("Password", "12345");
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("no such user");
        }
    }

    public void resetUsername(String memId) {
        String query = "SELECT * FROM member WHERE Id= '" + Integer.parseInt(memId) + "'";

        try {
            Statement stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);
            rs.absolute(1);
            rs.updateString("Username", "libraryuser");
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("no such user");
        }
    }

    public String login(String un, String pw) {
        String query1 = "SELECT * FROM member WHERE BINARY Username='" + un + "' && BINARY Password='" + pw + "'";
        String query2 = "SELECT * FROM staff WHERE BINARY Username='" + un + "' && BINARY Password='" + pw + "'";

        Statement stmt;
        String post = "not found";
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rsMem = stmt.executeQuery(query1);

            if (rsMem.next()) {
                post = "member";
            } else {
                ResultSet rsStaff = stmt.executeQuery(query2);

                if (rsStaff.next()) {
                    post = rsStaff.getString("Post");
                } else {
                    post = "not found";
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return post;
    }

    public ArrayList<String> loadData(String un) {
        String query1 = "SELECT * FROM member WHERE BINARY Username='" + un + "' ";
        String query2 = "SELECT * FROM staff WHERE BINARY Username='" + un + "' ";

        ArrayList<String> stringArr = new ArrayList<>();
        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rsMem = stmt.executeQuery(query1);

            if (rsMem.next()) {
                stringArr.add(rsMem.getString("Name"));
                stringArr.add(rsMem.getString("Address"));
                stringArr.add(rsMem.getString("Contact_Number"));
                stringArr.add(rsMem.getString("Id"));
                stringArr.add(rsMem.getString("No_Of_Taken_Books"));
                stringArr.add(rsMem.getString("Status"));
                stringArr.add(rsMem.getString("Username"));
                stringArr.add(rsMem.getString("Password"));
            } else {
                ResultSet rsStaff = stmt.executeQuery(query2);

                if (rsStaff.next()) {
                    stringArr.add(rsStaff.getString("Name"));
                    stringArr.add(rsStaff.getString("Address"));
                    stringArr.add(rsStaff.getString("Contact_Number"));
                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stringArr;
    }

    public ArrayList<String> searchMember(int memId) {
        String query = "SELECT * FROM member WHERE Id='" + memId + "'";

        Statement stmt;
        ArrayList<String> details = new ArrayList<>();
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                details.add(rs.getString("Name"));
                details.add(rs.getString("Id"));
                details.add(rs.getString("No_Of_Taken_Books"));
                details.add(rs.getString("Contact_Number"));
                details.add(rs.getString("Address"));
            }


        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return details;
    }

    public void issueBooktoUser(Member memObj) {
        String query = "SELECT * FROM member WHERE Id='" + Integer.parseInt(memObj.getId()) + "'";
        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateInt("No_Of_Taken_Books", memObj.getTakenBooks());
            rs.updateString("Order_Id", memObj.getOrder().getId());
            rs.updateRow();

        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTakenBooks(int memId) {
        String query = "SELECT * FROM member WHERE Id='" + memId + "'";
        Statement stmt;
        int takenBooks = 0;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                takenBooks = rs.getInt("No_Of_Taken_Books");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return takenBooks;
    }

    public void updateProfile(Member memObj) {
        String query = "SELECT * FROM member WHERE Id='" + memObj.getId() + "'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateString("Name", memObj.getName());
            rs.updateString("Contact_Number", memObj.getContactNumber());
            rs.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeLogin(Member memObj, boolean isChanged) {
        String query1 = "SELECT * FROM member WHERE Username='" + memObj.getUsername() + "'";
        Statement stmt1, stmt2;
        boolean check = true;

        try {
            stmt1 = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs1 = stmt1.executeQuery(query1);

            if (rs1.next() && isChanged) {
                JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                check = false;
            }

            if (check == true) {
                String query2 = "SELECT * FROM member WHERE Id='" + memObj.getId() + "'";
                stmt2 = (Statement) conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                ResultSet rs2 = stmt2.executeQuery(query2);

                rs2.absolute(1);
                rs2.updateString("Username", memObj.getUsername());
                rs2.updateString("Password", memObj.getPassword());
                rs2.updateRow();
                JOptionPane.showMessageDialog(null, "Change Succesful!", "Change", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void returnBook(Member memObj) {
        String query = "SELECT * FROM member WHERE Id='" + memObj.getId() + "'";
        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            rs.absolute(1);
            rs.updateInt("No_Of_Taken_Books", memObj.getTakenBooks());
            if(memObj.getTakenBooks()==0){
                rs.updateString("Order_Id", null);
            }
            rs.updateRow();


        } catch (SQLException ex) {
            Logger.getLogger(PeopleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
