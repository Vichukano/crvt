package ru.vichukano.crvt_test.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vichukano.crvt_test.domain.Item;
import ru.vichukano.crvt_test.service.Parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Main controller of application.
 */
@RestController
public class MainController {
    @Qualifier("horizontal")
    private final Parser horizontal;
    @Qualifier("plain")
    private final Parser plain;
    @Qualifier("vertical")
    private final Parser vertical;
    private final AtomicLong counter = new AtomicLong();
    private final Log log = LogFactory.getLog(MainController.class);

    @Autowired
    public MainController(Parser horizontal,
                          Parser plain,
                          Parser vertical) {
        this.horizontal = horizontal;
        this.plain = plain;
        this.vertical = vertical;
    }

    /**
     * Method for parsing file and converting parsed fields to JSON.
     *
     * @param file Multipart content from client.
     * @param type integer number of type.
     * @return item object in JSON format.
     */
    @PostMapping("/upload/{type}")
    public ResponseEntity<Item> convertFileToJson(
            @RequestParam("file") MultipartFile file,
            @PathVariable("type") int type) {
        log.info("Incoming type: " + type);
        Item item;
        String text;
        try (InputStream stream = new ByteArrayInputStream(file.getBytes())) {
            switch (type) {
                case (1):
                    text = this.getPlaneText(stream);
                    item = this.plain.convertTextToObject(text);
                    break;
                case (2):
                    text = this.getHtmlText(stream);
                    item = this.horizontal.convertTextToObject(text);
                    break;
                case (3):
                    text = this.getHtmlText(stream);
                    item = this.vertical.convertTextToObject(text);
                    break;
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (IOException | ChunkNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        item.setId(this.counter.incrementAndGet());
        log.info("Converted item: " + item.toString());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Util method for converting InputStream to plain text.
     *
     * @param stream InputStream.
     * @return String with plain text.
     * @throws IOException            may be thrown.
     * @throws ChunkNotFoundException may be thrown.
     */
    private String getPlaneText(InputStream stream) throws IOException, ChunkNotFoundException {
        MAPIMessage message = new MAPIMessage(stream);
        return message.getTextBody();
    }

    /**
     * Util method for converting InputStream to html text.
     *
     * @param stream InputStream.
     * @return String with html text.
     * @throws IOException may be thrown.
     */
    private String getHtmlText(InputStream stream) throws IOException {
        return IOUtils.toString(stream, StandardCharsets.UTF_8);
    }
}
