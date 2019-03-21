package ru.vichukano.crvt_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MsgParserTest {

    /**
     * Нужно решить проблемы с кодировкой.
     */
    @Test
    public void whenParseMessageThenConvertItToText() {
        MsgParser parser = new MsgParser();
        String plain = parser.parsePlainMsg(new File("C://msg/03 - plain text.msg"));
        System.out.println(plain);
        assertThat(parser.getName(plain), is("Дмитрий"));
        assertThat(parser.getCompany(plain), is("Дмитрий"));
        assertThat(parser.getPhone(plain), is("9124467370"));
    }

    @Test
    public void whenParsePlainTextToJsonThenReturnJson() throws JsonProcessingException {
        MsgParser parser = new MsgParser();
        String plain = parser.parsePlainMsg(new File("C://msg/03 - plain text.msg"));
        assertThat(parser.convertPlainTextToJson(plain), is("{\"phone\":\"9124467370\",\"company\":\"Дмитрий\",\"fio\":\"Дмитрий\"}"));
    }
}
