package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.commands.music.Stop;
import xyz.reisminer.chtop.music.GuildMusicManager;
import xyz.reisminer.chtop.music.PlayerManager;

public class Leave {
    public static void leave(Message event) {
        if (event.getGuild().getAudioManager().isConnected()) {
            event.getGuild().getAudioManager().closeAudioConnection();

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
        }
    }
}
