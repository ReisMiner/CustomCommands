package xyz.reisminer.chtop;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import nu.pattern.OpenCV;
import org.jetbrains.annotations.NotNull;
import xyz.reisminer.chtop.commands.DB.SetStuff;
import xyz.reisminer.chtop.commands.Menu;
import xyz.reisminer.chtop.commands.*;
import xyz.reisminer.chtop.commands.gamble.*;
import xyz.reisminer.chtop.commands.music.*;
import xyz.reisminer.chtop.commands.util.Base64Convert;
import xyz.reisminer.chtop.commands.util.Crypto;
import xyz.reisminer.chtop.commands.util.HexConvert;
import xyz.reisminer.chtop.commands.util.UserProfile;
import xyz.reisminer.chtop.slashcommands.CreateCommands;
import xyz.reisminer.chtop.slashcommands.MailSpoof;
import xyz.reisminer.chtop.slashcommands.Notion;
import xyz.reisminer.chtop.slashcommands.SalaryCountdown;
import xyz.reisminer.chtop.slashcommands.cheese.ViewerPlay;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static xyz.reisminer.chtop.commands.gamble.gambleDB.addNewUser;
import static xyz.reisminer.chtop.commands.gamble.gambleDB.userExists;

public class Bot extends ListenerAdapter {

    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        OpenCV.loadShared();
        jda = JDABuilder.create(Token.TOKEN, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES).addEventListeners(new Bot()).setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL).build();
        jda.setAutoReconnect(true);
    }

    public void onReady(ReadyEvent event) {
        Token.logChannel = event.getJDA().getTextChannelById(787026214207356938L);
        event.getJDA().getPresence().setActivity(Activity.streaming(Token.prefix + "help | reisminer.xyz/dc", "https://youtube.com/dbdcheese"));
        event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);

        CreateCommands createSlashCmds = new CreateCommands();

        createSlashCmds.initialize(event.getJDA());
        //createSlashCmds.initialize(event.getJDA(), Token.ELMOGUILDID);
        //createSlashCmds.removeAll(event.getJDA());
        createSlashCmds.removeAll(event.getJDA(), Token.ELMOGUILDID);

        GetSettings.getSettings();
        Menu.load();


        Timer salaryTimer = new Timer();
        salaryTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Token.countdownChannel == -1L) {
                    return;
                }
                SalaryCountdown.updateChannel(jda);
            }
        }, 0, 60 * 1000);
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

            EmbedBuilder eb = new EmbedBuilder();
            event.getMember().getUser().openPrivateChannel().queue(channnel -> {
                eb.setTitle("Thank you for joining the DBD Cheese server!");
                eb.setDescription("Keep in mind though that this is not the official milkywaycheese discord server!");
                eb.setColor(Color.green);
                channnel.sendMessageEmbeds(eb.build()).queue();
            });
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (event.getGuild().getIdLong() == Token.CHEESESERVERID) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
            eb.setTitle("Member Left");
            eb.setDescription("**" + event.getMember().getUser().getAsTag() + "** left the server!");
            event.getGuild().getTextChannelById(980157760555581451L).sendMessageEmbeds(eb.build()).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        if (msg.getGuild().getIdLong() == Token.CHEESESERVERID) {
            for (String x : Token.banWhenSend) {
                if (msg.getContentRaw().contains(x) && !msg.getAuthor().isBot()) {
                    msg.getGuild().ban(msg.getJDA().getUserById(msg.getAuthor().getIdLong()), 1, "sending bs").queue();
                    msg.getChannel().sendMessage("banned user " + msg.getAuthor().getAsTag()).queue();
                    jda.getTextChannelById(985267292466204763L).sendMessage("banned for saying: `" + x + "`").queue();
                    return;
                }
            }
            for (String x : Token.blockList) {
                if (msg.getContentRaw().contains(x) && !msg.getAuthor().isBot()) {
                    msg.delete().queue();
                    channel.sendMessage("Don't use words that are against Discord TOS. Use Acronyms Cheese and Choofer!\n If you're looking for where to buy it, use command `$getmilky`").queue();
                    jda.getTextChannelById(985267292466204763L).sendMessage(msg.getAuthor().getAsTag() + " sent: " + msg.getContentRaw().replaceAll(x, "<bad word>")).queue();
                    return;
                }
            }
            if (msg.getContentRaw().equalsIgnoreCase("viewer-play")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setImage("https://cdn.discordapp.com/attachments/684446613028077644/995719534164066385/unknown.png").setDescription("Enter the command with a **/** in front and select the circled command from the selection!\nIf the selection does not appear, you are doing something wrong.").addField("MilkyWay Forum Invite?", "Use Code `R1miner` as invite code on https://milkywaycheese.com", false).setTitle("Wrong Syntax!");
                msg.replyEmbeds(eb.build()).queue();
                return;
            }
        }


        if (!msg.getMentions().getMembers().isEmpty()) {
            if (msg.getMentions().getMembers().get(0).getIdLong() == Token.BOTID) {
                if (!userExists(msg.getAuthor())) {
                    addNewUser(msg.getAuthor());
                }
                gambleDB.changeBalance(msg.getAuthor(), 5);
            }
        }

        if (msg.getContentRaw().split(" ")[0].equalsIgnoreCase("$-$block") && msg.getAuthor().getIdLong() == Token.REISMINERID) {
            if (Token.blocked.contains(msg.getMentions().getMembers().get(0).getIdLong())) {
                Token.blocked.remove(msg.getMentions().getMembers().get(0).getIdLong());
                msg.addReaction(Emoji.fromUnicode("\uD83D\uDC4E")).queue();
            } else {
                Token.blocked.add(msg.getMentions().getMembers().get(0).getIdLong());
                msg.addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queue();
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
                case ("ebsay"): {
                    Say.sayEmbed(msg, channel);
                    break;
                }
                case ("b0ld"): {
                    Say.sayMsgBold(msg, channel);
                    break;
                }
                case ("emotes"): {
                    ListEmotes.listAll(msg, channel);
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
                    new Thread(() -> Taufe.doTaufi(event)).start();
                    break;
                }
                case ("bword"): {
                    ModCmds.addBanWord(msg, channel, event);
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
                case ("adminrob"): {
                    rob.adminRob(msg, channel, event);
                    break;
                }
                case ("admingift"): {
                    rob.adminGift(msg, channel, event);
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
                case ("avatar"): {
                    UserProfile.getAvatar(msg, channel, event);
                    break;
                }
                case ("fakemagik"): {
                    Magik.fakeMagik(msg, channel, event);
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
            new Thread(() -> {
                msg.addReaction(Emoji.fromFormatted(":fredy:780366700415287326")).complete();
                msg.addReaction(Emoji.fromFormatted(":joinkohl:780369817307447317")).complete();
                msg.addReaction(Emoji.fromFormatted(":hitlerthonk:817055243732123659")).complete();
            }).start();

        }

    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping": {
                long time = System.currentTimeMillis();
                event.reply("Pong!").setEphemeral(true).flatMap(v -> event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
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
            case "viewer-play": {
                ViewerPlay.getInstance().register(event);
                break;
            }
            case "viewer-play-view": {
                ViewerPlay.getInstance().viewQueue(event);
                break;
            }
            case "viewer-play-next": {
                ViewerPlay.getInstance().next(event);
                break;
            }
            case "viewer-play-toggle": {
                ViewerPlay.getInstance().toggle(event);
                break;
            }
            case "viewer-play-remove": {
                ViewerPlay.getInstance().remove(event);
                break;
            }
            case "salary-setup": {
                SalaryCountdown.setupChannel(event);
                break;
            }
            default: {
                event.reply("not a valid command").setEphemeral(true).queue();
            }
        }

    }
}
