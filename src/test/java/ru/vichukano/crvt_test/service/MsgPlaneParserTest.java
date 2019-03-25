package ru.vichukano.crvt_test.service;

import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MsgPlaneParserTest {
    private static String text;

    @BeforeClass
    public static void loadTextFromFile() throws ChunkNotFoundException, IOException {
        try (
                InputStream stream = MsgPlaneParserTest.class
                        .getClassLoader()
                        .getResourceAsStream("03 - plain text.msg")
        ) {
            MAPIMessage message = new MAPIMessage(stream);
            text = message.getTextBody();
        }
    }

    @Test
    public void whenParseNameThenReturnName() {
        MsgPlaneParser parser = new MsgPlaneParser();
        assertThat(parser.getName(text), is("Дмитрий."));
    }

    @Test
    public void whenParseCompanyThenReturnCompany() {
        MsgPlaneParser parser = new MsgPlaneParser();
        assertThat(parser.getCompany(text), is("Дмитрий."));
    }

    @Test
    public void whenParsePhoneThenReturnPhone() {
        MsgPlaneParser parser = new MsgPlaneParser();
        assertThat(parser.getPhone(text), is("9124467370"));
    }

    @Test
    public void whenParseEmailThenReturnEmail() {
        MsgPlaneParser parser = new MsgPlaneParser();
        assertThat(parser.getEmail(text), is("zavarzindv@mail.ru"));
    }

}
