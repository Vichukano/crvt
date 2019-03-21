package ru.vichukano.crvt_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MsgParser {
    private File file;

    public MsgParser() {

    }

    public String parsePlainMsg(File file) {
        String plain = "";
        try (InputStream in = new FileInputStream(file)) {
            MAPIMessage message = new MAPIMessage(in);
            plain = message.getTextBody();
        } catch (ChunkNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return plain;
    }

    public String convertPlainTextToJson(String plain) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map.put("fio", this.getName(plain));
        map.put("company", this.getCompany(plain));
        map.put("phone", this.getPhone(plain));
        return mapper.writeValueAsString(map);
    }

    public String getPhone(String plain) {
        Pattern phonePattern = Pattern.compile("((7)|(8)|(9))[0-9]{9}");
        Matcher phoneMatcher = phonePattern.matcher(plain);
        StringBuilder sb = new StringBuilder();
        while (phoneMatcher.find()) {
            sb.append(phoneMatcher.group());
        }
        String phone = sb.substring(0, 10);
        return phone;

    }

    /**
     * Из текста вытаскиваем название организации.
     * @param plain
     * @return
     */
    public String getCompany(String plain) {
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
    public String getName(String plain) {
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
