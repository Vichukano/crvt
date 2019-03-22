package ru.vichukano.crvt_test.service;

import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MsgPlaneParser {

    public MsgPlaneParser() {

    }

    public Item convertPlainTextToObject(String plain) {
        return new Item(
                this.getPhone(plain),
                this.getName(plain),
                this.getCompany(plain)
        );
    }

    private String getPhone(String plain) {
        Pattern phonePattern = Pattern.compile("((7)|(8)|(9))[0-9]{9}");
        Matcher phoneMatcher = phonePattern.matcher(plain);
        StringBuilder sb = new StringBuilder();
        while (phoneMatcher.find()) {
            sb.append(phoneMatcher.group());
        }
        String phone = sb.toString();
        return phone;

    }

    /**
     * Из текста вытаскиваем название организации.
     * @param plain
     * @return
     */
    private String getCompany(String plain) {
        Pattern companyPattern = Pattern.compile("((ОРГАНИЗАЦ)|(организац)).*");
        Matcher companyMatcher = companyPattern.matcher(plain);
        StringBuilder sb = new StringBuilder();
        while (companyMatcher.find()) {
            sb.append(companyMatcher.group());
        }
        String company = sb.substring(sb.indexOf(":") + 1, sb.length());
        company = company.replace(" ", "").replace(".", "");
        return company;
    }

    /**
     * Из текста вытаскиваем ФИО.
     * @param plain
     * @return
     */
    private String getName(String plain) {
        Pattern namePattern = Pattern.compile("((Ф.И.О.)|(ФИО)|(фио)).*");
        Matcher nameMatcher = namePattern.matcher(plain);
        StringBuilder sb = new StringBuilder();
        while (nameMatcher.find()) {
            sb.append(nameMatcher.group());
        }
        String name = sb.substring(sb.indexOf(":") + 1, sb.length());
        name = name.replace(" ", "").replace(".", "");
        return name;
    }
}
