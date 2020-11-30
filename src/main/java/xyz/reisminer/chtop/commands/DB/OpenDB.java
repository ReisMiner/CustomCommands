package xyz.reisminer.chtop.commands.DB;

import xyz.reisminer.chtop.Token;

import java.sql.*;

public class OpenDB {
    public static String openDB() {
        System.out.println("Connecting database...");
        StringBuilder retun = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                    .executeQuery("select * from command");
            while (resultSet.next()) {
                String cmd = resultSet.getString("cmd");
                retun.append(Token.prefix).append(cmd + "\n");
            }
        } catch (SQLException e) {
            retun.append("`Couldn't connect to the Database.`");
            System.err.println("DB Error:\n"+e);
        }
        return retun.toString();
    }
}
