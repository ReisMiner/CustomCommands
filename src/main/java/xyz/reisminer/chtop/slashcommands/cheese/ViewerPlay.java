package xyz.reisminer.chtop.slashcommands.cheese;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;

public class ViewerPlay {

    private static ArrayList<CheeseViewer> _queue;
    private static ViewerPlay _viewerPlay;

    private ViewerPlay() {
        _queue = new ArrayList<>();
    }

    public static ViewerPlay getInstance() {
        if (_viewerPlay == null) {
            _viewerPlay = new ViewerPlay();
        }
        return _viewerPlay;
    }

    public void register(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String dbd = event.getOption("dbd-name").getAsString();
        String yt = event.getOption("yt-name").getAsString();
        long dcID = event.getMember().getIdLong();

        boolean existent = false;

        if (_queue != null)
            for (CheeseViewer c : _queue) {
                if (/*c.getDiscordID() == dcID || */c.getDbdName().equalsIgnoreCase(dbd) || c.getYtName().equalsIgnoreCase(yt)) {
                    existent = true;
                    break;
                }
            }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Queue Manager**");
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
        eb.setColor(Color.decode("#FF6969"));
        eb.setDescription("Didnt add `" + dbd + "` to the queue since you're already registered!");
        eb.addField(new MessageEmbed.Field("Hint", "Providing a false dbd in-game name or YouTube channel name can lead to a block!", false));

        if (!existent) {
            _queue.add(new CheeseViewer(dcID, dbd, yt));
            _queue.add(new CheeseViewer(dcID, "aaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaa"));
            _queue.add(new CheeseViewer(dcID, "bbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbb"));
            _queue.add(new CheeseViewer(dcID, "ccccccccc", "ccccccccccccccccccccccccccccc"));
            _queue.add(new CheeseViewer(dcID, "d", "d"));
            _queue.add(new CheeseViewer(dcID, "e", "e"));
            _queue.add(new CheeseViewer(dcID, "f", "f"));
            _queue.add(new CheeseViewer(dcID, "g", "g"));
            eb.setDescription("Successfully added `" + dbd + "` to the queue.");
            eb.setColor(Color.decode("#69FF69"));
        }

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }

    public void viewQueue(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Queue Manager**");
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        StringBuilder out = new StringBuilder();
        int count = 1;
        for (CheeseViewer c : _queue) {
            out.append(String.format("**%d.** <@%d> - **%s** - **%s** - *%d*\n", count, c.getDiscordID(), c.getDbdName(), c.getYtName(), c.getGamesLeft()));
            count++;
        }

        eb.setDescription("Listing the Viewers currently queued up!\n\nDiscord - DBD - YouTube - Games Left\n\n" + out);

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }
}
