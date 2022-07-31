package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.reisminer.chtop.Token;

import java.awt.*;

public class SpamDM {
    static EmbedBuilder eb = new EmbedBuilder();

    public static void sendDM(Message msg, MessageChannel channel, int count) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        if ((msg.getMentions().getMembers().get(0).getIdLong() != 215136536260378624L) && msg.getGuild().getIdLong() != Token.CHEESESERVERID) {
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
            boolean error = false;
            msg.getMentions().getMembers().get(0).getUser().openPrivateChannel().queue(channnel -> {
                eb.setTitle("Your DM:", "http://reisminer.xyz");
                eb.setDescription(finalTmp);
                eb.setColor(Color.red);
                eb.setImage(Token.shutImg);
                for (int i = 0; i < finalCount; i++) {
                    if (!error)
                        channnel.sendMessageEmbeds(eb.build()).queue();
                }
            });
            channel.sendMessage("Sending DMs").queue();
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` spammed `" + msg.getMentions().getMembers().get(0).getUser().getName() + "` full with the message: `" + tmp + "`").queue();

        }
    }
}
