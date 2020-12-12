package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.OpenDB;

public class Help {
    public static void help(Message msg, TextChannel channel) {

        if (msg.getContentRaw().equalsIgnoreCase(Token.prefix + "help")) {

            channel.sendMessage("DB Commands: \n" + OpenDB.openDB()).queue();


            channel.sendMessage("Music Commands: " +
                    " | " + Token.prefix + "join" +
                    " | " + Token.prefix + "play"+
                    " | " + Token.prefix + "stop"+
                    " | " + Token.prefix + "skip"+
                    " | " + Token.prefix + "volume"+
                    " | " + Token.prefix + "leave"
            ).queue();
            channel.sendMessage("Annoying Commands: " +
                    " | " + Token.prefix + "dm" +
                    " | " + Token.prefix + "anondm" +
                    " | " + Token.prefix + "spamdm"
            ).queue();
            channel.sendMessage("Text Commands: " +
                    " | " + Token.prefix + "say" +
                    " | " + Token.prefix + "b0ld"
            ).queue();
            channel.sendMessage("Moderator Commands: " +
                    " | " + Token.prefix + "give" +
                    " | " + Token.prefix + "rename" +
                    " | " + Token.prefix + "renameall" +
                    " | " + Token.prefix + "renamereset" +
                    " | " + Token.prefix + "kickrandom" +
                    " | " + Token.prefix + "notsokickrandom"
            ).queue();
            channel.sendMessage("Settings Commands: "+
                    " | " + Token.prefix + "prefix" +
                    " | " + Token.prefix + "react"
            ).queue();
        }
    }
}
