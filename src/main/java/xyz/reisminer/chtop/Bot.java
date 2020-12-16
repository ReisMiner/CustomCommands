package xyz.reisminer.chtop;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import xyz.reisminer.chtop.commands.*;
import xyz.reisminer.chtop.commands.music.*;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder.create(Token.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new Bot())
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
    }

    public void onReady(ReadyEvent event) {
        Token.logChannel= event.getJDA().getTextChannelById(787026214207356938L);
        GetSettings.getSettings();
        event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        Menu.load();
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
                case ("spam"): {
                    Say.sayMsgSpam(msg);
                    break;
                }
                case ("spamdm"): {
                    SpamDM.sendDM(msg,channel,100);
                    break;
                }

                case ("rename"): {
                    Rename.single(msg,channel,event);
                    break;
                }
                case ("renameall"): {
                    Rename.all(msg,channel,event);
                    break;
                }
                case ("renamereset"): {
                    Rename.reset(msg,channel,event);
                    break;
                }
                case ("say"): {
                    Say.sayMsg(msg, channel);
                    break;
                }
                case ("b0ld"): {
                    Say.sayMsgBold(msg, channel);
                    break;
                }
                case ("moods"): {
                    Menu.moods(channel);
                    break;
                }
                case ("gibz"): {
                    Menu.gibz(channel);
                    break;
                }
                case ("give"): {
                    RoleCommands.give(msg,channel,event);
                    break;
                }
                case ("remove"): {
                    RoleCommands.remove(msg,channel,event);
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
                case ("-$reload"): {
                    Reload.reload(msg, channel, event);
                    break;
                }
                case ("leave"): {
                    Leave.leave(msg);
                    break;
                }
                case ("join"): {
                    Join join = new Join(msg);
                    join.start();
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
                case ("kickrandom"): {
                    Kick.Random(msg,channel,event);
                    break;
                }
                case ("notsokickrandom"): {
                    Kick.NotRandom(msg,channel,event);
                    break;
                }
                default: {
                    if (msg.getAuthor().getIdLong() != 780394207251660800L)
                        channel.sendMessage(DBSay.dbSay(msg.getContentRaw())).queue();
                    break;
                }
            }

        }
        if(msg.getContentRaw().equalsIgnoreCase("$-$prefix"))
            ResetPrefix.reset(msg, channel, event);
        if (Token.sendReacts) {
            msg.addReaction(":fredy:780366700415287326").complete();
            msg.addReaction(":joinkohl:780369817307447317").complete();
            msg.addReaction(":drininne:780366363804958721").complete();
        }
    }
}
