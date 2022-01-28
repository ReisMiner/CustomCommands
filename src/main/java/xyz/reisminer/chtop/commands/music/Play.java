package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.music.PlayerManager;

public class Play {
    public static void play(Message msg, TextChannel channel, Event event, Member member) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        String search = "";
        for (int i = 1; i < splitMessage.length; i++) {
            search += splitMessage[i];
        }
        final Member self = member.getGuild().getSelfMember();
        if (!self.getVoiceState().inVoiceChannel())
            Join.joiin(msg);
        if (splitMessage.length >= 2) {
            PlayerManager.getInstance().loadAndPlay(channel, search);
        } else
            channel.sendMessage("Please use the command like that: " + Token.prefix + "play [YT-URL]\nAlternative method: "+Token.prefix+"play ytsearch:[SEARCH STRING]").queue();
    }
}