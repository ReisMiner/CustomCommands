package xyz.reisminer.chtop.commands.util;

import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import xyz.reisminer.chtop.Token;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class HexConvert {

    public static void txtToHex(Message msg) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();

        if (splitMessage.length >= 3) {
            if (Objects.equals(splitMessage[1], "text")) {

                for (int i = 2; i < splitMessage.length; i++) {
                    tmp.append(splitMessage[i]).append(" ");
                }
                msg.getChannel().sendMessage(Hex.encodeHexString(tmp.toString().getBytes()).replaceAll("..", "$0 ").toUpperCase()).queue();

            } else if (Objects.equals(splitMessage[1], "decimal")) {

                msg.getChannel().sendMessage(Integer.toHexString(Integer.parseInt(splitMessage[2])).replaceAll("..", "$0 ").toUpperCase()).queue();

            } else if (Objects.equals(splitMessage[1], "float")) {

                msg.getChannel().sendMessage(Float.toHexString(Float.parseFloat(splitMessage[2]))).queue();

            } else {
                msg.getChannel().sendMessage("Command Usage: " + Token.prefix + "tohex <text/decimal/float> [MESSAGE]\nExample: " + Token.prefix + "tohex text example string to be encoded").queue();
            }
        } else {
            msg.getChannel().sendMessage("Command Usage: " + Token.prefix + "tohex <text/decimal/float> [MESSAGE]\nExample: " + Token.prefix + "tohex text example string to be encoded").queue();
        }
    }

    public static void hexToTxt(Message msg) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        StringBuilder tmp = new StringBuilder();

        if (splitMessage.length >= 2) {

            for (int i = 1; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]);
            }
            String output = "**Converted Results**";
            try {
                String hex_text = new String(Hex.decodeHex(tmp.toString()), StandardCharsets.UTF_8);
                output += String.format("\nText: %s", hex_text);
            } catch (DecoderException ignore) {
            }
            try {
                output += String.format("\nDecimal: %d", Integer.parseInt(tmp.toString(), 16));
            } catch (NumberFormatException ignore) {
            }
            try {
                output += String.format("\nFloat: %f\n*float rounding errors can happen. idk why lol. make a contribution to fix it <3. use %sgithub to see the repo!*", Float.parseFloat(tmp.toString()),Token.prefix);
            }catch (NumberFormatException ignore){
            }
            if(output.length()>22){
                msg.getChannel().sendMessage(output).queue();
            }else{
                msg.getChannel().sendMessage("**Couldn't decode the Hex string**").queue();
            }

        } else {
            msg.getChannel().sendMessage("Command Usage: " + Token.prefix + "fromhex [HEX NUMBERS]\nExample: " + Token.prefix + "fromhex 51 52 53").queue();
        }
    }


}
