package librarysystem;

import java.sql.Date;


public class Return extends Transaction{
    
    private Order order;
    
    private Date returnDate;
    public Return(Member mem, Book book, Order or, Date issueDate, Date dueDate){
        super(mem, book, issueDate, dueDate);
        setOrder(or);
        
        //set issue date from order
        //set due date from order
        //set return date (current date)
      
    }

     
    public void setOrder(Order order){
        this.order = order;
    }
    
    public void setReturnDate(Date returnDate){
        this.returnDate = returnDate;
    }
}
