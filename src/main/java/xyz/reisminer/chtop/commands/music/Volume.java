package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.reisminer.chtop.music.GuildMusicManager;
import xyz.reisminer.chtop.music.PlayerManager;

public class Volume {
    public static void setVolume(Message msg, TextChannel channel) {
        channel.sendMessage("Command Disabled cuz it broke the bot!").queue();

//        String[] splitMessage = msg.getContentRaw().split(" ");
//        if (NumberUtils.isParsable(splitMessage[1])) {
//            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(msg.getGuild());
//            musicManager.scheduler.player.setVolume(Integer.parseInt(splitMessage[1]));
//        } else {
//            channel.sendMessage("Please use the command like that: [PREFIX]volume [NUMBER]").queue();
//        }
    }
}
