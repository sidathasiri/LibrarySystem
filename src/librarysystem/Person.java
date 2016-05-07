package librarysystem;

import DB.BookHandler;
import DB.PeopleHandler;
import java.util.ArrayList;
import javax.swing.JTable;

public class Person {

    private String name;
    private String address;
    private String id;
    private String username, password;
    private String contactNumber;
    BookHandler bookHandlerObj = new BookHandler("member");
    PeopleHandler peopleHandlerObj = new PeopleHandler("member");

    public String getPassword() {
        return password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void login(String un, String pw) {
        peopleHandlerObj.login(un, pw);
    }

    public void logout() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void searchBook(String name, String author, String cat, JTable table) {
       /* ArrayList<String> results = bookHandlerObj.searchBook(name, author, cat);
        return results;*/
        bookHandlerObj.searchBook(name, author, cat, table);
        
    }
}
