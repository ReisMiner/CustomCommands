package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;
import xyz.reisminer.chtop.Token;

import java.security.SecureRandom;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class rob {
    public static void steal(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 3) {


            User victim = null;
            boolean error = false;
            User author = msg.getAuthor();
            if (!msg.getMentionedMembers().isEmpty()) {
                victim = msg.getMentionedMembers().get(0).getUser();
            } else if (StringUtils.isNumeric(splitMessage[1])) {
                try {
                    victim = msg.getJDA().getUserById(Long.parseLong(splitMessage[1]));
                } catch (Exception ignore) {
                    error = true;
                }
            } else {
                victim = getUserByName(splitMessage[1], msg);
                try {
                    victim.getIdLong();
                } catch (NullPointerException e) {
                    error = true;
                }
            }


            if (!error && victim.getIdLong() != Token.BOTID) {

                int stealAmount = Integer.parseInt(splitMessage[2]);

                if (stealAmount > 0) {

                    if (!userExists(victim)) {
                        addNewUser(victim);
                    }

                    int walletAmountSelf = getCurrencyOfUser(author);
                    int walletAmountVictim = getCurrencyOfUser(victim);

                    if (stealAmount <= walletAmountSelf && walletAmountVictim >= stealAmount) {

                        SecureRandom rand = new SecureRandom();

                        int n = rand.nextInt(101);
                        System.out.println(n);

                        if (n < 50) {
                            changeBalance(author, stealAmount);
                            changeBalance(victim, -stealAmount);
                            channel.sendMessage("<@" + author.getIdLong() + ">, You Robbed `" + stealAmount + "` peterZ from " + victim.getName() + "! You now have `" + getCurrencyOfUser(author) + "` peterZ!").queue();
                            System.out.println(author.getName() + " Robbed and won " + stealAmount);
                        } else {
                            changeBalance(author, -stealAmount);
                            changeBalance(victim, stealAmount);
                            channel.sendMessage("<@" + author.getIdLong() + ">, You got caught by the guards! You lost `" + stealAmount + "` peterZ! You now have `" + getCurrencyOfUser(author) + "` peterZ!").queue();
                            System.out.println(author.getName() + " Robbed and lost " + stealAmount);
                        }
                    } else {
                        channel.sendMessage("You or your Victim are too poor :(").queue();
                    }
                } else {
                    channel.sendMessage("Cannot steal negative or no amount!").queue();
                }
            } else {
                channel.sendMessage("Cannot rob this User. Make sure its spelled correctly. If not sure look in leaderboard!\n" +
                        "Note: Cannot rob CustomCommands!").queue();
            }
        } else {
            channel.sendMessage("CMD Usage: [PREFIX]rob <@someone> <amount to steal>.\n" +
                    "NOTE: You can lose your money too and give it to the other person ;)").queue();
        }
    }

    public static void gift(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 3 && !msg.getMentionedMembers().isEmpty()) {
            User author = msg.getAuthor();
            User victim = msg.getMentionedMembers().get(0).getUser();

            if (victim.getIdLong() != Token.BOTID) {

                int giftAmount;

                giftAmount = Integer.parseInt(splitMessage[2]);

                if (giftAmount > 0) {

                    if (!userExists(victim)) {
                        addNewUser(victim);
                    }

                    int walletAmountSelf = getCurrencyOfUser(author);

                    if (giftAmount <= walletAmountSelf) {

                        changeBalance(author, -giftAmount);
                        changeBalance(victim, giftAmount);
                        channel.sendMessage("<@" + author.getIdLong() + ">, You gifted `" + giftAmount + "` peterZ to " + victim.getName() + "! You now have `" + getCurrencyOfUser(author) + "` peterZ!").queue();
                        System.out.println(author.getName() + " Gifted" + giftAmount);
                    } else {
                        channel.sendMessage("You are too poor :(").queue();
                    }
                } else {
                    channel.sendMessage("Cannot gift negative amount!").queue();
                }
            } else {
                channel.sendMessage("Cannot gift to CustomCommands!").queue();
            }
        } else {
            channel.sendMessage("CMD Usage: [PREFIX]gift <@someone> <amount to steal>.\n").queue();
        }
    }
}
