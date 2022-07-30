package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class getPeterZ {

    public static void getWallet(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        User author = msg.getAuthor();
        if (splitMessage.length == 2) {
            User checkFrom = null;
            boolean error = false;
            if (!msg.getMentions().getMembers().isEmpty()) {
                checkFrom = msg.getMentions().getMembers().get(0).getUser();
            } else if (StringUtils.isNumeric(splitMessage[1])) {
                try {
                    checkFrom = msg.getJDA().getUserById(Long.parseLong(splitMessage[1]));
                } catch (Exception ignore) {
                    error = true;
                }
            } else {
                checkFrom = getUserByName(splitMessage[1], msg);
                try {
                    checkFrom.getIdLong();
                } catch (NullPointerException e) {
                    error = true;
                }
            }

            if(error){
                channel.sendMessage("Error looking up balance!").queue();
                return;
            }

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
