package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.OpenDB;

public class Help {
    public static void help(Message msg, TextChannel channel) {
        if (msg.getContentRaw().equalsIgnoreCase(Token.prefix + "help")) {
            channel.sendMessage("DB Commands: \n" + OpenDB.openDB()).queue();
            channel.sendMessage("Static Commands: " +
                    "\n" + Token.prefix + "dm" +
                    "\n" + Token.prefix + "anondm" +
                    "\n" + Token.prefix + "spamdm" +
                    "\n" + Token.prefix + "join" +
                    "\n" + Token.prefix + "play"+
                    "\n" + Token.prefix + "stop"+
                    "\n" + Token.prefix + "skip"+
                    "\n" + Token.prefix + "volume"+
                    "\n" + Token.prefix + "leave" +
                    "\n" + Token.prefix + "say" +
                    "\n" + Token.prefix + "prefix" +
                    "\n" + Token.prefix + "react"
            ).queue();
        }
    }
}
