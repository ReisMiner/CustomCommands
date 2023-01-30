package xyz.reisminer.chtop.slashcommands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.reisminer.chtop.Token;
import xyz.reisminer.chtop.commands.DB.SetStuff;

import java.time.LocalDate;
import java.util.EnumSet;

public class SalaryCountdown {
    public static void setupChannel(SlashCommandInteractionEvent event) {

        if (Token.countdownChannel != -1) {
            event.reply("Salary Channel already there!").queue();
            return;
        }

        String newChannelName = "salary-countdown-vc";
        event.getGuild().createVoiceChannel(newChannelName).addPermissionOverride(event.getGuild().getRoleById(777819288005378050L), EnumSet.of(Permission.VOICE_CONNECT), null).queue();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        VoiceChannel vc = (VoiceChannel) event.getGuild().getChannels().stream().filter((x) -> x.getName().equals(newChannelName)).findFirst().get();
        SetStuff.setCountdownChannel(vc.getIdLong());

        updateChannel(event.getJDA());
    }

    public static void updateChannel(JDA jda) {
        VoiceChannel vc = jda.getChannelById(VoiceChannel.class, Token.countdownChannel);
        if (vc == null)
            return;

        int lengthOfMonth = LocalDate.now().lengthOfMonth();
        int dayNow = LocalDate.now().getDayOfMonth();
        int daysLeft = 25 - dayNow;

        LocalDate WEChecker = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 25);
        if (WEChecker.getDayOfWeek().toString().equals("SUNDAY"))
            daysLeft -= 2;
        else if (WEChecker.getDayOfWeek().toString().equals("SATURDAY"))
            daysLeft--;

        if (daysLeft < 0) {
            vc.getManager().setName("days-till-salary: " + (lengthOfMonth - dayNow + 25)).queue();
        } else {
            vc.getManager().setName("days-till-salary: " + daysLeft).queue();
        }
    }
}
