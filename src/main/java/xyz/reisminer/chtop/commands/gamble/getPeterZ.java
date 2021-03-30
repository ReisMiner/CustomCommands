package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class getPeterZ {

    public static void getWallet(Message msg, TextChannel channel) {

        User author = msg.getAuthor();
        if (!msg.getMentionedMembers().isEmpty()) {
            User checkFrom = msg.getMentionedMembers().get(0).getUser();
            if (!userExists(checkFrom)) {
                addNewUser(checkFrom);
            }
            channel.sendMessage("<@" + author.getIdLong() + ">, The Wallet you looked up contains `" + getCurrencyOfUser(checkFrom) + "` peterZ!").queue();
            System.out.println(author.getName() + " Checked " + checkFrom.getName() + "'s wallet.");
        } else {
            if (!userExists(author)) {
                addNewUser(author);
            }
            channel.sendMessage("<@" + author.getIdLong() + ">, Your Wallet contains `" + getCurrencyOfUser(author) + "` peterZ!").queue();
            System.out.println(author.getName() + " Checked their wallet.");
        }
    }
}
