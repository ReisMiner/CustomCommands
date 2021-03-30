package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Random;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;
import static xyz.reisminer.chtop.commands.gamble.gambleDB.getCurrencyOfUser;

public class rob {
    public static void steal(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 3 && !msg.getMentionedMembers().isEmpty()) {
            User author = msg.getAuthor();
            User victim = msg.getMentionedMembers().get(0).getUser();

            if (!victim.isBot()) {

                int stealAmount;

                stealAmount = Integer.parseInt(splitMessage[2]);

                if (stealAmount > 0) {

                    if (!userExists(victim)) {
                        addNewUser(victim);
                    }

                    int walletAmountSelf = getCurrencyOfUser(author);
                    int walletAmountVictim = getCurrencyOfUser(victim);

                    if (stealAmount <= walletAmountSelf && walletAmountVictim >= stealAmount) {

                        Random rand = new Random();

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
                    channel.sendMessage("Cannot steal negative amount!").queue();
                }
            } else {
                channel.sendMessage("Cannot rob a Bot!").queue();
            }
        } else {
            channel.sendMessage("CMD Usage: [PREFIX]rob <@someone> <amount to steal>.\n" +
                    "NOTE: You can lose your money too and give it to the other person ;)").queue();
        }
    }
}
