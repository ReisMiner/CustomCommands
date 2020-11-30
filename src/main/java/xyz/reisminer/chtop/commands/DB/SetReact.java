package xyz.reisminer.chtop.commands.DB;

import xyz.reisminer.chtop.Token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetReact {
    public static void setReact(Boolean react) {
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("UPDATE prefixTable SET react=" + react + "");
            Token.sendReacts = react;
            System.out.println("React set to: " + Token.sendReacts);
        } catch (SQLException e) {
            System.out.println("React stays: " + Token.sendReacts);
            System.err.println("DB Error:\n"+e);
        }
    }
}
