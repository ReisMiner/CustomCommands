package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.security.SecureRandom;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class gamble {

    public static void roulette(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 2) {

            User author = msg.getAuthor();

            int wagerdAmount;

            if (splitMessage[1].equalsIgnoreCase("max")) {
                wagerdAmount = getCurrencyOfUser(author);
            } else if (splitMessage[1].equalsIgnoreCase("half")) {
                wagerdAmount = getCurrencyOfUser(author) / 2;
            } else {
                wagerdAmount = Integer.parseInt(splitMessage[1]);
            }

            if (wagerdAmount > 0) {

                if (!userExists(author)) {
                    addNewUser(author);
                }

                int walletAmount = getCurrencyOfUser(author);

                if (wagerdAmount <= walletAmount) {

                    SecureRandom rand = new SecureRandom();

                    int n = rand.nextInt(101);
                    System.out.println(n);
                    if (n < 45) {
                        changeBalance(author, -wagerdAmount);
                        channel.sendMessage("<@" + author.getIdLong() + ">, You Lost `" + wagerdAmount + "` peterZ! You now have `" + getCurrencyOfUser(author) + "` peterZ!").queue();
                        System.out.println(author.getName() + " Gambled and lost " + wagerdAmount);
                    } else {
                        changeBalance(author, wagerdAmount);
                        channel.sendMessage("<@" + author.getIdLong() + ">, You Won `" + wagerdAmount + "` peterZ! You now have `" + getCurrencyOfUser(author) + "` peterZ!").queue();
                        System.out.println(author.getName() + " Gambled and won " + wagerdAmount);
                    }
                } else {
                    channel.sendMessage("You are too poor! You wagered `" + wagerdAmount + "` but only have `" + walletAmount + "` peterZ in your Wallet!").queue();
                }
            } else {
                channel.sendMessage("Cannot wager negative amount!").queue();
            }
        } else {
            channel.sendMessage("CMD Usage: [PREFIX]roulette <amount to wager>.\n" +
                    "CMD Usage: [PREFIX]roulette max.").queue();
        }
    }
}
