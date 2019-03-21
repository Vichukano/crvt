package ru.vichukano.crvt_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MsgHorizontalParserTest {

    @Test
    public void whenParseForNameThenReturnName() {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        String html = parser.parseHtmlMsg(new File("C://msg/01 - horizontal table.msg"));
        assertThat(parser.getName(html), is("Сергей Александрович"));
        assertThat(parser.getCompany(html), is(""));
        assertThat(parser.getPhone(html), is("+79250407624"));
    }

    @Test
    public void whenParseHtmlToJsonThenReturnJson() throws JsonProcessingException {
        MsgHorizontalParser parser = new MsgHorizontalParser();
        String html = parser.parseHtmlMsg(new File("C://msg/01 - horizontal table.msg"));
        assertThat(parser.convertHtmlTextToJson(html), is("{\"phone\":\"+79250407624\",\"company\":\"\",\"fio\":\"Сергей Александрович\"}"));
    }


}
