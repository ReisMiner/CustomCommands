package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;

import static xyz.reisminer.chtop.GetSettings.getSettings;

public class Reload {
    public static void reload(Message msg, TextChannel channel, Event event){
        if (msg.getAuthor().getIdLong() == 215136536260378624L) {
            getSettings();
            channel.sendMessage("*Reloaded Settings*").queue();
            event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        } else {
            channel.sendMessage("*NO PERMS U NOOB*").queue();
        }
    }
}
