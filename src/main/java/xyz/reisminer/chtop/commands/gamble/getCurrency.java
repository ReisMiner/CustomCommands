package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class getCurrency {
    public static void sayMsg(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        msg.delete().complete();
        if (splitMessage.length >= 1) {
            for (int i = 1; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }
            channel.sendMessage(tmp).queue();
        }
    }
}
