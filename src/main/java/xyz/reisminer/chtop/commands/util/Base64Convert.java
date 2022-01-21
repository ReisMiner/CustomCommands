package xyz.reisminer.chtop.commands.util;

import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.codec.binary.Base64;
import xyz.reisminer.chtop.Token;


import java.util.Objects;

public class Base64Convert {
    public static void Base64Cmd(Message msg) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();

        if (splitMessage.length >= 3) {

            for (int i = 2; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }

            if (Objects.equals(splitMessage[1], "encode")) {
                msg.getChannel().sendMessage(new String(Base64.encodeBase64(tmp.toString().getBytes()))).queue();

            } else if (Objects.equals(splitMessage[1], "decode")) {
                msg.getChannel().sendMessage(new String(Base64.decodeBase64(tmp.toString().getBytes()))).queue();

            } else {
                msg.getChannel().sendMessage("Command Usage: " + Token.prefix + "base64 <encode/decode> [MESSAGE]\nExample: " + Token.prefix + "base64 encode example string to be encoded").queue();

            }
        } else {
            msg.getChannel().sendMessage("Command Usage: " + Token.prefix + "base64 <encode/decode> [MESSAGE]\nExample: " + Token.prefix + "base64 encode example string to be encoded").queue();
        }
    }
}
