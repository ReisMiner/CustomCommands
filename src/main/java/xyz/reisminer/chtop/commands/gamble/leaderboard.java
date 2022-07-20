package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class leaderboard {
    public static void showTopTen(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");

        Map<Long, Double> result = new HashMap<>();
        if (splitMessage.length == 2) {
            int count = Integer.parseInt(splitMessage[1]);
            result = gambleDB.getLeaderBoard(count);
        } else if(splitMessage.length==1) {
            result = gambleDB.getLeaderBoard(10);
        }else{
            channel.sendMessage("CMD Usage: [PREFIX]leaderboard <optional: how many users u wanna see>").queue();
        }

        if(result == null){
            channel.sendMessage("Database Error!").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Currency Leaderboard");

        StringBuilder sb = new StringBuilder();
        AtomicInteger count = new AtomicInteger(1);
        result.forEach((k,v) ->{
            sb.append(String.format("**%d.** <@%d> - %.0f peterZ\n",count.get(),k,v));
            count.getAndIncrement();
        });
        eb.setDescription(sb.toString());

        channel.sendMessageEmbeds(eb.build()).queue();
    }
}
