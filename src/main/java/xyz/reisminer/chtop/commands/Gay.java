package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class Gay {
    public static void calc(GuildMessageReceivedEvent event) {
        Random rand = new Random();
        EmbedBuilder eb = new EmbedBuilder();
        String[] splitt = event.getMessage().getContentRaw().split(" ");
        if (splitt.length == 2) {
            String user = event.getMessage().getMentionedMembers().get(0).getAsMention();
            eb.setDescription(user + " is **" + rand.nextInt(100) + "%** Gay :rainbow_flag:");
        } else {
            eb.setDescription("You are **" + rand.nextInt(100) + "%** Gay :rainbow_flag:");
        }
        eb.setTitle("Gay Meter");
        eb.setColor(Color.decode("#e67e22"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        event.getMessage().replyEmbeds(eb.build()).queue();
    }
}
