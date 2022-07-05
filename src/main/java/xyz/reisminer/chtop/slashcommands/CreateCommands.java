package xyz.reisminer.chtop.slashcommands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import xyz.reisminer.chtop.Token;

import java.util.Objects;

public class CreateCommands {

    public void initialize(JDA jda) {
        jda.upsertCommand("ping", "Calculate Ping of the bot!").queue();
        jda.upsertCommand("resetprefix", "Resets the Bot prefix").queue();

        CommandDataImpl mail = new CommandDataImpl("mail", "send emails with every email address you want");
        mail.addOption(OptionType.STRING, "receiver", "to whom you send the email", true);
        mail.addOption(OptionType.STRING, "sender", "the sender email. Optional: Separate the display name with a pipe | Example: Pete|pete@example.com", true);
        mail.addOption(OptionType.STRING, "subject", "the subject of the mail", true);
        mail.addOption(OptionType.STRING, "message", "the message contents. can be html or a url to a raw text e.g https://pastebin.com/raw/ABC1abc", true);
        jda.upsertCommand(mail).queue();

        CommandDataImpl coin = new CommandDataImpl("crypto", "get crypto prices");
        coin.addOption(OptionType.STRING, "coin", "what coin/token you wanna look up", true);
        coin.addOption(OptionType.STRING, "currency", "in which FIAT Currency the value is converted", true);
        jda.upsertCommand(coin).queue();

        CommandDataImpl notion = new CommandDataImpl("calendar-view","Get the notion calendar lool");
        notion.addOption(OptionType.BOOLEAN, "tests", "do you wanna see tests (true) or husi (false)", true);
        jda.upsertCommand(notion).queue();

        CommandDataImpl notion2 = new CommandDataImpl("calendar-add","Add a new entry to the calendar");
        notion2.addOption(OptionType.STRING, "title", "title of the entry", true);
        notion2.addOption(OptionType.BOOLEAN, "test", "is it a test?", true);
        notion2.addOption(OptionType.STRING, "subjects", "what subjects does the entry belong to? separate with , E.G: mati,dütsch", true);
        notion2.addOption(OptionType.STRING, "date", "what date does it take place? Format: DD.MM.YYYY", true);
        notion2.addOption(OptionType.STRING, "content", "additional information. Split individual lines with \\n", false);
        jda.upsertCommand(notion2).queue();

        CommandDataImpl viewerPlay = new CommandDataImpl("viewer-play","Register Yourself to play with me during a stream!");
        viewerPlay.addOption(OptionType.STRING, "dbd-name", "Your DBD Ingame Name", true);
        viewerPlay.addOption(OptionType.STRING, "yt-name", "Your Youtube Chat name", true);
        jda.getGuildById(Token.CHEESESERVERID).upsertCommand(viewerPlay).queue();

        CommandDataImpl viewerPlayViewQueue = new CommandDataImpl("viewer-play-view","Get the current queue.");
        jda.getGuildById(Token.CHEESESERVERID).upsertCommand(viewerPlayViewQueue).queue();
    }

    public void initialize(JDA jda, Long guildID) {
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand("ping", "Calculate Ping of the bot!").queue();
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand("resetprefix", "Resets the Bot prefix").queue();

        CommandDataImpl notion = new CommandDataImpl("calendar-view","Get the notion calendar lool");
        notion.addOption(OptionType.BOOLEAN, "tests", "do you wanna see tests (true) or husi (false)", true);
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand(notion).queue();

        CommandDataImpl mail = new CommandDataImpl("mail", "send emails with every email address you want");
        mail.addOption(OptionType.STRING, "receiver", "to whom you send the email", true);
        mail.addOption(OptionType.STRING, "sender", "the sender email. Optional: Separate the display name with a pipe | Example: Pete|pete@example.com", true);
        mail.addOption(OptionType.STRING, "subject", "the subject of the mail", true);
        mail.addOption(OptionType.STRING, "message", "the message contents. can be html or a link to a raw text e.g pastebin", true);
        jda.getGuildById(guildID).upsertCommand(mail).queue();

        CommandDataImpl coin = new CommandDataImpl("crypto", "get crypto prices");
        coin.addOption(OptionType.STRING, "coin", "what coin/token you wanna look up", true);
        coin.addOption(OptionType.STRING, "currency", "in which FIAT Currency the value is converted", true);
        jda.getGuildById(guildID).upsertCommand(coin).queue();

        CommandDataImpl notion2 = new CommandDataImpl("calendar-add","Add a new entry to the calendar");
        notion2.addOption(OptionType.STRING, "title", "title of the entry", true);
        notion2.addOption(OptionType.BOOLEAN, "test", "is it a test?", true);
        notion2.addOption(OptionType.STRING, "subjects", "what subjects does the entry belong to? separate with , E.G: mathi,Finanz", true);
        notion2.addOption(OptionType.STRING, "date", "what date does it take place? Format: DD.MM.YYYY", true);
        notion2.addOption(OptionType.STRING, "content", "additional information. Split individual lines with ;;", false);
        jda.getGuildById(guildID).upsertCommand(notion2).queue();
    }

    public void removeAll(JDA jda) {
        jda.updateCommands().queue();
    }

    public void removeAll(JDA jda, Long guildID) {
        Objects.requireNonNull(jda.getGuildById(guildID)).updateCommands().queue();
    }

}
