package xyz.reisminer.chtop.commands.DB;

import xyz.reisminer.chtop.Token;

import java.sql.*;

public class SetStuff {
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
            System.err.println("DB Error:\n" + e);
        }
    }

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
            System.err.println("DB Error:\n" + e);
        }
    }

    public static void setAutoRename(Boolean rename) {
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("UPDATE prefixTable SET `rename`=" + rename + "");
            Token.autoRename = rename;
            System.out.println("Autorename set to: " + Token.autoRename);
        } catch (SQLException e) {
            System.out.println("Autorename stays: " + Token.autoRename);
            System.err.println("DB Error:\n" + e);
        }
    }

    public static void setJoinBlock(Boolean joinBlocked) {

        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("UPDATE prefixTable SET `join`=" + joinBlocked + "");
            Token.joinBlocked = joinBlocked;
            System.out.println("JoinBlocked set to: " + Token.joinBlocked);
        } catch (SQLException e) {
            System.out.println("JoinBlocked stays: " + Token.joinBlocked);
            System.err.println("DB Error:\n" + e);
        }
    }

    public static void setTaufBoostOnly(Boolean boostOnly) {

        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("UPDATE prefixTable SET `boost_only`=" + boostOnly + "");
            Token.boostOnly = boostOnly;
            System.out.println("Boost Only set to: " + Token.boostOnly);
        } catch (SQLException e) {
            System.out.println("Boost Only stays: " + Token.boostOnly);
            System.err.println("DB Error:\n" + e);
        }
    }

    public static void addBanWord(String word) {

        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            System.out.println("Database connected!");
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            statement.executeUpdate("insert into banWords(word) value (\"" + word + "\")");
            Token.banWhenSend.add(word);
            System.out.println("added new ban word: " + word);
        } catch (SQLException e) {
            System.out.println("could not add ban word: " + word);
            System.err.println("DB Error:\n" + e);
        }
    }

}
