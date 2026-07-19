package dbarch.a11database.config;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection1() {
        if (connection == null) {
            try {
                Class.forName("org.h2.Driver");
                // connection = DriverManager.getConnection("jdbc:h2:./projectdb", "sa", "");
                //jdbc:h2:tcp://172.26.128.1:9092/./projectdb
                connection = DriverManager.getConnection("jdbc:h2:tcp://172.26.128.1:9092/./hellodb", "sa", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

  public static Connection getConnection() {
     if (connection == null) {
         try {
             Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(
                 "jdbc:postgresql://localhost:5432/yourdbname", "yourusername", "yourpassword"
             );
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     return connection;
 }

}
