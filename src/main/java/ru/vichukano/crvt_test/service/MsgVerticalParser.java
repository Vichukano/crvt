package ru.vichukano.crvt_test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

@Component
public class MsgVerticalParser {

    public MsgVerticalParser() {

    }

    public Item convertHtmlTextToObject(String html) {
        return new Item(
                this.getPhone(html),
                this.getName(html),
                this.getCompany(html)
        );
    }

    private String getPhone(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String phone = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("телефон".toLowerCase())
                    || element.text().contains("phone")
                    || element.text().contains("номер телефона")
            ) {
                phone = element.nextElementSibling().text();
            }
        }
        return phone;
    }

    private String getName(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String name = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("фио".toLowerCase())
                    || element.text().contains("имя")
                    || element.text().contains("лицо")
            ) {
                name = element.nextElementSibling().text();
            }
        }
        return name;
    }

    private String getCompany(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String company = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("юл".toLowerCase())
                    || element.text().contains("компания")
                    || element.text().contains("юридическое лицо")
            ) {
                company = element.nextElementSibling().text();
            }
        }
        return company;
    }

}
