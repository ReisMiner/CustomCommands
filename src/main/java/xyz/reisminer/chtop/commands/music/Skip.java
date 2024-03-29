package xyz.reisminer.chtop.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.music.GuildMusicManager;
import xyz.reisminer.chtop.music.PlayerManager;

public class Skip {
    public static void skip(MessageChannel channel, Member member, Event event) {
        final Member self = member.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(member.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessageFormat("Skipped Song").queue();

    }
}
