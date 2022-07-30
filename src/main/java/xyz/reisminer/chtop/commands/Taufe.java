package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.reisminer.chtop.Token;

import java.util.ArrayList;
import java.util.List;

public class Taufe {

    public static void doTaufi(MessageReceivedEvent event) {
        Member baby = event.getMessage().getMentions().getMembers().get(0);
        Member sender = event.getGuild().getMemberById(event.getAuthor().getIdLong());
        Long targetID = null;
        List<VoiceChannel> chanels = event.getGuild().getVoiceChannels();

        int delay = 1000;

        if (event.getGuild().getIdLong() == Token.ELMOGUILDID && Token.boostOnly) {
            if (!sender.getRoles().contains(event.getJDA().getRoleById(Token.BRATWURSCHTROLE))) {
                event.getChannel().sendMessage("CHAUF DER NITRO GOPFETAMI UND BOOST DE SERVER!").queue();
                return;
            }
        }

        if (!sender.getVoiceState().inAudioChannel()) {
            event.getChannel().sendMessage("Gang in en Voice <:chanel:930558229073772554>!").queue();
            return;
        }

        targetID = sender.getVoiceState().getChannel().getIdLong();

        if (!baby.getVoiceState().inAudioChannel()) {
            event.getChannel().sendMessage("S Baby isch ned imne Voice <:chanel:930558229073772554>!").queue();
            return;
        }

        for (VoiceChannel vc : chanels) {

            // to not disturb the others ;)
            if (vc.getIdLong() == targetID) {
                continue;
            }

            event.getGuild().moveVoiceMember(baby, vc).queue();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<VoiceChannel> reverseChanels = reverse(chanels);

        for (VoiceChannel vc : reverseChanels) {

            event.getGuild().moveVoiceMember(baby, vc).queue();

            if (vc.getIdLong() == targetID) {
                break;
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static List<VoiceChannel> reverse(List<VoiceChannel> list) {
        List<VoiceChannel> rev = new ArrayList<>();
        for (int i = list.size(); i > 0; i--)
            rev.add(list.get(i - 1));
        return rev;
    }

}
