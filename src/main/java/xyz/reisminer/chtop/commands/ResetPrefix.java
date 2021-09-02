package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.SetStuff;

import java.util.Objects;


public class ResetPrefix {
    public static void reset(SlashCommandEvent event){

        if (Objects.requireNonNull(event.getMember()).getIdLong() == 215136536260378624L) {
            SetStuff.setPrefix("$");
            event.reply("Reset prefix to **$**").queue();
            event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        } else {
            event.reply("**U GOT NO PERMS LOOOL** <a:kekg:823836339694600192>").queue();
        }
    }
}
