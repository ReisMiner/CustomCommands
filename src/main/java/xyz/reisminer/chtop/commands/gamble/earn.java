package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class earn {
    public static void earn(Message msg, TextChannel channel) {
        channel.sendMessage("COMMAND NOT READY YET!").queue();
    }

    public static void increaseWallet(Message msg, TextChannel channel) {

        String[] splitMessage = msg.getContentRaw().split(" ");

        if (splitMessage.length == 3 && !msg.getMentionedMembers().isEmpty()) {

            User author = msg.getAuthor();
            User mentioneduser = msg.getMentionedMembers().get(0).getUser();

            double wagerdAmount=Double.parseDouble(splitMessage[2]);

            if (!userExists(mentioneduser)) {
                addNewUser(mentioneduser);
            }

            channel.sendMessage(mentioneduser.getName() + " got richer by `" + wagerdAmount + "` peterZ!").queue();
            System.out.println(author.getName() + " Checked their wallet.");
        }else{
            channel.sendMessage("CMD Usage: [PREFIX]walletAdd <@someone> <amount you wanna give>.\n").queue();
        }
    }
}
