package librarysystem;

import java.sql.Date;

public class Book {
    private String bookId;
    private String author;
    private String name;
    private String ISBN;
    private int noOfPages;
    private String category;
    private int issueNo;
    private Date publishedDate;
    private Date issueDate, dueDate;
    private String status;
    //Name, Author, ISBN, No_Of_Pages, Category, Published_Date
    public Book(String name, String author, String ISBN, int page, String category, Date publishedDate, int issueNo){
        this.name = name;
        this.ISBN = ISBN;
        this.noOfPages = page;
        this.category = category;
        this.author = author;
        this.publishedDate = publishedDate;
        this.issueNo = issueNo;
        
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookId() {
        return bookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public void setIssueNo(int issueNo) {
        this.issueNo = issueNo;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getName() {
        return name;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public int getIssueNo() {
        return issueNo;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
   
}
