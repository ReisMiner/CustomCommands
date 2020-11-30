package xyz.reisminer.chtop.commands.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.math.NumberUtils;

public class Join extends Thread{
    private static Message tmpmsg;
    public Join(Message msg){
        tmpmsg=msg;
    }
    public static void joiin(Message msg) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        Guild guild = msg.getGuild();
        Member member = guild.getMemberById(msg.getAuthor().getId());
        guild.getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
    }
    public static void joiin() {
        String[] splitMessage = tmpmsg.getContentRaw().split(" ");
        Guild guild = tmpmsg.getGuild();
        Member member = guild.getMemberById(tmpmsg.getAuthor().getId());

        if (!tmpmsg.getMentionedMembers().isEmpty())
            if (splitMessage.length > 2) {
                if (NumberUtils.isParsable(splitMessage[2])) {
                    int j = Integer.parseInt(splitMessage[2]);
                    for (int i = 0; i < j; i++) {
                        guild.getAudioManager().openAudioConnection(tmpmsg.getMentionedMembers().get(0).getVoiceState().getChannel());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        guild.getAudioManager().closeAudioConnection();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                guild.getAudioManager().openAudioConnection(tmpmsg.getMentionedMembers().get(0).getVoiceState().getChannel());

            }
        else if (member.getVoiceState().inVoiceChannel()) {
            guild.getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
        }
    }
    public void run(){
        joiin();
    }
}
