package xyz.reisminer.chtop.slashcommands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.Objects;

public class CreateCommands {

    public void initialize(JDA jda) {
        jda.upsertCommand("ping", "Calculate Ping of the bot!").queue();
        jda.upsertCommand("resetprefix", "Resets the Bot prefix").queue();

        CommandData mail = new CommandData("mail", "send emails with every email address you want");
        mail.addOption(OptionType.STRING, "receiver", "to whom you send the email", true);
        mail.addOption(OptionType.STRING, "sender", "the sender email. Optional: Separate the display name with a pipe | Example: Pete|pete@example.com", true);
        mail.addOption(OptionType.STRING, "subject", "the subject of the mail", true);
        mail.addOption(OptionType.STRING, "message", "the message contents. can be html or a url to a raw text e.g https://pastebin.com/raw/ABC1abc", true);
        jda.upsertCommand(mail).queue();

        CommandData coin = new CommandData("crypto", "get crypto prices");
        coin.addOption(OptionType.STRING, "coin", "what coin/token you wanna look up", true);
        coin.addOption(OptionType.STRING, "currency", "in which FIAT Currency the value is converted", true);
        jda.upsertCommand(coin).queue();
    }

    public void initialize(JDA jda, Long guildID) {
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand("ping", "Calculate Ping of the bot!").queue();
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand("resetprefix", "Resets the Bot prefix").queue();

        CommandData notion = new CommandData("calendar","Get the notion calendar lool");
        notion.addOption(OptionType.BOOLEAN, "tests", "do you wanna see tests (true) or husi (false)", true);
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand(notion).queue();

        CommandData mail = new CommandData("mail", "send emails with every email address you want");
        mail.addOption(OptionType.STRING, "receiver", "to whom you send the email", true);
        mail.addOption(OptionType.STRING, "sender", "the sender email. Optional: Separate the display name with a pipe | Example: Pete|pete@example.com", true);
        mail.addOption(OptionType.STRING, "subject", "the subject of the mail", true);
        mail.addOption(OptionType.STRING, "message", "the message contents. can be html or a link to a raw text e.g pastebin", true);
        jda.getGuildById(guildID).upsertCommand(mail).queue();

        CommandData coin = new CommandData("crypto", "get crypto prices");
        coin.addOption(OptionType.STRING, "coin", "what coin/token you wanna look up", true);
        coin.addOption(OptionType.STRING, "currency", "in which FIAT Currency the value is converted", true);
        jda.getGuildById(guildID).upsertCommand(coin).queue();
    }

    public void removeAll(JDA jda) {
        jda.updateCommands().queue();
    }

    public void removeAll(JDA jda, Long guildID) {
        Objects.requireNonNull(jda.getGuildById(guildID)).updateCommands().queue();
    }

}
