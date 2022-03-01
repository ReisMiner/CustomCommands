package xyz.reisminer.chtop.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import xyz.reisminer.chtop.music.PlayerManager;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Playlist {
    public static void getPlaylist(Message msg) {
        AtomicInteger i = new AtomicInteger();
        AtomicInteger parts = new AtomicInteger(1);
        AtomicBoolean more = new AtomicBoolean(false);
        AtomicReference<String> x = new AtomicReference<>("");
        PlayerManager.getInstance().getMusicManager(msg.getGuild()).scheduler.queue.forEach(audioTrack -> {
            more.set(true);
            String y = x.get() + "\n";
            x.set(y + i + " | " + audioTrack.getInfo().title);
            i.getAndIncrement();
            if (x.get().length() > 1800) {
                msg.getChannel().sendMessage("**Queue Part " + parts.getAndIncrement() + "**\n```" + x.get() + "```").queue();
                x.set("");
                more.set(false);
            }
        });

        AudioTrack currently = PlayerManager.getInstance().getMusicManager(msg.getGuild()).audioPlayer.getPlayingTrack();
        if (currently != null)
            if (parts.get() == 1)
                msg.getChannel().sendMessage("**Queue**\n```" + x.get() + "```\n**Now Playing:**\n`" + currently.getInfo().title + "`").queue();
            else if (more.get())
                msg.getChannel().sendMessage("**Queue Part " + parts.get() + "**\n```" + x.get() + "```\n**Now Playing:**\n`" + currently.getInfo().title + "`").queue();
            else
                msg.getChannel().sendMessage("**Now Playing:**\n`" + currently.getInfo().title + "`").queue();
        else
            msg.getChannel().sendMessage("Playlist empty and no song currently playing!").queue();
    }
}
