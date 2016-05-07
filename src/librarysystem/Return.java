package librarysystem;

import java.sql.Date;

public class Return extends Transaction {

    private Date returnDate;

    public Return(Member mem, Book book, Date issueDate, Date dueDate, Date returnDate) {
        super(mem, book, issueDate, dueDate);
        setReturnDate(returnDate);

        //set issue date from order
        //set due date from order
        //set return date (current date)

    }


    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
