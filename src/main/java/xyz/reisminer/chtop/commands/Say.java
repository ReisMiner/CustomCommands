package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Say{

    public static void sayMsg(Message msg, MessageChannel channel) {
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

    public static void sayEmbed(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("coming s00n!");
        eb.setThumbnail("https://cdn.discordapp.com/attachments/967361799483695145/1001129427280867339/For_Gohtor.jpg");
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public static void sayMsgBold(Message msg, MessageChannel channel) {
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
}
