package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.SetStuff;

public class ModCmds {

    public static void addBanWord(Message msg, MessageChannel channel, MessageReceivedEvent event) {
        if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            SetStuff.addBanWord(msg.getContentRaw().split(" ")[1]);
        } else {
            Token.logChannel.sendMessage("On `" + msg.getGuild().getName() + "` , `" + msg.getAuthor().getName() + "` tried to add ban word `" + msg.getContentRaw().split(" ")[1] + "`").queue();
            channel.sendMessage("You got no permissions to do so :)").queue();
        }
    }
}
