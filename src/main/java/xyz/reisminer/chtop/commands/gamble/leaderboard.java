package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class leaderboard {
    public static void showTopTen(Message msg, MessageChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        if (splitMessage.length == 2) {
            int count = Integer.parseInt(splitMessage[1]);
            channel.sendMessage(gambleDB.getLeaderBoard(count)).queue();
        } else if(splitMessage.length==1) {
            channel.sendMessage(gambleDB.getLeaderBoard(10)).queue();
        }else{
            channel.sendMessage("CMD Usage: [PREFIX]leaderboard <optional: how many users u wanna see>").queue();
        }
    }
}
