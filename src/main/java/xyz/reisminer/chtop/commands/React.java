package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.reisminer.chtop.commands.DB.SetReact;

public class React {
    public static void setReact(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 2 && (splitMessage[1].toLowerCase().equals("true") || splitMessage[1].toLowerCase().equals("false"))) {
            if (splitMessage[1].toLowerCase().equals("true"))
                SetReact.setReact(true);
            else
                SetReact.setReact(false);
        } else {
            channel.sendMessage("Please use this Command as followed:\n[PREFIX]react [true/false]").queue();
        }
    }
}
