package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.reisminer.chtop.Token;

import java.awt.*;

public class SpamDM {
    static EmbedBuilder eb = new EmbedBuilder();

    public static void sendDM(Message msg, TextChannel channel, int count) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        if ((msg.getMentionedUsers().get(0).getIdLong() != 15136536260378624L) && (msg.getMentionedUsers().get(0).getIdLong() != 286888695879958530L) && (msg.getMentionedUsers().get(0).getIdLong() != 384396707305357313L)) {
            if (NumberUtils.isParsable(splitMessage[2])) {
                count = Integer.parseInt(splitMessage[2]);
                tmp = new StringBuilder(" ");
            }
            for (int i = 2; i < splitMessage.length; i++) {
                if (tmp.toString().equals(" ")) {
                    i++;
                }
                tmp.append(splitMessage[i]).append(" ");
            }
            String finalTmp = tmp.toString();
            int finalCount = count;
            msg.getMentionedMembers().get(0).getUser().openPrivateChannel().queue(channnel -> {
                eb.setTitle("Your DM:", "http://reisminer.xyz");
                eb.setDescription(finalTmp);
                eb.setColor(Color.red);
                eb.setImage(Token.shutImg);
                for (int i = 0; i < finalCount; i++)
                    channnel.sendMessage(eb.build()).queue();
            });
            channel.sendMessage("Sending DMs").queue();
        }
    }
}
