package xyz.reisminer.chtop.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import xyz.reisminer.chtop.music.PlayerManager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Playlist {
    public static void getPlaylist(Message msg) {
        AtomicInteger i = new AtomicInteger();
        AtomicReference<String> x = new AtomicReference<>("");
        PlayerManager.getInstance().getMusicManager(msg.getGuild()).scheduler.queue.forEach(audioTrack -> {
            String y = x.get() + "\n";
            x.set(y + i + " | " + audioTrack.getInfo().title);
            i.getAndIncrement();
        });

        AudioTrack currently = PlayerManager.getInstance().getMusicManager(msg.getGuild()).audioPlayer.getPlayingTrack();
        if (currently != null)
            msg.getChannel().sendMessage("**Now Playing:**\n`" + currently.getInfo().title + "`\n**Queue**\n```" + x.get() + "```").queue();
        else
            msg.getChannel().sendMessage("Playlist empty and no song currently playing!").queue();
    }
}
