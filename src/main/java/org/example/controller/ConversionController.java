package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.convertor.JsonToYamlConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class ConversionController {

    @Autowired
    JsonToYamlConvertor convertor;

    @PostMapping("/api/v1/generate/{function}")
    public ResponseEntity generateFile(@PathVariable("function") String function, @RequestBody String input){
        log.info("ACTION=Generate Function={} input={}", function, input);
        try {
            String output = convertor.convertJsonToYaml(input);

            return ResponseEntity.ok(output);
        } catch (IOException e) {
            log.error("ACTION=generate exception", e);
            // implement a exceptioncontroller - advice
            throw new RuntimeException(e);
        }
    }
}
