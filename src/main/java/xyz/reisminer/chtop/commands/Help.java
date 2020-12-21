package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.OpenDB;

public class Help {
    public static void help(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        try {
            splitMessage[1].getBytes();
        } catch (ArrayIndexOutOfBoundsException e) {
            channel.sendMessage("```" + Token.prefix + "help db\n" +
                    Token.prefix + "help music\n" +
                    Token.prefix + "help annoying\n" +
                    Token.prefix + "help text\n" +
                    Token.prefix + "help menu\n" +
                    Token.prefix + "help moderator\n" +
                    Token.prefix + "help settings```\n"
            ).queue();
            return;
        }
        EmbedBuilder eb = new EmbedBuilder();
        if (splitMessage[1].equalsIgnoreCase("db")) {
            channel.sendMessage("DB Commands: \n" + OpenDB.openDB()).queue();
            eb.setTitle("Add Commands here", "http://reisminer.xyz/dc");
            eb.setDescription("PW: freddy");
            channel.sendMessage(eb.build()).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("music")) {
            channel.sendMessage("```Music Commands: " +
                    " \n " + Token.prefix + "join" +
                    " \n " + Token.prefix + "play" +
                    " \n " + Token.prefix + "stop" +
                    " \n " + Token.prefix + "skip" +
                    " \n " + Token.prefix + "volume" +
                    " \n " + Token.prefix + "leave```"
            ).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("annoying")) {
            channel.sendMessage("```Annoying Commands: " +
                    " \n " + Token.prefix + "dm" +
                    " \n " + Token.prefix + "anondm" +
                    " \n " + Token.prefix + "spam" +
                    " \n " + Token.prefix + "spamdm```"
            ).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("text")) {
            channel.sendMessage("```Text Commands: " +
                    " \n " + Token.prefix + "say" +
                    " \n " + Token.prefix + "b0ld```"
            ).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("menu")) {
            channel.sendMessage("```Menu Commands: " +
                    " \n " + Token.prefix + "moods" +
                    " \n " + Token.prefix + "gibz```"
            ).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("moderator")) {
            channel.sendMessage("```Moderator Commands: " +
                    " \n " + Token.prefix + "give" +
                    " \n " + Token.prefix + "rename" +
                    " \n " + Token.prefix + "renameall" +
                    " \n " + Token.prefix + "renamereset" +
                    " \n " + Token.prefix + "kickrandom" +
                    " \n " + Token.prefix + "notsokickrandom```"
            ).queue();
        }
        if (splitMessage[1].equalsIgnoreCase("settings")) {
            channel.sendMessage("```Settings Commands: " +
                    " \n " + Token.prefix + "prefix" +
                    " \n " + Token.prefix + "react```"
            ).queue();
        }
    }
}
