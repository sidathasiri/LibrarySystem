package librarysystem;

import DB.BookHandler;
import DB.OrderHandler;
import DB.PeopleHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public abstract class StaffMember extends Person {

    PeopleHandler peopleHandlerObj = new PeopleHandler("admin");
    BookHandler bookHandlerObj = new BookHandler("admin");
    OrderHandler orderHandlerObj = new OrderHandler("admin");

    public StaffMember(String name, String address, String contactNum) {
        setName(name);
        setAddress(address);
        setContactNumber(contactNum);
        setUsername("libraryuser");
        setPassword("12345");
    }

    public void issueBook(int memId, int bookId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        ArrayList<String> memberDetails = peopleHandlerObj.searchMember(memId);
        ArrayList<String> bookDetails = bookHandlerObj.loadBookData(bookId);

        Member memObj = new Member(memberDetails.get(0), memberDetails.get(4), memberDetails.get(3));
        memObj.setTakenBooks(Integer.parseInt(memberDetails.get(2)));
        memObj.setId(memberDetails.get(1));
        java.util.Date parsed;
        try {
            parsed = format.parse("20100214");
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            Book bookObj = new Book(bookDetails.get(0), bookDetails.get(2), bookDetails.get(4), Integer.parseInt(bookDetails.get(5)), bookDetails.get(6), date, Integer.parseInt(bookDetails.get(7)));
            bookObj.setBookId(bookDetails.get(1));

            if (memObj.getTakenBooks() < 2) {
                if (bookHandlerObj.checkStatus(bookId).equalsIgnoreCase("available")) {
                    memObj.setTakenBooks((memObj.getTakenBooks() + 1));
                    peopleHandlerObj.issueBooktoUser(memObj);
                    bookHandlerObj.issueBook(bookObj);
                    bookObj.setStatus("Ünavailable");
                    
                    
                    java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
                    java.sql.Date dueDate = new java.sql.Date(now.getTime() + 336 * 60 * 60 * 1000);
                    Order orderObj;
                    orderObj = new Order(memObj, bookObj, now, dueDate);
                    System.out.println("order:"+orderObj.getIssueDate());
                    orderHandlerObj.createOrder(orderObj);
                    
               
                } else {
                    JOptionPane.showMessageDialog(null, "Book not available!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "You already taken maximum books!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ParseException ex) {
            Logger.getLogger(StaffMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void returnBook(String memId, String bookId) {
    }
}
