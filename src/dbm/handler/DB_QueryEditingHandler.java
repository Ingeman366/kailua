package dbm.handler;

import dbm.DB_Dependencies;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_QueryEditingHandler {


    public void insertQuery(String query) {
        try (
                java.sql.Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password);
                        Statement s = connection.createStatement();
        ) {
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Empty response" + e);
        } // End of try-catch block
    } // End of method
}
