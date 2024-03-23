package com.example.maperfromvk;

import com.example.maperfromvk.models.Root;
import com.example.maperfromvk.service.VKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final VKService vkService;

    @Autowired
    public Controller(VKService vkService) {
        this.vkService = vkService;
    }

    @PutMapping("/convert")
    public void convert(@RequestBody Root root) {
        vkService.parseDialog(root);
    }
}
