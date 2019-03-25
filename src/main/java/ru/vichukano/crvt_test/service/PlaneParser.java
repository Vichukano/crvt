package ru.vichukano.crvt_test.service;

import org.springframework.stereotype.Component;
import ru.vichukano.crvt_test.Model.Item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing plain text.
 */
@Component("plain")
public class PlaneParser implements Parser {
    private final String phonePattern = "((7)|(8)|(9))[0-9]{9}";
    private final String fioPattern = "((Ф.И.О.)|(ФИО)).*";
    private final String companyPattern = "((организац)|(компани)|(юридическое)|(юл)|(ю.л.)).*";
    private final String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    public PlaneParser() {

    }

    /**
     * Method for converting to item object.
     *
     * @param text text for parsing.
     * @return item object.
     */
    @Override
    public Item convertTextToObject(String text) {
        return new Item(
                this.getPhone(text),
                this.getName(text),
                this.getCompany(text),
                this.getEmail(text)
        );
    }

    /**
     * Method for parsing phone number.
     *
     * @param text text for parsing.
     * @return phone.
     */
    @Override
    public String getPhone(String text) {
        return this.parseText(
                this.phonePattern,
                text
        );
    }

    /**
     * Method for parsing company name.
     *
     * @param text text for parsing.
     * @return company.
     */
    @Override
    public String getCompany(String text) {
        return this.parseText(
                this.companyPattern,
                text
        );
    }

    /**
     * Method for parsing email.
     *
     * @param text text for parsing.
     * @return email.
     */
    @Override
    public String getEmail(String text) {
        return this.parseText(
                this.emailPattern,
                text
        );
    }

    /**
     * Method for parsing name.
     *
     * @param text text for parsing.
     * @return name.
     */
    @Override
    public String getName(String text) {
        return this.parseText(
                this.fioPattern,
                text
        );
    }

    /**
     * Util method for parsing plain text.
     *
     * @param regexp regular expresion.
     * @param text   text for parsing.
     * @return String result.
     */
    private String parseText(String regexp, String text) {
        Pattern pattern = Pattern.compile(
                regexp,
                Pattern.CASE_INSENSITIVE
        );
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
