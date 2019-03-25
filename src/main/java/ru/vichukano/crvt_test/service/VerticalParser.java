package ru.vichukano.crvt_test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

/**
 * Class for parsing file with vertical table.
 */
@Component("vertical")
public class VerticalParser implements Parser {

    public VerticalParser() {

    }

    /**
     * Method for converting to item object.
     *
     * @param html text for parsing.
     * @return item object.
     */
    @Override
    public Item convertTextToObject(String html) {
        return new Item(
                this.getPhone(html),
                this.getName(html),
                this.getCompany(html),
                this.getEmail(html)
        );
    }

    /**
     * Method for parsing phone number.
     *
     * @param html text for parsing.
     * @return phone.
     */
    @Override
    public String getPhone(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String phone = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("телефон")
                    || element.text().toLowerCase().contains("phone")
                    || element.text().toLowerCase().contains("номер телефона")
            ) {
                phone = element.nextElementSibling().text();
            }
        }
        return phone;
    }

    /**
     * Method for parsing company name.
     *
     * @param html text for parsing.
     * @return company.
     */
    @Override
    public String getName(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String name = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("фио")
                    || element.text().toLowerCase().contains("ф.и.о.")
                    || element.text().toLowerCase().contains("лицо")
            ) {
                name = element.nextElementSibling().text();
            }
        }
        return name;
    }

    /**
     * Method for parsing company name.
     *
     * @param html text for parsing.
     * @return company.
     */
    @Override
    public String getCompany(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        String company = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("юл")
                    || element.text().toLowerCase().contains("компания")
                    || element.text().toLowerCase().contains("юридическое лицо")
                    || element.text().toLowerCase().contains("ю.л.")
            ) {
                company = element.nextElementSibling().text();
            }
        }
        return company;
    }

    /**
     * Method for parsing email.
     *
     * @param text text for parsing.
     * @return email.
     */
    @Override
    public String getEmail(String text) {
        Document doc = Jsoup.parse(text);
        Elements elements = doc.select("table");
        String email = "";
        for (Element element : elements.select("tr").select("td")) {
            if (element.text().toLowerCase().contains("email")
                    || element.text().toLowerCase().contains("mail")
                    || element.text().toLowerCase().contains("e-mail")
                    || element.text().toLowerCase().contains("почта")
            ) {
                email = element.text();
            }
        }
        return email;
    }

}
