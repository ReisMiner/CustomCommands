package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.math.NumberUtils;

public class Say{

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

    public static void sayMsgBold(Message msg, TextChannel channel) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        tmp.append("**");
        if (splitMessage.length >= 1) {
            for (int i = 1; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }
            tmp.append("**");
            channel.sendMessage(tmp).queue();
        }
    }

    public static void sayMsgSpam(Message msg) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        int count =0;

        if (splitMessage.length >= 3) {
            if (NumberUtils.isParsable(splitMessage[1])) {
                count = Integer.parseInt(splitMessage[1]);
                tmp = new StringBuilder(" ");
            }
            for (int i = 2; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }
            for(int i =0; i<count;i++){
                msg.getChannel().sendMessage(tmp).queue();
            }
        }else{
            msg.getChannel().sendMessage("Command Usage: [PREFIX]spam [COUNT] [MESSAGE]").queue();
        }
    }
}
