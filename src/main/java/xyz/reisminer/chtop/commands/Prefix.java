package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.SetStuff;

public class Prefix {
    public static void setPrefix(Message msg, TextChannel channel, Event event) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 2) {
            SetStuff.setPrefix(splitMessage[1]);
            event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));

            Token.logChannel.sendMessage("On `"+msg.getGuild().getName()+"` , `"+msg.getAuthor().getName()+"` changed the Prefix").queue();

        } else {
            channel.sendMessage("Please use this Command as followed:\n[OLDPREFIX]prefix [NEWPREFIX]").queue();
        }
    }
}
