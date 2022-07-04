package xyz.reisminer.chtop.slashcommands;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Locale;
import java.util.Map;

public class ViewerPlay {

    public static void register(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String dbd = event.getOption("dbd-name").getAsString();
        String yt = event.getOption("yt-name").getAsString();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Queue Manager**");
        eb.setDescription("Successfully added "+dbd+" to the queue.");
        eb.setColor(Color.decode("#2ecc71"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }
}
