package ru.vichukano.crvt_test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

@Component
public class MsgHorizontalParser implements Parser {
    private final Log LOG = LogFactory.getLog(MsgHorizontalParser.class);

    public MsgHorizontalParser() {

    }

    @Override
    public String getPhone(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = -1;
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("phone")
                    || element.text().toLowerCase().contains("телефон")) {
                index = element.elementSiblingIndex();
                LOG.info(index);
            }
        }
        if (index == -1) {
            return "";
        }
        return elements.select("tr").get(1).select("td").get(index).text();
    }

    /**
     * Ищем поле с компанией, если нет, то пустая строка.
     *
     * @param html
     * @return
     */
    @Override
    public String getCompany(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = -1;
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("компания")
                    || element.text().toLowerCase().contains("фирма")
                    || element.text().toLowerCase().contains("юл")
                    || element.text().toLowerCase().contains("ю.л.")) {
                index = element.elementSiblingIndex();
                LOG.info(index);
            }
        }
        if (index == -1) {
            return "";
        }
        return elements.select("tr").get(1).select("td").get(index).text();

    }

    @Override
    public String getEmail(String text) {
        Document doc = Jsoup.parse(text);
        Elements elements = doc.select("table");
        int index = -1;
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("email")
                    || element.text().toLowerCase().contains("mail")
                    || element.text().toLowerCase().contains("e-mail")
                    || element.text().toLowerCase().contains("почта")
            ) {
                index = element.elementSiblingIndex();
                LOG.info(index);
            }
        }
        if (index == -1) {
            return "";
        }
        return elements.select("tr").get(1).select("td").get(index).text();
    }

    @Override
    public String getName(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = -1;
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("контактное")
                    || element.text().toLowerCase().contains("фио")
                    || element.text().toLowerCase().contains("ф.и.о")
            ) {
                index = element.elementSiblingIndex();
                LOG.info(index);
            }
        }
        if (index == -1) {
            return "";
        }
        return elements.select("tr").get(1).select("td").get(index).text();
    }

    @Override
    public Item convertTextToObject(String html) {
        return new Item(
                this.getPhone(html),
                this.getName(html),
                this.getCompany(html),
                this.getEmail(html)
        );
    }
}
