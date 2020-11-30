package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.music.GuildMusicManager;
import xyz.reisminer.chtop.music.PlayerManager;

public class Stop {
    public static void stop(Message msg, TextChannel channel, Member member){
        final Member self = member.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(msg.getGuild());

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        channel.sendMessage("Music Stopped playing").queue();
    }
}
