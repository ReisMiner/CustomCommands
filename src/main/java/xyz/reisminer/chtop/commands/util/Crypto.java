package xyz.reisminer.chtop.commands.util;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.util.Locale;
import java.util.Map;


public class Crypto {
    public static void convert(SlashCommandEvent event) {
        event.deferReply().queue();
        String crypto = event.getOption("coin").getAsString().toLowerCase(Locale.ROOT);
        String fiat = event.getOption("currency").getAsString();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**" + crypto + " Value**");
        eb.setDescription("Could not fetch the coin.\nMake sure to provide the FIAT currency as 3 Letters. E.g `USD`\nThe Crypto Coin has to be fully written though. E.g `Bitcoin`");
        eb.setColor(Color.decode("#2ecc71"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        Map<String, Map<String, Double>> res = client.getPrice(crypto, fiat);

        String val = "";
        try {
            val = res.get(crypto).get(fiat).toString();
            eb.setDescription("1 " + crypto + " is **" + val + "** " + fiat + "!");
        } catch (Exception e) {
            eb.setTitle("Error Fetching Coin");
            eb.addField("Available Coins", "https://api.coingecko.com/api/v3/coins/list",false);
            eb.addField("API Docs", "https://www.coingecko.com/en/api/documentation",false);
        }

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }
}
