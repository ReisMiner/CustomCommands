package xyz.reisminer.chtop.slashcommands;

import kotlin.Unit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jraf.klibnotion.client.Authentication;
import org.jraf.klibnotion.client.ClientConfiguration;
import org.jraf.klibnotion.client.NotionClient;
import org.jraf.klibnotion.client.blocking.BlockingNotionClient;
import org.jraf.klibnotion.client.blocking.BlockingNotionClientUtils;
import org.jraf.klibnotion.model.base.reference.DatabaseReference;
import org.jraf.klibnotion.model.block.Block;
import org.jraf.klibnotion.model.block.ParagraphBlock;
import org.jraf.klibnotion.model.block.VideoBlock;
import org.jraf.klibnotion.model.database.query.DatabaseQuery;
import org.jraf.klibnotion.model.database.query.filter.DatabaseQueryPredicate;
import org.jraf.klibnotion.model.database.query.filter.DatabaseQueryPropertyFilter;
import org.jraf.klibnotion.model.date.DateOrDateRange;
import org.jraf.klibnotion.model.date.DateTime;
import org.jraf.klibnotion.model.page.Page;
import org.jraf.klibnotion.model.pagination.Pagination;
import org.jraf.klibnotion.model.pagination.ResultPage;
import org.jraf.klibnotion.model.property.SelectOption;
import org.jraf.klibnotion.model.property.value.DatePropertyValue;
import org.jraf.klibnotion.model.property.value.MultiSelectPropertyValue;
import org.jraf.klibnotion.model.property.value.PropertyValueList;
import org.jraf.klibnotion.model.property.value.TitlePropertyValue;
import org.jraf.klibnotion.model.richtext.RichTextList;
import xyz.reisminer.chtop.Token;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static org.jraf.klibnotion.model.file.FileKt.File;

public class Notion {

    public static void readCalendar(SlashCommandEvent event) {

        if (!Arrays.asList(Token.allowed_notion).contains(event.getMember().getIdLong())) {
            event.reply("You have no permissions to use this cmd cuz u are not in da notion group :))").setEphemeral(true).queue();
            return;
        }

        event.deferReply().queue();

        boolean onlyTest = event.getOption("tests").getAsBoolean();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Calendar Entries");
        eb.setColor(Color.decode("#22AE43"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        NotionClient notionClient = NotionClient.newInstance(
                new ClientConfiguration(
                        new Authentication(Token.NOTION_TOKEN)
                )
        );
        BlockingNotionClient client = BlockingNotionClientUtils.asBlockingNotionClient(notionClient);
        var z = new DateTime(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24), "GMT");

        ResultPage<Page> res = client.getDatabases().queryDatabase("75896b5ca9f948afbce3aa5c51ade569", new DatabaseQuery().all(
                new DatabaseQueryPropertyFilter.Checkbox("Tescht", new DatabaseQueryPredicate.Checkbox(onlyTest)),
                new DatabaseQueryPropertyFilter.Formula("Date", new DatabaseQueryPredicate.Formula.Date.After(z))
        ), null, new Pagination());

        for (var x : res.results) {
            AtomicReference<MessageEmbed.Field> date = new AtomicReference<>(), fach = new AtomicReference<>(), title = new AtomicReference<>();
            ResultPage<Block> blocks = client.getBlocks().getBlockList(x.getId(), new Pagination());
            x.getPropertyValues().forEach(propertyValue -> {
                if (propertyValue instanceof DatePropertyValue) {
                    if (propertyValue.getValue() instanceof DateOrDateRange) {
                        date.set(new MessageEmbed.Field("Date", ((DateOrDateRange) propertyValue.getValue()).component1().getTimestamp().toString(), true));
                    }
                } else if (propertyValue instanceof MultiSelectPropertyValue) {
                    if (propertyValue.getValue() instanceof ArrayList) {
                        AtomicReference<String> s = new AtomicReference<>("");
                        ArrayList<SelectOption> sl = (ArrayList<SelectOption>) propertyValue.getValue();
                        sl.forEach(selectOption -> s.updateAndGet(v -> v + selectOption.getName() + ", "));
                        fach.set(new MessageEmbed.Field("Subject", s.get().substring(0, s.get().length() - 2), true));
                    }

                } else if (propertyValue instanceof TitlePropertyValue) {
                    if (propertyValue.getValue() instanceof RichTextList) {
                        RichTextList tl = (RichTextList) propertyValue.getValue();
                        title.set(new MessageEmbed.Field("Title", "[" + tl.getPlainText() + "](" + x.getUrl() + ")", true));
                    }
                }
            });
            StringBuilder s = new StringBuilder();
            for (Block block : blocks.results) {
                if (block instanceof org.jraf.klibnotion.model.block.ParagraphBlock) {
                    String tmp = ((ParagraphBlock) block).getText().getPlainText();
                    s.append(tmp == null ? "\n" : tmp);
                } else if (block instanceof VideoBlock) {
                    s.append(((VideoBlock) block).getVideo().getUrl());
                }
                s.append("\n");
            }
            eb.addField(title.get()).addField(date.get()).addField(fach.get());
            if (!s.toString().equals("")) {
                eb.addField(new MessageEmbed.Field("Content", s.toString(), false));
            }
        }
        client.close();
        notionClient.close();

        event.getHook().editOriginalEmbeds(eb.build()).queue();

    }

    public static void addToCalendar(SlashCommandEvent event) {

        if (!Arrays.asList(Token.allowed_notion).contains(event.getMember().getIdLong())) {
            event.reply("You have no permissions to use this cmd cuz u are not in da notion group :))").setEphemeral(true).queue();
            return;
        }

        boolean isTest = event.getOption("test").getAsBoolean();
        String content = null;
        if (event.getOption("content") != null)
            content = new String(event.getOption("content").getAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String date = new String(event.getOption("date").getAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String subjects = new String(event.getOption("subjects").getAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String title = new String(event.getOption("title").getAsString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        event.deferReply().queue();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Calendar Entry Added");
        eb.setColor(Color.decode("#22AE43"));
        eb.setFooter("Query performed by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        NotionClient notionClient = NotionClient.newInstance(
                new ClientConfiguration(
                        new Authentication(Token.NOTION_TOKEN)
                )
        );

        BlockingNotionClient client = BlockingNotionClientUtils.asBlockingNotionClient(notionClient);

        String finalContent = content;
        Page createdPageInDatabase = client.getPages().createPage(
                new DatabaseReference("75896b5ca9f948afbce3aa5c51ade569"),
                File("https://cdn.discordapp.com/emojis/742366602720444578.webp"),
                null,
                new PropertyValueList()
                        .date("Date", new DateOrDateRange(new DateTime(parseDate(date), "CET"), null))
                        .title("title", title)
                        .checkbox("Tescht", isTest)
                        .multiSelectByNames("Fach", subjects.split(",")),
                contentValueList -> {
                    if (finalContent != null)
                        for (String hi : finalContent.split(";;")) {
                            contentValueList.paragraph(hi);
                        }
                    contentValueList.paragraph("Created via CHTOP by "+event.getMember().getUser().getAsTag());
                    return Unit.INSTANCE;
                }
        );

        eb.setDescription(createdPageInDatabase.getUrl());
        client.close();
        notionClient.close();
        event.getHook().editOriginalEmbeds(eb.build()).queue();
    }

    private static java.util.Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
