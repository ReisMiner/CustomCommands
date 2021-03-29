package xyz.reisminer.chtop.commands.gamble;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.*;

public class leaderboard {
    public static void showTopTen(TextChannel channel) {
        channel.sendMessage(gambleDB.getLeaderBoard()).queue();
    }
}
