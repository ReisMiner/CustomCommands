package xyz.reisminer.chtop.commands;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Menu {
    static String date = "";
    static boolean gibz_we = false;
    //five moods menu website
    static String url = "https://siemens.sv-restaurant.ch/de/menuplan/five-moods/";
    static String gibz = "https://zfv.ch/de/microsites/restaurant-treff/menuplan";
    static Document document, documentGibz;

    public static void load() {
        try {
            document = Jsoup.connect(url).get();
            documentGibz = Jsoup.connect(gibz).get();
            System.out.println("loaded Sites");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moods(TextChannel channel) {
        if (!LocalDate.now().toString().equals(date)) {
            date = LocalDate.now().toString();
            System.out.println(date + " localdate -> " + LocalDate.now().toString());
            load();
        }
        //sending the message
        channel.sendMessage("```" + getMoods(0) + "\n============================\n"
                + getMoods(1) + "\n============================\n"
                + getMoods(2) + "\n============================\n"
                + getMoods(3) +
                "```").queue();
    }

    public static void gibz(TextChannel channel) {
        int gibz_count = 0;
        gibz_we = false;
        System.out.println(LocalDate.now());
        String mesg = "```";

        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            channel.sendMessage("```lol. du gasch am wucheend id schuel? xD```").queue();
            gibz_we = true;
        }
        try {
            for (gibz_count = 0; gibz_count < gibz_count + 1; gibz_count++) {
                GetGIBZ(gibz_count).id();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(gibz_count);
        }
        if (!gibz_we) {
            for (int j = 0; j < gibz_count; j++) {
                if (GetGIBZ(j).parent().parent().attr("data-date").contains(LocalDate.now().toString())) {
                    mesg += GetGIBZ(j).text() + "\n=============================================\n";
                }
            }
            channel.sendMessage(mesg + "```").queue();
        }
    }

    public static String getMoods(int number) {
        Element body = document.select("body").get(0);
        Element menuline = body.select(".item-content .menuline").get(number);
        Element menu_title = body.select(".item-content .menu-title").get(number);
        Element menu_description = body.select(".item-content .menu-description").get(number);
        Element menu_prices = body.select(".item-content .menu-prices").get(number);
        return menuline.text() + "\n" + menu_title.text() + "\n" + menu_description.text() + "\n" + menu_prices.text();
    }

    public static Element GetGIBZ(int number) {
        Element body = documentGibz.select("body").get(0);
        documentGibz.select(".txt-slide").remove();
        Element menuline = body.select(".menu .txt-hold").get(number);
        return menuline;
    }
}
