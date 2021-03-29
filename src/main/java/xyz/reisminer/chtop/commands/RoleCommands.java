package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.reisminer.chtop.Token;

public class RoleCommands {
    public static void give(Message msg, TextChannel channel, GuildMessageReceivedEvent event) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        String tmp = "";
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES) || msg.getAuthor().getIdLong() == 215136536260378624L) {
            msg.delete().complete();
            if (splitMessage.length >= 3) {
                for (int i = 2; i < splitMessage.length; i++) {
                    tmp+=splitMessage[i]+" ";
                }
                Role role = msg.getGuild().getRolesByName(tmp.substring(0,tmp.length()-1), true).get(0);
                try {
                    msg.getGuild().addRoleToMember(msg.getMentionedMembers().get(0), role).complete();
                    Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` gave the role `" + tmp+ "` to `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                channel.sendMessage("Command Usage: [PREFIX]give [@user] [Role]\nNote: Just write the role down. Do not mention it.");
            }
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to give the role `" + tmp + "` to `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }

    public static void remove(Message msg, TextChannel channel, GuildMessageReceivedEvent event) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        String tmp = "";
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            msg.delete().complete();
            if (splitMessage.length >= 3) {
                for (int i = 2; i < splitMessage.length; i++) {
                    tmp+=splitMessage[i]+" ";
                }
                Role role = msg.getGuild().getRolesByName(tmp.substring(0,tmp.length()-1), true).get(0);
                try {
                    msg.getGuild().removeRoleFromMember(msg.getMentionedMembers().get(0), role).complete();
                    Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` removed the role `" + tmp + "` from `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                channel.sendMessage("Command Usage: [PREFIX]give [@user] [Role]\nNote: Just write the role down. Do not mention it.");
            }
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to remove the role `" + tmp + "` to `" + msg.getMentionedMembers().get(0).getUser().getName() + "`").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }
}
