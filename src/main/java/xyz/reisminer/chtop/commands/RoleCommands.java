package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class RoleCommands {
    public static void give(Message msg, TextChannel channel) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 3) {
            net.dv8tion.jda.api.entities.Role role = msg.getGuild().getRolesByName(splitMessage[2], true).get(0);
            try {
                msg.getGuild().addRoleToMember(msg.getMentionedMembers().get(0), role).complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            channel.sendMessage("Command Usage: [PREFIX]give [@user] [Role]\nNote: Just write the role down. Do not mention it.");
        }
    }

    public static void remove(Message msg, TextChannel channel) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 3) {
            net.dv8tion.jda.api.entities.Role role = msg.getGuild().getRolesByName(splitMessage[2], true).get(0);
            try {
                msg.getGuild().removeRoleFromMember(msg.getMentionedMembers().get(0), role).complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            channel.sendMessage("Command Usage: [PREFIX]give [@user] [Role]\nNote: Just write the role down. Do not mention it.");
        }
    }
}
