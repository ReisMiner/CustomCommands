package xyz.reisminer.chtop;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import xyz.reisminer.chtop.commands.*;
import xyz.reisminer.chtop.commands.music.*;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder.create(Token.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new Bot())
                .build();
    }

    public void onReady(ReadyEvent event) {
        GetSettings.getSettings();
        event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();
        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        if (msg.getContentRaw().startsWith(Token.prefix)) {
            switch (msg.getContentRaw().split(" ")[0].substring(Token.prefix.length()).toLowerCase()) {
                case ("help"): {
                    Help.help(msg, channel);
                    break;
                }
                case ("dm"): {
                    DM.sendDM(msg,channel,false);
                    break;
                }
                case ("anondm"): {
                    DM.sendDM(msg, channel,true);
                    break;
                }
                case ("say"): {
                    Say.sayMsg(msg, channel);
                    break;
                }
                case ("react"): {
                    React.setReact(msg, channel);
                    break;
                }
                case ("prefix"): {
                    Prefix.setPrefix(msg, channel, event);
                    break;
                }
                case ("-$prefix"): {
                    ResetPrefix.reset(msg, channel, event);
                    break;
                }
                case ("-$reload"): {
                    Reload.reload(msg, channel, event);
                    break;
                }
                case ("leave"): {
                    Leave.leave(msg);
                    break;
                }
                case ("join"): {
                    Join.join(msg);
                    break;
                }
                case ("play"): {
                    Play.play(msg,channel,event,member);
                    break;
                }
                case ("skip"): {
                    Skip.skip(channel,member,event);
                    break;
                }
                case ("stop"): {
                    Stop.stop(msg, channel, member);
                    break;
                }
                case ("volume"): {
                    Volume.setVolume(msg,channel);
                    break;
                }


                default: {
                    if (msg.getAuthor().getIdLong() != 780394207251660800L)
                        channel.sendMessage(DBSay.dbSay(msg.getContentRaw())).queue();
                    break;
                }
            }
        }
        if (Token.sendReacts) {
            msg.addReaction(":fredy:780366700415287326").complete();
            msg.addReaction(":joinkohl:780369817307447317").complete();
            msg.addReaction(":drininne:780366363804958721").complete();
        }
    }
}
