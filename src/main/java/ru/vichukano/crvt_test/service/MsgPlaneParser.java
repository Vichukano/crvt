package ru.vichukano.crvt_test.service;

import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MsgPlaneParser implements Parser {
    private final String phonePattern = "((7)|(8)|(9))[0-9]{9}";
    private final String fioPattern = "((Ф.И.О.)|(ФИО)).*";
    private final String companyPattern = "((организац)|(компани)|(юридическое)|(юл)|(ю.л.)).*";
    private final String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    public MsgPlaneParser() {

    }

    @Override
    public Item convertTextToObject(String plain) {
        return new Item(
                this.getPhone(plain),
                this.getName(plain),
                this.getCompany(plain),
                this.getEmail(plain)
        );
    }

    @Override
    public String getPhone(String text) {
        return this.parseText(
                this.phonePattern,
                text
        );
    }

    @Override
    public String getCompany(String text) {
        return this.parseText(
                this.companyPattern,
                text
        );
    }

    @Override
    public String getEmail(String text) {
        return this.parseText(
                this.emailPattern,
                text
        );
    }

    @Override
    public String getName(String text) {
        return this.parseText(
                this.fioPattern,
                text
        );
    }

    private String parseText(String regexp, String text) {
        Pattern pattern = Pattern.compile(
                regexp,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb
                .substring(sb.indexOf(":") + 1, sb.length())
                .replace(" ", "");
    }
}
