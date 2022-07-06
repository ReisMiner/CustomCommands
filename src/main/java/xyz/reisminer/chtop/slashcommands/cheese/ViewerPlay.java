package xyz.reisminer.chtop.slashcommands.cheese;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import xyz.reisminer.chtop.Token;

import java.awt.*;
import java.util.ArrayList;

public class ViewerPlay {

    private static ArrayList<CheeseViewer> _queue;
    private static ViewerPlay _viewerPlay;
    private static boolean _queueOpen = false;

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

        if (!_queueOpen) {
            event.getHook().editOriginal("Currently, the queue is not open!").queue();
            return;
        }

        String dbd = event.getOption("dbd-name").getAsString();
        String yt = event.getOption("yt-name").getAsString();
        long dcID = event.getMember().getIdLong();

        boolean existent = false;

        if (_queue != null)
            for (CheeseViewer c : _queue) {
                if (c.getDiscordID() == dcID || c.getDbdName().equalsIgnoreCase(dbd) || c.getYtName().equalsIgnoreCase(yt)) {
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
            eb.setDescription("Successfully added `" + dbd + "` to the queue.");
            eb.setColor(Color.decode("#69FF69"));
        }

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }

    public void viewQueue(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        if (!_queueOpen) {
            event.getHook().editOriginal("Currently, the queue is not open!").queue();
            return;
        }

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

    public void next(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        boolean allowed = false;
        for (Role r : event.getMember().getRoles()) {
            if (r.getIdLong() == Token.CHEESEMODROLE) {
                allowed = true;
            }
        }

        if (!allowed) {
            event.getHook().setEphemeral(true).editOriginal("No Permissions!").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Queue Manager**");
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        if ((_queue != null ? _queue.size() : 0) > 0)
            _queue.removeIf(v -> v.getGamesLeft() == 0);

        StringBuilder out = new StringBuilder();
        if ((_queue != null ? _queue.size() : 0) > 0)
            for (int i = 0; i < (Math.min(_queue.size(), 3)); i++) {
                out.append(String.format("<@%d> - **%s** - **%s** - *%d*\n", _queue.get(i).getDiscordID(), _queue.get(i).getDbdName(), _queue.get(i).getYtName(), _queue.get(i).getGamesLeft()));
                _queue.get(i).setGamesLeft(_queue.get(i).getGamesLeft() - 1);
            }

        eb.setDescription("The following people are playing in the next round!\n\nDiscord - DBD - YouTube - Games Left\n\n" + out);

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }

    public void toggle(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        boolean allowed = false;
        for (Role r : event.getMember().getRoles()) {
            if (r.getIdLong() == Token.CHEESEMODROLE) {
                allowed = true;
            }
        }

        if (!allowed) {
            event.getHook().setEphemeral(true).editOriginal("No Permissions!").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Queue Manager**");
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        if (!_queueOpen) {
            _queueOpen = true;
            eb.setDescription("Queue Toggled to **Open**");
            eb.setColor(Color.decode("#69FF69"));
        } else {
            _queueOpen = false;
            _queue.clear();
            eb.setDescription("Queue Toggled to **Closed**");
            eb.setColor(Color.decode("#FF6969"));
        }

        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }
}
