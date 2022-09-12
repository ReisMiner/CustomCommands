package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;

public class ListEmotes {

    public static void listAll(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");

        String out = "";
        int count = 0;
        boolean compact = false;

        if (splitMessage.length > 1) {
            if (splitMessage[1].equalsIgnoreCase("compact")) {
                compact = true;
            }
        }

        for (RichCustomEmoji emoji : msg.getGuild().getEmojis()) {
            out += emoji.getAsMention();
            count++;

            if (out.length() > 2000) {
                out = out.substring(0, out.length() - emoji.getAsMention().length());
                channel.sendMessage(out).queue();
                out = emoji.getAsMention();
            }

            if (!compact && count % 27 == 0) {
                channel.sendMessage(out).queue();
                out = "";
            }
        }
        if(out != "")
            channel.sendMessage(out).queue();
        channel.sendMessage("Emote count: **" + count + "**").queue();
    }

}
