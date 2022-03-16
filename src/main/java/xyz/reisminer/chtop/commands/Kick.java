package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.reisminer.chtop.Token;

import java.util.Random;

public class Kick {
    public static void Random(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            System.out.println((long) msg.getJDA().getGuildById(msg.getGuild().getId()).getMembers().size());
            Random rand = new Random();
            int memberNum = rand.nextInt(msg.getJDA().getGuildById(msg.getGuild().getId()).getMembers().size());
            if (!msg.getGuild().getMembers().get(memberNum).getUser().isBot() && msg.getGuild().getMembers().get(memberNum).getUser().getIdLong() != Token.REISMINERID) {

                msg.getGuild().kick(msg.getGuild().getMembers().get(memberNum)).complete();

                Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` kicked `" + msg.getGuild().getMembers().get(memberNum).getUser().getName() + " with randomkick`").queue();

                System.out.println(msg.getAuthor().getName() + " Kicked " + msg.getGuild().getMembers().get(memberNum).getUser().getName() + " with randomkick :)");

            } else {
                channel.sendMessage("Cannot kick a bot").queue();
            }
        } else {

            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to use RandomKick").queue();

            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }

    public static void NotRandom(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS) && msg.getMentionedMembers().get(0).getIdLong() != Token.REISMINERID && !msg.getMentionedMembers().get(0).getRoles().contains(event.getGuild().getRoleById(Token.BRATWURSCHTROLE))) {
            msg.getGuild().kick(msg.getMentionedMembers().get(0)).complete();
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` kicked `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
            System.out.println(msg.getAuthor().getName() + " Kicked " + msg.getMentionedMembers().get(0).getUser().getName() + " with notrandomkick :)");
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to kick `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }
}
