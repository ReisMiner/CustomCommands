package xyz.reisminer.chtop.commands;

import xyz.reisminer.chtop.Token;

import java.sql.*;

public class DBSay {
    public static String dbSay(String msg) {
        System.out.println("Connecting database...");
        String retun = "This Command does not exist";

        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                    .executeQuery("SELECT * from command WHERE cmd='" + msg.substring(Token.prefix.length()) + "';");
            if (resultSet.next()) {
                retun = resultSet.getString("cmdOut");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return retun;
    }
}
