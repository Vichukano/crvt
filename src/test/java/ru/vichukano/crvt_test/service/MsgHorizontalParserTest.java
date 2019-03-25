package ru.vichukano.crvt_test.service;


import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MsgHorizontalParserTest {
    private static String text;

    @BeforeClass
    public static void loadTextFromFile() throws IOException {
        try (
                InputStream stream = MsgVerticalParser.class
                        .getClassLoader()
                        .getResourceAsStream("01 - horizontal.msg")
        ) {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
    }

    @Test
    public void whenParseNameThenReturnName() {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        assertThat(parser.getName(text), is("Сергей Александрович"));
    }

    @Test
    public void whenParseCompanyThenReturnCompany() {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        assertThat(parser.getCompany(text), is(""));
    }

    @Test
    public void whenParsePhoneThenReturnPhone() {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        assertThat(parser.getPhone(text), is("+79250407624"));
    }

    @Test
    public void whenParseEmailThenReturnEmail() {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        assertThat(parser.getEmail(text), is("auto-pik@mail.ru"));
    }

}
