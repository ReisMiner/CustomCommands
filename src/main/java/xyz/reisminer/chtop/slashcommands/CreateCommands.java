package xyz.reisminer.chtop.slashcommands;

import net.dv8tion.jda.api.JDA;

import java.util.Objects;

public class CreateCommands {

    public void initialize(JDA jda){
        jda.upsertCommand("ping","Calculate Ping of the bot!").queue();
    }

    public void initialize(JDA jda, Long guildID){
        Objects.requireNonNull(jda.getGuildById(guildID)).upsertCommand("ping","Calculate Ping of the bot!").queue();
    }

    public void removeAll(JDA jda){
        jda.updateCommands().queue();
    }
    public void removeAll(JDA jda, Long guildID){
        Objects.requireNonNull(jda.getGuildById(guildID)).updateCommands().queue();
    }

}
