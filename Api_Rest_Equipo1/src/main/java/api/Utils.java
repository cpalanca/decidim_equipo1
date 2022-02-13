package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static Connection connectDB() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:/Users/carlos/Documents/BBDD/decidim_equipo1.db";
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
	    	return null;
	    }
		System.out.println("Connection to SQLite has been established.");
		return conn;
	}
	
	public static Date StringToDatetime(String date_time) {
		//String date_time = "11/27/2020 05:35:00";
		Date date= null;
        SimpleDateFormat dateParser = new SimpleDateFormat("yy/MM/dd HH:mm");
        {
            try {
                date = dateParser.parse(date_time);
                System.out.println(date);

                //SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
                //System.out.println(dateFormatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
	}
}
