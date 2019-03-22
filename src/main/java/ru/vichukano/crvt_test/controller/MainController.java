package ru.vichukano.crvt_test.controller;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vichukano.crvt_test.Model.Item;
import ru.vichukano.crvt_test.service.MsgHorizontalParser;
import ru.vichukano.crvt_test.service.MsgPlaneParser;
import ru.vichukano.crvt_test.service.MsgVerticalParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainController {
    private final MsgHorizontalParser horizontal;
    private final MsgPlaneParser plain;
    private final MsgVerticalParser vertical;
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public MainController(MsgHorizontalParser horizontal, MsgPlaneParser plain, MsgVerticalParser vertical) {
        this.horizontal = horizontal;
        this.plain = plain;
        this.vertical = vertical;
    }


    @PostMapping("/upload/{type}")
    public Item convertItem(
            @RequestParam("file") MultipartFile file,
            @PathVariable("type") int type) {
        Item item = new Item();
        String text;
        try (InputStream stream = new ByteArrayInputStream(file.getBytes())) {
            switch (type) {
                case (1):
                    text = this.getPlaneText(stream);
                    item = this.plain.convertPlainTextToObject(text);
                    break;
                case (2):
                    text = this.getHtmlText(stream);
                    item = this.horizontal.convertHtmlTextToObject(text);
                    break;
                case (3):
                    text = this.getHtmlText(stream);
                    item = this.vertical.convertHtmlTextToObject(text);
                default:
                    break;
            }
        } catch (IOException | ChunkNotFoundException e) {
            e.printStackTrace();
        }
        item.setId(this.counter.incrementAndGet());
        return item;
    }

    private String getPlaneText(InputStream stream) throws IOException, ChunkNotFoundException {
        MAPIMessage message = new MAPIMessage(stream);
        return message.getTextBody();
    }

    private String getHtmlText(InputStream stream) throws IOException {
        return IOUtils.toString(stream, StandardCharsets.UTF_8);
    }
}
