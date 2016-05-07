package librarysystem;

import java.sql.Date;
import java.util.ArrayList;

public class Transaction {

    private String status;
    private Date issueDate, dueDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private Member member;
    private Book book;
    private String id;

    public Transaction(Member mem, Book book, Date issueDate, Date dueDate) {
        this.member = mem;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;

    }

    public Member getMember() {
        return member;
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

    public Book getBook() {
        return book;
    }
}
