package xyz.reisminer.chtop.commands.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import xyz.reisminer.chtop.EmailModel;
import xyz.reisminer.chtop.Token;

import java.awt.*;
import java.io.IOException;
import java.util.Currency;
import java.util.Map;


public class Crypto {
    public static void convert(SlashCommandEvent event) {
        event.deferReply().queue();
        String crypto = event.getOption("coin").getAsString();
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
