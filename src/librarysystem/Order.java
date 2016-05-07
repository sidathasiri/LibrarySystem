package librarysystem;

import java.sql.Date;

public class Order extends Transaction {
        
    public Order(Member mem, Book book,Date issueDate, Date dueDate){
        super(mem, book,issueDate, dueDate);
        setStatus("Active");
        //set current date
        //set due date        
    }
    
    

   
    
}
