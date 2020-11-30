package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;

public class Join {
    public static void join(Message msg){
        Guild guild = msg.getGuild();
        Member member = guild.getMemberById(msg.getAuthor().getId());

        if (member.getVoiceState().inVoiceChannel()) {

            if (msg.getMentionedMembers().isEmpty())
                guild.getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
            else
                guild.getAudioManager().openAudioConnection(msg.getMentionedMembers().get(0).getVoiceState().getChannel());
        }
    }
}
