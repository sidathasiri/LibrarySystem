package librarysystem;

import DB.BookHandler;
import DB.OrderHandler;
import DB.PeopleHandler;
import DB.ReturnHandler;
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
    ReturnHandler returnHandlerObj = new ReturnHandler("admin");

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
                if (bookHandlerObj.checkStatus(bookId).equalsIgnoreCase("available") && bookHandlerObj.checkReservation(bookId).equalsIgnoreCase("false")) {
                    memObj.setTakenBooks((memObj.getTakenBooks() + 1));
                    bookObj.setStatus("Ünavailable");


                    java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
                    java.sql.Date dueDate = new java.sql.Date(now.getTime() + 336 * 60 * 60 * 1000);
                    Order orderObj;
                    orderObj = new Order(memObj, bookObj, now, dueDate);
                    memObj.setOrder(orderObj);
                    orderHandlerObj.createOrder(orderObj, memObj);
                    peopleHandlerObj.issueBooktoUser(memObj);
                    bookHandlerObj.issueBook(bookObj);


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

    public void returnBook(String memId, String bookId, String issueDate, String dueDate) {
        ArrayList<String> memberDetails = peopleHandlerObj.searchMember(Integer.parseInt(memId));
        ArrayList<String> bookDetails = bookHandlerObj.loadBookData(Integer.parseInt(bookId));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed;
        try {
            parsed = format.parse("20100214");
            java.sql.Date date = new java.sql.Date(parsed.getTime());

            Member memObj = new Member(memberDetails.get(0), memberDetails.get(4), memberDetails.get(3));
            memObj.setTakenBooks(Integer.parseInt(memberDetails.get(2)));
            memObj.setId(memberDetails.get(1));

            Book bookObj = new Book(bookDetails.get(0), bookDetails.get(2), bookDetails.get(4), Integer.parseInt(bookDetails.get(5)), bookDetails.get(6), date, Integer.parseInt(bookDetails.get(7)));
            bookObj.setBookId(bookDetails.get(1));

            memObj.setTakenBooks(memObj.getTakenBooks() - 1);
            bookObj.setStatus("Available");

            bookHandlerObj.returnBook(bookObj);
            peopleHandlerObj.returnBook(memObj);
            int orderId = orderHandlerObj.getOrderId(memObj);
            if (memObj.getTakenBooks() == 0) {
                
                orderHandlerObj.bookReturnUpdate(memObj);
            }

            parsed = format.parse(issueDate);
            java.sql.Date issueD = new java.sql.Date(parsed.getTime());

            parsed = format.parse(dueDate);
            java.sql.Date dueD = new java.sql.Date(parsed.getTime());
            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            Return returnObj = new Return(memObj, bookObj, issueD, dueD, now);
            returnHandlerObj.createReturn(returnObj, memObj, issueD, dueD, now, orderId);

        } catch (ParseException ex) {
            Logger.getLogger(StaffMember.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
