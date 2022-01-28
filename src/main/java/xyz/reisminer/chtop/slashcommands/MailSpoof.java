package xyz.reisminer.chtop.slashcommands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import xyz.reisminer.chtop.EmailModel;
import xyz.reisminer.chtop.Token;

import java.awt.*;
import java.io.IOException;

public class MailSpoof {
    public static void send(SlashCommandEvent event) {
        event.deferReply().queue();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Email Sent");
        eb.setDescription("Email Successfully sent!");
        eb.setColor(Color.decode("#22AE43"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        EmailModel mail = new EmailModel(event.getOption("receiver").getAsString(), event.getOption("sender").getAsString(), event.getOption("subject").getAsString(), event.getOption("message").getAsString());


        Gson gson = new Gson();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://ramon.cc/mailspoof/send/");
        HttpEntity stringEntity = new StringEntity(gson.toJson(mail), ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            try {
                byte[] bytes = response.getEntity().getContent().readAllBytes();
                JsonObject obj = JsonParser.parseString(new String(bytes, "UTF-8")).getAsJsonObject();
                eb.setDescription("Error Code: `" + obj.get("status") + "`\nMessage: " + obj.get("message"));
            } catch (IOException e) {
                e.printStackTrace();

                eb.setDescription("Bot messed up something! Contact the owner `ReisMiner#1111`!");

            }
            eb.setTitle("Email Sender Error");
            eb.setColor(Color.decode("#AF245E"));
        }

        Token.logChannel.sendMessage("On `" + event.getGuild().getName() + "` , `" + event.getMember().getUser().getAsTag() + "` sent a mail with sender `" + mail.sender + "` to `" + mail.receiver
                + "`. Content: `" + mail.message + "`, Subject: `" + mail.subject + "`").queue();

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }
}

