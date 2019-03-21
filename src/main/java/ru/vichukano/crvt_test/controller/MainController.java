package ru.vichukano.crvt_test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vichukano.crvt_test.Model.Item;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/api/msg")
    public Item convertItem(
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file) {


        return new Item();
    }

}
