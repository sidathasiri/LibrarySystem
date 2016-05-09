package librarysystem;

import DB.BookHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) throws ParseException {
        java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
        java.sql.Date dueDate = new java.sql.Date(now.getTime() - 168 * 60 * 60 * 1000);
        System.out.println("now:"+now);
        System.out.println("new date:"+dueDate);

    }
}
