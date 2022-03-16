package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.reisminer.chtop.Token;

public class Rename {
    public static void all(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            String[] splitMessage = msg.getContentRaw().split(" ");
            StringBuilder tmp = new StringBuilder(" ");
            if (splitMessage.length >= 2) {
                for (int i = 1; i < splitMessage.length; i++) {
                    tmp.append(splitMessage[i]).append(" ");
                }
                for (Member member : event.getJDA().getGuildById(msg.getGuild().getId()).getMembers()) {
                    try {
                        member.modifyNickname(tmp.toString()).complete();
                    } catch (Exception ignored) {
                        channel.sendMessage("No perms to edit `" + member.getNickname() + "`").queue();
                    }
                }
                Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` changed everyone's name to `" + tmp + "`").queue();

            } else {
                channel.sendMessage("Use this command like this: [PREFIX]renameall [New NAME]").queue();
            }
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to change everyone's name").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }

    public static void single(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            String[] splitMessage = msg.getContentRaw().split(" ");
            msg.delete().complete();
            StringBuilder tmp = new StringBuilder(" ");
            msg.getMentionedMembers();
            if (splitMessage.length >= 3) {
                for (int i = 2; i < splitMessage.length; i++) {
                    tmp.append(splitMessage[i]).append(" ");
                }
                try {
                    msg.getMentionedMembers().get(0).modifyNickname(String.valueOf(tmp)).complete();
                    Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` changed the name of `" + msg.getMentionedMembers().get(0).getUser().getName() + "` to `" + tmp + "`").queue();
                } catch (Exception ignored) {
                    channel.sendMessage("No perms to edit `" + msg.getMentionedMembers().get(0).getNickname() + "`").queue();
                }
            } else {
                channel.sendMessage("Use this command like this: [PREFIX]renameall [New NAME]").queue();
            }
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to change the name of `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }

    public static void reset(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            Token.logChannel.sendMessage(msg.getAuthor().getName() + " reset everyone's name").queue();
            for (Member member : event.getJDA().getGuildById(msg.getGuild().getId()).getMembers()) {
                try {
                    member.modifyNickname("").complete();
                    Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` reset everyone's name").queue();
                } catch (Exception ignored) {
                    channel.sendMessage("No perms to edit `" + member.getNickname() + "`").queue();
                }
            }
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to reset everyone's name").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }
}
