package xyz.reisminer.chtop.commands.gamble;

import xyz.reisminer.chtop.Token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class gambleDB {
    public static void addNewUser(long userID) {
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("INSERT INTO currencies (IDLong, amount) values ("+userID+", 0)");
            System.out.println("Created new user in DB with ID: " + userID);
        } catch (SQLException e) {
            System.err.println("DB Error:\n"+e);
        }
    }
}
