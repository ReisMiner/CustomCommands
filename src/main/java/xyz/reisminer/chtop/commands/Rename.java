package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class Rename {
    public static void all(Message msg, TextChannel channel) {
        String[] splitMessage = msg.getContentRaw().split(" ");
        System.out.println((long) msg.getGuild().getMembers().size());
        StringBuilder tmp = new StringBuilder(" ");
        if (splitMessage.length >= 2) {
            for (int i = 1; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }
            for (Member member : msg.getGuild().getMembers()) {
                try {
                    member.modifyNickname(tmp.toString()).complete();
                } catch (Exception ignored) {
                    channel.sendMessage("No perms to edit `"+member.getNickname()+"`").queue();
                }
            }
        }else{
            channel.sendMessage("Use this command like this: [PREFIX]renameall [New NAME]").queue();
        }
    }
    public static void single(Message msg,TextChannel channel){
        String[] splitMessage = msg.getContentRaw().split(" ");
        msg.delete().complete();
        StringBuilder tmp = new StringBuilder(" ");
        msg.getMentionedMembers();
        if(splitMessage.length >= 3){
            for (int i = 2; i < splitMessage.length; i++) {
                tmp.append(splitMessage[i]).append(" ");
            }
            try {
                msg.getMentionedMembers().get(0).modifyNickname(String.valueOf(tmp)).complete();
            } catch (Exception ignored) {
                channel.sendMessage("No perms to edit `"+msg.getMentionedMembers().get(0).getNickname()+"`").queue();
            }
        }else{
            channel.sendMessage("Use this command like this: [PREFIX]renameall [New NAME]").queue();
        }
    }
}
