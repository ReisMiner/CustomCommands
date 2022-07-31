package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.reisminer.chtop.Token;

public class Spam extends Thread {

    private static Message tmpmsg;

    public Spam(Message msg) {
        tmpmsg = msg;
    }

    public static void sayMsgSpam(Message msg) {
        msg.delete().complete();
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();
        int count = 0;
        if (msg.getGuild().getIdLong() != Token.CHEESESERVERID)
            if (splitMessage.length >= 3) {
                if (NumberUtils.isParsable(splitMessage[1])) {
                    count = Integer.parseInt(splitMessage[1]);
                    tmp = new StringBuilder(" ");
                }
                for (int i = 2; i < splitMessage.length; i++) {
                    tmp.append(splitMessage[i]).append(" ");
                }
                for (int i = 0; i < count; i++) {
                    msg.getChannel().sendMessage(tmp).queue();
                }
            } else {
                msg.getChannel().sendMessage("Command Usage: [PREFIX]spam [COUNT] [MESSAGE]").queue();
            }
    }

    public void run() {
        sayMsgSpam(tmpmsg);
    }
}
