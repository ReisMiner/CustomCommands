package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.reisminer.chtop.music.PlayerManager;

public class Play {
    public static void play(Message msg, TextChannel channel, Event event, Member member) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        final Member self = member.getGuild().getSelfMember();
        if (!self.getVoiceState().inVoiceChannel())
            Join.joiin(msg);
        if (splitMessage.length >= 2) {
            PlayerManager.getInstance().loadAndPlay(channel, splitMessage[1]);
            if (splitMessage.length > 2)
                if (NumberUtils.isParsable(splitMessage[2]))
                    PlayerManager.getInstance().getMusicManager(msg.getGuild()).audioPlayer.setVolume(Integer.parseInt(splitMessage[2]));
        } else
            channel.sendMessage("Please use the command like that: [PREFIX]play [YT-URL] [OPTIONAL: INT FOR VOLUME]").queue();
    }
}