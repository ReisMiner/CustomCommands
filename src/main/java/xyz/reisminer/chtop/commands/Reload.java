package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;

import static xyz.reisminer.chtop.GetSettings.getSettings;

public class Reload {
    public static void reload(Message msg, MessageChannel channel, Event event){
        if (msg.getAuthor().getIdLong() == Token.REISMINERID) {
            getSettings();
            Menu.load();
            channel.sendMessage("*Reloaded Settings And Menus*").queue();
            event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        } else {
            Token.logChannel.sendMessage("On `"+msg.getGuild().getName()+"` , `"+msg.getAuthor().getName()+"` tried to reload the bot").queue();

            channel.sendMessage("*NO PERMS U NOOB*").queue();
        }
    }
}
