package ru.vichukano.crvt_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.simplejavamail.outlookmessageparser.OutlookMessageParser;
import org.simplejavamail.outlookmessageparser.model.OutlookMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MsgHorizontalParser {
    private File file;

    public MsgHorizontalParser() {

    }

    public String parseHtmlMsg(File file) {
        String html = "";
        try (InputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer, 0, in.available());
            html = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public String getPhone(String html) {
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
     * @param html
     * @return
     */
    public String getCompany(String html) {
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
     * @param html
     * @return
     */
    public String getName(String html) {
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

    public String convertHtmlTextToJson(String html) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map.put("fio", this.getName(html));
        map.put("company", this.getCompany(html));
        map.put("phone", this.getPhone(html));
        return mapper.writeValueAsString(map);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
