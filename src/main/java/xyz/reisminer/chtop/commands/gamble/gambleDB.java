package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import xyz.reisminer.chtop.Token;

import java.sql.*;

public class gambleDB {
    public static void addNewUser(User user) {
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            String s =String.format("INSERT INTO currencies (IDLong, `username`, amount) values ( '%d', '%s', '%d')",user.getIdLong(),user.getName(),50);
            statement.executeUpdate(s);
            System.out.println("Created new user in DB: " + user.getName());
        } catch (SQLException e) {
            System.err.println("Add User: DB Error:\n"+e);
        }
    }
    public static boolean userExists(User user) {
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("select * from currencies where IDLong like "+user.getIdLong());
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Check user: DB Error:\n"+e);
            return true;
        }
    }
    public static int getCurrencyOfUser(User user) {
        int balance=-1337;
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("select * from currencies where IDLong like "+user.getIdLong()+"");
            if (resultSet.next()) {
                balance=resultSet.getInt(3);
            }
        } catch (SQLException e) {
            System.err.println("DB Error:\n"+e);

        }
        return balance;
    }
    public static void changeBalance(User user, int amount) {
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query update currencies set amount=amount+1 where username like 'ReisMiner'
            String s =String.format("UPDATE currencies set amount=amount+%d where `IDLong` like '%d'",amount,user.getIdLong());
            statement.executeUpdate(s);
        } catch (SQLException e) {
            System.err.println("Chanbe balance: DB Error:\n"+e);
        }
    }
    public static String getLeaderBoard(int userCount) {
        StringBuilder retun = new StringBuilder();
        retun.append("```Top ").append(userCount).append(" :\n");
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                    .executeQuery("select * from currencies order by amount desc limit "+userCount);
            int i = 1;
            while (resultSet.next()) {
                String name = resultSet.getString("username");
                Double amount = resultSet.getDouble("amount");
                retun.append(" ").append(i).append(". ").append(name).append(" ").append(amount).append("\n");
                i++;
            }
            retun.append("```");
        } catch (SQLException e) {
            retun.append("`Couldn't connect to the Database.`");
            System.err.println("DB Error:\n"+e);
        }
        return retun.toString();
    }

    public static User getUserByName(String name, Message msg) {
        long retunIDLong = -1337;
        try (Connection connection = DriverManager.getConnection(Token.DBurl, Token.DBusername, Token.DBpassword)) {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            ResultSet resultSet = statement
                    .executeQuery("select * from currencies where username like '"+name+"'");
            if (resultSet.next()) {
                retunIDLong = resultSet.getLong("IDLong");
                            }
        } catch (SQLException e) {
            System.err.println("DB Error:\n"+e);
        }

        return msg.getJDA().getUserById(retunIDLong);
    }
}
