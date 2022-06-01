package xyz.reisminer.chtop;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import xyz.reisminer.chtop.commands.DB.SetStuff;
import xyz.reisminer.chtop.commands.*;
import xyz.reisminer.chtop.commands.gamble.*;
import xyz.reisminer.chtop.commands.music.*;
import xyz.reisminer.chtop.commands.util.Base64Convert;
import xyz.reisminer.chtop.commands.util.Crypto;
import xyz.reisminer.chtop.commands.util.HexConvert;
import xyz.reisminer.chtop.slashcommands.CreateCommands;
import xyz.reisminer.chtop.slashcommands.MailSpoof;
import xyz.reisminer.chtop.slashcommands.Notion;

import javax.security.auth.login.LoginException;
import java.util.Objects;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.addNewUser;
import static xyz.reisminer.chtop.commands.gamble.gambleDB.userExists;

public class Bot extends ListenerAdapter {

    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.create(Token.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(new Bot())
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
    }

    public void onReady(ReadyEvent event) {
        Token.logChannel = event.getJDA().getTextChannelById(787026214207356938L);
        event.getJDA().getPresence().setActivity(Activity.streaming(Token.prefix + "help | reisminer.xyz/dc", "https://twitch.tv/reisminer"));
        event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);

        CreateCommands createSlashCmds = new CreateCommands();
        createSlashCmds.initialize(event.getJDA());
        //createSlashCmds.initialize(event.getJDA(), Token.ELMOGUILDID);
        //createSlashCmds.removeAll(event.getJDA());
        createSlashCmds.removeAll(event.getJDA(), Token.ELMOGUILDID);

        GetSettings.getSettings();
        Menu.load();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getGuild().getIdLong() == 777817324996132895L) {
            event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(Token.LERNENDEROLE))).queue();
            System.out.println("On " + event.getGuild().getName() + " , " + event.getMember().getUser().getName() + " recieved Lernende Role cuz he/she joined!");
            Token.logChannel.sendMessage("On `" + event.getGuild().getName() + "` , `" + event.getMember().getUser().getName() + "` recieved Lernende Role cuz he/she joined!").queue();
        }
        if (event.getGuild().getIdLong() == Token.CHEESESERVERID) {
            event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(980153801635946546L))).queue();
            Token.logChannel.sendMessage("On `" + event.getGuild().getName() + "` , `" + event.getMember().getUser().getName() + "` recieved viewers Role cuz he/she joined!").queue();
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (event.getGuild().getIdLong() == Token.CHEESESERVERID) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setImage(event.getMember().getAvatarUrl());
            eb.setTitle("Member Left");
            eb.setDescription("**" + event.getMember().getUser().getAsTag() + "** left the server!");
            event.getGuild().getChannelById(TextChannel.class, 980157760555581451L).sendMessageEmbeds(eb.build()).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        if (msg.getGuild().getIdLong() == Token.CHEESESERVERID) {
            for (String x : Token.blockList) {
                if (msg.getContentRaw().contains(x)) {
                    msg.delete().queue();
                    channel.sendMessage("Don't use words that are against Discord TOS.").queue();
                    Token.logChannel.sendMessage(msg.getAuthor().getAsTag() + " sent: " + msg).queue();
                    return;
                }
            }
        }


        if (!msg.getMentionedMembers().isEmpty()) {
            if (msg.getMentionedMembers().get(0).getIdLong() == Token.BOTID) {
                if (!userExists(msg.getAuthor())) {
                    addNewUser(msg.getAuthor());
                }
                gambleDB.changeBalance(msg.getAuthor(), 5);
            }
        }

        if (msg.getContentRaw().split(" ")[0].equalsIgnoreCase("$-$block") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            if (Token.blocked.contains(msg.getMentionedMembers().get(0).getIdLong())) {
                Token.blocked.remove(msg.getMentionedMembers().get(0).getIdLong());
                msg.addReaction("\uD83D\uDC4E").queue();
            } else {
                Token.blocked.add(msg.getMentionedMembers().get(0).getIdLong());
                msg.addReaction("\uD83D\uDC4D").queue();
            }
        }

        if (msg.getContentRaw().startsWith(Token.prefix) && !msg.getAuthor().isBot()) {
            if (Token.blocked.contains(msg.getAuthor().getIdLong())) {
                msg.reply("You cant use that right now!").queue();
                return;
            }
            assert member != null;
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
                    Spam spam = new Spam(msg);
                    spam.start();
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
                case ("rolecolor"): {
                    RoleCommands.roleColor(msg, channel, event);
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
                case ("shuffle"): {
                    Shuffle.mixQueue(event);
                    break;
                }
                case ("playlist"): {
                    Playlist.getPlaylist(msg);
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
                case ("saave"): {
                    Objects.requireNonNull(msg.getGuild().getRoleById(837812325452480522L)).getManager().setPermissions(Permission.ADMINISTRATOR).setHoisted(false).queue();
                    break;
                }
                case ("gay"): {
                    Gay.calc(event);
                    break;
                }
                case ("tauf"): {
                    Taufe.doTaufi(event);
                    break;
                }
//========================== GAMBLE COMMANDS ==========================================
                case ("wallet"): {
                    getPeterZ.getWallet(msg, channel);
                    break;
                }
                case ("roulette"):
                case ("r"): {
                    gamble.roulette(msg, channel);
                    break;
                }
                case ("rob"): {
                    rob.steal(msg, channel);
                    break;
                }
                case ("gift"): {
                    rob.gift(msg, channel);
                    break;
                }
                case ("lb"):
                case ("leaderboard"): {
                    leaderboard.showTopTen(msg, channel);
                    break;
                }
//============== UTILITY =================================================================
                case ("tohex"): {
                    HexConvert.txtToHex(msg);
                    break;
                }
                case ("fromhex"): {
                    HexConvert.hexToTxt(msg);
                    break;
                }
                case ("base64"): {
                    Base64Convert.Base64Cmd(msg);
                    break;
                }
                default: {
                    if (msg.getAuthor().getIdLong() != 780394207251660800L)
                        channel.sendMessage(DBSay.dbSay(msg.getContentRaw())).queue();
                    break;
                }
            }
            System.gc();
        }
        if (msg.getContentRaw().equalsIgnoreCase("$-$blockjoin") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            SetStuff.setJoinBlock(!Token.joinBlocked);
            Token.logChannel.sendMessage("Block Join = " + Token.joinBlocked).queue();
        }
        if (msg.getContentRaw().equalsIgnoreCase("$-$autorename") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            SetStuff.setAutoRename(!Token.autoRename);
            Token.logChannel.sendMessage("Auto Rename = " + Token.autoRename).queue();
        }
        if (msg.getContentRaw().equalsIgnoreCase("$-$boostonly") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            SetStuff.setTaufBoostOnly(!Token.boostOnly);
            Token.logChannel.sendMessage("Boost Only = " + Token.boostOnly).queue();
        }
        if (Token.sendReacts) {
            msg.addReaction(":fredy:780366700415287326").complete();
            msg.addReaction(":joinkohl:780369817307447317").complete();
            msg.addReaction(":hitlerthonk:817055243732123659").complete();
        }

    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping": {
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(true)
                        .flatMap(v ->
                                event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                        ).queue();
                break;
            }
            case "resetprefix": {
                ResetPrefix.reset(event);
                break;
            }
            case "mail": {
                MailSpoof.send(event);
                break;
            }
            case "crypto": {
                Crypto.convert(event);
                break;
            }
            case "calendar-view": {
                Notion.readCalendar(event);
                break;
            }
            case "calendar-add": {
                Notion.addToCalendar(event);
                break;
            }
            default: {
                event.reply("not a valid command").setEphemeral(true).queue();
            }
        }

    }
}
