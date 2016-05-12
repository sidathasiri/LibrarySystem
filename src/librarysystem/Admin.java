package librarysystem;

import DB.BookHandler;
import DB.PeopleHandler;
import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Admin extends StaffMember {

    BookHandler bookHandlerObj = new BookHandler("admin");
    PeopleHandler personHandlerObj = new PeopleHandler("admin");

    public Admin(String name, String address, String contactNum) {
        super(name, address, contactNum);
    }

    public void addMember(String name, String address, String contactNum, String email) {
        Member memObj = new Member(name, address, contactNum, email);
        personHandlerObj.addPerson(memObj, "member");
    }

    public void addLibrarian(String name, String address, String contactNum) {
        Librarian libObj = new Librarian(name, address, contactNum);
        personHandlerObj.addPerson(libObj, "Librarian");
    }

    public void addCoLibrarian(String name, String address, String contactNum) {
        CoLibrarian coLibObj = new CoLibrarian(name, address, contactNum);
        personHandlerObj.addPerson(coLibObj, "CoLibrarian");
    }

    public void blockUser(String memId) {
        personHandlerObj.blockPeoson(memId);
    }

    public void activateUser(String memId) {
        personHandlerObj.activatePerson(memId);
    }

    public void addBook(String name, String author, String ISBN, int page, String category, String pubDate, int issueNo) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        try {
            java.util.Date parsed = format.parse(pubDate);
            java.sql.Date date = new java.sql.Date(parsed.getTime());
            Book bookObj = new Book(name, author, ISBN, page, category, date, issueNo);
            bookHandlerObj.addBook(bookObj);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Date format not correct", "Error", JOptionPane.ERROR);
        }
    }

    public void resetPassword(String memId) {
        personHandlerObj.resetPassword(memId);
    }

    public void resetUsername(String memId) {
        personHandlerObj.resetUsername(memId);
    }

    public void editBookRecord(String bookId) {
    }

    public void editPersonRecord(String memId) {
    }
}
