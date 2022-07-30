package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import xyz.reisminer.chtop.Token;

import java.awt.*;

public class DM {
    static EmbedBuilder eb = new EmbedBuilder();
    public static void sendDM(Message msg, MessageChannel channel, boolean deleteAuthorMsg){
        String[] splitMessage = msg.getContentRaw().split(" ");
        String tmp = "";
        if(deleteAuthorMsg){
            msg.delete().complete();
        }
        if (splitMessage.length >= 3) {
            for (int i = 0; i < splitMessage.length; i++) {
                if (i >= 2)
                    tmp += splitMessage[i] + " ";
            }
            String finalTmp = tmp;
            msg.getMentions().getMembers().get(0).getUser().openPrivateChannel().queue(channnel -> {
                eb.setTitle("Your DM:","http://reisminer.xyz");
                eb.setDescription(finalTmp);
                eb.setColor(Color.red);
                eb.setImage(Token.shutImg);
                channnel.sendMessageEmbeds(eb.build()).queue();
            });
        }

        Token.logChannel.sendMessage("On `"+msg.getGuild().getName()+"` , `"+msg.getAuthor().getName()+"` sent a dm to `"+ msg.getMentions().getMembers().get(0).getUser().getName()+"`").queue();
        channel.sendMessage("DM sent").queue();
    }
}
