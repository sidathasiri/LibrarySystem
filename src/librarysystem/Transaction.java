package librarysystem;


import java.sql.Date;
import java.util.ArrayList;


public class Transaction {
    private String status;
    private Date issueDate, dueDate;
    private Member member;
    private Book book1, book2;
    
    public Transaction(Member mem, Book book, Date issueDate, Date dueDate){
        this.member = mem;
        this.book1 = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        
    }

    public Member getMember() {
        return member;
    }

    public Book getBook1() {
        return book1;
    }


    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    
     public void setStatus(String status) {
        this.status = status;
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
}
