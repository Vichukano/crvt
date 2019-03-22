package ru.vichukano.crvt_test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

@Component
public class MsgHorizontalParser {

    public MsgHorizontalParser() {

    }

    private String getPhone(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = -1;
        String phone = "";
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("телефон".toLowerCase())
                    || element.text().contains("phone")
                    || element.text().contains("номер телефона")
            ) {
                index = element.elementSiblingIndex();
            }
        }
        for (Element element : elements.select("tr").get(1).select("td")) {
            if (element.elementSiblingIndex() == index) {
                phone = element.text();
            }
        }
        return phone;
    }

    /**
     * Ищем поле с компанией, если нет, то пустая строка.
     *
     * @param html
     * @return
     */
    private String getCompany(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = -1;
        String company = "";
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().toLowerCase().contains("Компания".toLowerCase())
                    || element.text().contains("ЮЛ")
                    || element.text().contains("Фирма")
            ) {
                index = element.elementSiblingIndex();
            }
        }
        for (Element element : elements.select("tr").get(1).select("td")) {
            if (element.elementSiblingIndex() == index) {
                company = element.text();
            }
        }
        return company;
    }

    /**
     * Вытаскиваем индекс колонки где ФИО и по нему находим значение.
     * Улучшить и убрать второй цикл!!!
     *
     * @param html
     * @return
     */
    private String getName(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = 0;
        String name = "";
        for (Element element : elements.select("tr").get(0).select("td")) {
            if (element.text().contains("Контактное")
                    || element.text().contains("ФИО")
                    || element.text().contains("Ф.И.О")
            ) {
                index = element.elementSiblingIndex();
            }
        }
        for (Element element : elements.select("tr").get(1).select("td")) {
            if (element.elementSiblingIndex() == index) {
                name = element.text();
            }
        }
        return name;
    }

    public Item convertHtmlTextToObject(String html) {
        return new Item(
                this.getPhone(html),
                this.getName(html),
                this.getCompany(html)
        );
    }
}
