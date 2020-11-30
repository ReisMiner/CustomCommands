package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;

import static xyz.reisminer.chtop.commands.DB.SetPrefix.setPrefix;

public class ResetPrefix {
    public static void reset(Message msg, TextChannel channel, Event event){
        if (msg.getAuthor().getIdLong() == 215136536260378624L) {
            setPrefix("$");
            channel.sendMessage("*Reset prefix to $*").queue();
            event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        } else {
            channel.sendMessage("*NO PERMS U NOOB*").queue();
        }
    }
}
