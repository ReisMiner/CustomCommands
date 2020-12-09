package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;

import java.util.Random;

public class Kick {
    public static void Random(Message msg, TextChannel channel, Event event) {
        System.out.println((long) msg.getJDA().getGuildById(msg.getGuild().getId()).getMembers().size());
        Random rand = new Random();
        int memberNum = rand.nextInt(msg.getJDA().getGuildById(msg.getGuild().getId()).getMembers().size());
        System.out.println("Randomnum="+memberNum);
        if (!msg.getGuild().getMembers().get(memberNum).getUser().isBot() && msg.getGuild().getMembers().get(memberNum).getUser().getIdLong() != 215136536260378624L) {
            msg.getGuild().kick(msg.getGuild().getMembers().get(memberNum)).complete();
            Token.logChannel.sendMessage(msg.getAuthor().getName()+" Kicked `"+msg.getGuild().getMembers().get(memberNum).getUser().getName()+"` with randomkick :)").queue();
            System.out.println(msg.getAuthor().getName()+" Kicked "+msg.getGuild().getMembers().get(memberNum).getUser().getName()+" with randomkick :)");
        }else{
            channel.sendMessage("Cannot kick a bot").queue();
        }
    }
    public static void NotRandom(Message msg, TextChannel channel, Event event) {
        msg.getGuild().kick(msg.getMentionedMembers().get(0)).complete();
        Token.logChannel.sendMessage(msg.getAuthor().getName()+" Kicked `"+msg.getMentionedMembers().get(0).getUser().getName()+"` with randomkick :)").queue();
        System.out.println(msg.getAuthor().getName()+" Kicked "+msg.getMentionedMembers().get(0).getUser().getName()+" with notrandomkick :)");
    }
}
