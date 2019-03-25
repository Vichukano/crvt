package ru.vichukano.crvt_test.service;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VerticalParserTest {
    private static String text;

    @BeforeClass
    public static void loadTextFromFile() throws IOException {
        try (
                InputStream stream = VerticalParser.class
                        .getClassLoader()
                        .getResourceAsStream("02 - vertical table.msg")
        ) {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
            System.out.println(text);
        }
    }

    @Test
    public void whenParseNameThenReturnName() {
        VerticalParser parser = new VerticalParser();
        assertThat(parser.getName(text), is("Евгений"));
    }

    @Test
    public void whenParseCompanyThenReturnCompany() {
        VerticalParser parser = new VerticalParser();
        assertThat(parser.getCompany(text), is("ООО Кедр Сибири"));
    }

    @Test
    public void whenParsePhoneThenReturnPhone() {
        VerticalParser parser = new VerticalParser();
        assertThat(parser.getPhone(text), is("+79230009222"));
    }

    @Test
    public void whenParseEmailThenReturnEmail() {
        VerticalParser parser = new VerticalParser();
        assertThat(parser.getEmail(text), is("kedrsibiri22@mail.ru"));
    }

}
