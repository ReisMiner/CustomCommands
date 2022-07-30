package xyz.reisminer.chtop.commands.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class UserProfile {

    public static void getAvatar(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        String[] splitMessage = msg.getContentRaw().split(" ");

        if (splitMessage.length == 2) {
            User victim = null;
            boolean error = false;
            if (!msg.getMentions().getMembers().isEmpty()) {
                victim = msg.getMentions().getMembers().get(0).getUser();
            } else if (StringUtils.isNumeric(splitMessage[1])) {
                try {
                    victim = msg.getJDA().getUserById(Long.parseLong(splitMessage[1]));
                } catch (Exception ignore) {
                    error = true;
                }
            } else {
                channel.sendMessage("wat da heel is dis input?").queue();
                return;
            }


            if (!error) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Avatar from " + victim.getAsTag());
                eb.setImage(victim.getEffectiveAvatarUrl());
                channel.sendMessageEmbeds(eb.build()).queue();
            } else {
                channel.sendMessage("error. idk why lol.").queue();
            }
        } else {
            channel.sendMessage("Too few arguments (or too many lul)").queue();
        }
    }
}
