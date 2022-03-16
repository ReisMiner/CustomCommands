package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.SetStuff;

public class React {
    public static void setReact(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 2 && (splitMessage[1].equals("true") || splitMessage[1].equals("false"))) {
            SetStuff.setReact(splitMessage[1].equals("true"));

            Token.logChannel.sendMessage("On `"+msg.getGuild().getName()+"` , `"+msg.getAuthor().getName()+"` changed state of react").queue();

        } else {
            channel.sendMessage("Please use this Command as followed:\n[PREFIX]react [true/false]").queue();
        }
    }
}
