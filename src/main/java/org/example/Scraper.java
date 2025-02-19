package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Scraper {

    //collect all greyhound racing info and creates the object Greyhound
    public static Greyhound collectGreyhoundData(String greyhoundName){
        String formatName = greyhoundName.toLowerCase().replace(" ","-");
        String url = "https://www.thegreyhoundrecorder.com.au/greyhounds/" + formatName + "/";
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Element table = doc.select("table.greyhound-form-racing-results__table").first();
            if (table == null){
                System.out.println("No race results for " + greyhoundName);
                return null;
            }

            Elements rows = table.select("tr");
            ArrayList<Race> raceResults = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++){
                Elements columns = rows.get(i).select("td");
                if (columns.size() < 15) continue;

                Date raceDate = parseDate(columns.get(0).text());

                Race race = new Race(raceDate, columns.get(1).text(),
                        parseInt(columns.get(2).text()), parseInt(columns.get(3).select("img").attr("alt").replace("Rug ", "")),
                        parseInt(columns.get(4).text()), columns.get(5).text(), parseDouble(columns.get(6).text()),
                        parseDouble(columns.get(7).text()), parseDouble(columns.get(8).text()),
                        parseDouble(columns.get(9).text()), columns.get(10).text(), parseDouble(columns.get(11).text()),
                        columns.get(12).text(), columns.get(13).text(),
                        parseDouble(columns.get(14).text().replace("$","")));
                raceResults.add(race);
            }

            return new Greyhound(greyhoundName, raceResults);
        }catch (IOException e) {
            System.err.println("Error!");
            return null;
        }

    }

    //convert to int
    private static int parseInt(String value){
        try{
            value = value.replaceAll("(st|nd|rd|th)$","").trim();
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e){
            return 0;
        }
    }

    //convert to double
    private static double parseDouble(String value){
        try{
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e){
            return 0.0;
        }}

    //convert to Date
    private static Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        try{
            return formatter.parse(dateStr.trim());
        } catch(ParseException e){
            System.err.println("Something went wrong with the date");
            return null;
        }
    }
}
