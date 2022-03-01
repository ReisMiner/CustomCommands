package xyz.reisminer.chtop.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import xyz.reisminer.chtop.music.GuildMusicManager;
import xyz.reisminer.chtop.music.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;


public class Shuffle {
    public static void mixQueue(GuildMessageReceivedEvent event) {

        Member sender = event.getMember();

        if (!sender.getVoiceState().inVoiceChannel()) {
            event.getChannel().sendMessageFormat("Not in a Voice Channel!").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getChannel().getGuild());
        BlockingQueue<AudioTrack> trackQueue = musicManager.scheduler.queue;

        if (trackQueue.size() < 2) {
            event.getChannel().sendMessage("Too few songs in queue!").queue();
            return;
        }

        ArrayList<AudioTrack> x = new ArrayList<>(trackQueue);

        Collections.shuffle(x);

        for (AudioTrack t :musicManager.scheduler.queue) {
            musicManager.scheduler.queue.remove(t);
        }
        musicManager.scheduler.queue.addAll(x);

        event.getChannel().sendMessageFormat("Playlist Shuffled!").queue();

    }
}
