package xyz.reisminer.chtop;

import java.sql.*;

public class GetSettings {
    public static void getSettings() {
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                    .executeQuery("SELECT * from prefixTable where ID ='1'");
            if (resultSet.next()) {
                Token.prefix = resultSet.getString("prefix");
                Token.sendReacts = resultSet.getBoolean("react");
                System.out.println("Prefix: " + Token.prefix);
                System.out.println("Reacts: " + Token.sendReacts);
            }
        } catch (SQLException e) {
            Token.prefix = "$";
            Token.sendReacts = false;
            System.out.println("Database not reachable.\nPrefix: " + Token.prefix);
            System.out.println("Reacts: " + Token.sendReacts);
            System.err.println("DB Error:\n"+e);
        }

    }
}