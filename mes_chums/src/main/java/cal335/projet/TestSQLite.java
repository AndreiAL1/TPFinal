package cal335.projet;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestSQLite {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:mes_chums.db");
            if (connection != null) {
                System.out.println("Connected to SQLite!");
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}