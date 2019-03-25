package ru.vichukano.crvt_test.service;

import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlaneParserTest {
    private static String text;

    @BeforeClass
    public static void loadTextFromFile() throws ChunkNotFoundException, IOException {
        try (
                InputStream stream = PlaneParserTest.class
                        .getClassLoader()
                        .getResourceAsStream("03 - plain text.msg")
        ) {
            MAPIMessage message = new MAPIMessage(stream);
            text = message.getTextBody();
        }
    }

    @Test
    public void whenParseNameThenReturnName() {
        PlaneParser parser = new PlaneParser();
        assertThat(parser.getName(text), is("Дмитрий."));
    }

    @Test
    public void whenParseCompanyThenReturnCompany() {
        PlaneParser parser = new PlaneParser();
        assertThat(parser.getCompany(text), is("Дмитрий."));
    }

    @Test
    public void whenParsePhoneThenReturnPhone() {
        PlaneParser parser = new PlaneParser();
        assertThat(parser.getPhone(text), is("9124467370"));
    }

    @Test
    public void whenParseEmailThenReturnEmail() {
        PlaneParser parser = new PlaneParser();
        assertThat(parser.getEmail(text), is("zavarzindv@mail.ru"));
    }

}
