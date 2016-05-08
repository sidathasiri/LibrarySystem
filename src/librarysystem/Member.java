package librarysystem;

import DB.ReserveHandler;
import java.util.ArrayList;
import DB.BookHandler;

public class Member extends Person{
    private Book book1,book2;
    private Order order;
    private int takenBooks;
    ReserveHandler reserveHandlerObj = new ReserveHandler("admin");
    BookHandler bookHandlerObj = new BookHandler("admin");
    
    public Member(String name, String address, String contactNum){
        setName(name);
        setAddress(address);
        setContactNumber(contactNum);
        setUsername("libraryuser");
        setPassword("12345");
        //load user data
    }

    public void setTakenBooks(int takenBooks) {
        this.takenBooks = takenBooks;
    }
    
    public void takeBook(String id){
        
    }
    
    public void takeBook(String id1, String id2){
    
    }
    
    public void returnBook(String id){
        //change order
        //clear book fields
    }
    
    public void returnBook(String id1, String id2){
        
    }

    public void setBook(Book book) {
        this.book1 = book;
    }
    
     public void setBook(Book book1, Book book2) {
        this.book2 = book2;
        this.book1 = book1;
    }
     

    public ArrayList<Book> getBook() {
        ArrayList<Book> books = new ArrayList<>();
        if(book1!=null && book2!=null){
            books.add(book1);
            books.add(book2);
        }
        
        if(book1!=null){
            books.add(book1);
        }
     
        return books;
       
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTakenBooks() {
        return takenBooks;
    }
    
    public void reserveBook(int bookId){
        reserveHandlerObj.addReservation(bookId);
    }
    
    public void cancelReserve(int bookId){
        reserveHandlerObj.cancelReservation(Integer.parseInt(LibrarySystem.loggedMember.getId()));
        bookHandlerObj.updateReservation(bookId, "false");
    }

    
}
