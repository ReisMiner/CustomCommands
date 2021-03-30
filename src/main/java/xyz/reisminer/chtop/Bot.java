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
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import xyz.reisminer.chtop.commands.*;
import xyz.reisminer.chtop.commands.DB.SetStuff;
import xyz.reisminer.chtop.commands.gamble.*;
import xyz.reisminer.chtop.commands.music.*;

import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.addNewUser;
import static xyz.reisminer.chtop.commands.gamble.gambleDB.userExists;

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
        Token.logChannel = event.getJDA().getTextChannelById(787026214207356938L);
        GetSettings.getSettings();
        event.getJDA().getPresence().setActivity(Activity.playing(Token.prefix + "help | reisminer.xyz/dc"));
        Menu.load();

        Timer timer = new Timer();
        Member me = event.getJDA().getGuildById(777817324996132895L).getMemberById(Token.REISMINERID);
        Member sebi = event.getJDA().getGuildById(777817324996132895L).getMemberById(485407839095881749L);
        Member jannis = event.getJDA().getGuildById(777817324996132895L).getMemberById(406837798755368980L);

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (Token.autoRename) {
                    try {
                        if (!sebi.getNickname().equalsIgnoreCase("xX_oMeGaBoOmErBoi_Xx_4k420hzLP") || sebi.getUser().getName().equals(sebi.getNickname())) {
                            sebi.modifyNickname("xX_oMeGaBoOmErBoi_Xx_4k420hzLP").queue();
                        }
                    } catch (Exception e) {
                        try {
                            sebi.modifyNickname("xX_oMeGaBoOmErBoi_Xx_4k420hzLP").queue();
                        } catch (Exception ignored) {
                        }
                    }
                    try {
                        if (!jannis.getNickname().equalsIgnoreCase("spastbun") || jannis.getUser().getName().equals(jannis.getNickname())) {
                            jannis.modifyNickname("spastbun").queue();
                        }
                    } catch (Exception e) {
                        try {
                            jannis.modifyNickname("spastbun").queue();
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }, 0, 10000);

    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();
        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        if (!msg.getMentionedMembers().isEmpty()) {
            if (msg.getMentionedMembers().get(0).getIdLong() == Token.BOTID) {
                if (!userExists(msg.getAuthor())) {
                    addNewUser(msg.getAuthor());
                }
                gambleDB.changeBalance(msg.getAuthor(), 5);
            }
        }

        if (msg.getContentRaw().startsWith(Token.prefix)) {
            switch (msg.getContentRaw().split(" ")[0].substring(Token.prefix.length()).toLowerCase()) {
                case ("help"): {
                    Help.help(msg, channel);
                    break;
                }
                case ("dm"): {
                    DM.sendDM(msg, channel, false);
                    break;
                }
                case ("anondm"): {
                    DM.sendDM(msg, channel, true);
                    break;
                }
                case ("spam"): {
                    Say.sayMsgSpam(msg);
                    break;
                }
                case ("spamdm"): {
                    SpamDM.sendDM(msg, channel, 100);
                    break;
                }

                case ("rename"): {
                    Rename.single(msg, channel, event);
                    break;
                }
                case ("renameall"): {
                    Rename.all(msg, channel, event);
                    break;
                }
                case ("renamereset"): {
                    Rename.reset(msg, channel, event);
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
                case ("trattoria"): {
                    Menu.trattoria(channel);
                    break;
                }
                case ("give"): {
                    RoleCommands.give(msg, channel, event);
                    break;
                }
                case ("remove"): {
                    RoleCommands.remove(msg, channel, event);
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
                    if (!Token.joinBlocked) {
                        Join join = new Join(msg);
                        join.start();
                    } else {
                        msg.getChannel().sendMessage("**Join got blocked**").queue();
                    }
                    break;
                }
                case ("play"): {
                    Play.play(msg, channel, event, member);
                    break;
                }
                case ("skip"): {
                    Skip.skip(channel, member, event);
                    break;
                }
                case ("stop"): {
                    Stop.stop(msg, channel, member);
                    break;
                }
                case ("volume"): {
                    Volume.setVolume(msg, channel);
                    break;
                }
                case ("kickrandom"): {
                    Kick.Random(msg, channel, event);
                    break;
                }
                case ("notsokickrandom"): {
                    Kick.NotRandom(msg, channel, event);
                    break;
                }
//========================== GAMBLE COMMANDS ==========================================
                case ("wallet"): {
                    getPeterZ.getWallet(msg, channel);
                    break;
                }
                case ("roulette"): {
                    gamble.roulette(msg, channel);
                    break;
                }
                case ("rob"): {
                    rob.steal(msg, channel);
                    break;
                }
                case ("leaderboard"): {
                    leaderboard.showTopTen(channel);
                    break;
                }
                case ("earn"): {
                    earn.earn(msg, channel);
                    break;
                }
                default: {
                    if (msg.getAuthor().getIdLong() != 780394207251660800L)
                        channel.sendMessage(DBSay.dbSay(msg.getContentRaw())).queue();
                    break;
                }
            }

        }
        if (msg.getContentRaw().equalsIgnoreCase("$-$prefix"))
            ResetPrefix.reset(msg, channel, event);
        if (msg.getContentRaw().equalsIgnoreCase("$-$blockjoin") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            SetStuff.setJoinBlock(!Token.joinBlocked);
            Token.logChannel.sendMessage("Successfully (un)blocked the join command").queue();
        }
        if (msg.getContentRaw().equalsIgnoreCase("$-$autorename") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            SetStuff.setAutoRename(!Token.autoRename);
            Token.logChannel.sendMessage("Successfully toggled auto rename").queue();
        }
        if (Token.sendReacts) {
            msg.addReaction(":fredy:780366700415287326").complete();
            msg.addReaction(":joinkohl:780369817307447317").complete();
            msg.addReaction(":drininne:780366363804958721").complete();
        }
    }
}
