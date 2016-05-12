package librarysystem;

import DB.BookHandler;
import DB.ExtendHandler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) throws ParseException {
        String startDate = "2014-12-15";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date sqlNow = new Date(date.getTime());
        java.sql.Date newtime = new java.sql.Date(date.getTime() + 168 * 60 * 60 * 1000);
        System.out.println("now:"+sqlNow);
        System.out.println("new:"+newtime);
    }
}
