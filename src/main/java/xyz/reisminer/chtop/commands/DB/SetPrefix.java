package xyz.reisminer.chtop.commands.DB;

import xyz.reisminer.chtop.Token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetPrefix {
    public static void setPrefix(String prefix) {
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("UPDATE prefixTable SET prefix='" + prefix + "'");
            Token.prefix = prefix;
            System.out.println("Prefix set to: " + Token.prefix);
        } catch (SQLException e) {
            System.out.println("Prefix stays: " + Token.prefix);
            System.err.println("DB Error:\n"+e);
        }
    }
}
