package ru.vichukano.crvt_test.service;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MsgVerticalParserTest {
    private static String text;

    @BeforeClass
    public static void loadTextFromFile() throws IOException {
        try (
                InputStream stream = MsgVerticalParser.class
                        .getClassLoader()
                        .getResourceAsStream("02 - vertical table.msg")
        ) {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
            System.out.println(text);
        }
    }

    @Test
    public void whenParseNameThenReturnName() {
        MsgVerticalParser parser = new MsgVerticalParser();
        assertThat(parser.getName(text), is("Евгений"));
    }

    @Test
    public void whenParseCompanyThenReturnCompany() {
        MsgVerticalParser parser = new MsgVerticalParser();
        assertThat(parser.getCompany(text), is("ООО Кедр Сибири"));
    }

    @Test
    public void whenParsePhoneThenReturnPhone() {
        MsgVerticalParser parser = new MsgVerticalParser();
        assertThat(parser.getPhone(text), is("+79230009222"));
    }

    @Test
    public void whenParseEmailThenReturnEmail() {
        MsgVerticalParser parser = new MsgVerticalParser();
        assertThat(parser.getEmail(text), is("kedrsibiri22@mail.ru"));
    }

}
