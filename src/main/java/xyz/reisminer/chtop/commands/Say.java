package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Say {

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

        if (splitMessage.length < 2) {
            return;
        }

        int start = 3;

        try {
            URL u = null;
            u = new URL(splitMessage[2]);
            Image image = ImageIO.read(u);
            if (image != null) {
                eb.setThumbnail(splitMessage[2]);
            }
        } catch (IOException ignored) {
            start = 2;
        }

        eb.setTitle(splitMessage[1]);

        StringBuilder tmp = new StringBuilder();
        for (int i = start; i < splitMessage.length; i++) {
            tmp.append(splitMessage[i]).append(" ");
        }

        eb.setDescription(tmp.toString());
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
