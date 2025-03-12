package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.convertor.JsonToYamlConvertor;
import org.example.util.ZipFileBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class ConversionController {

    @Autowired
    JsonToYamlConvertor convertor;

    @Autowired
    ZipFileBuilder zipFileBuilder;

    @PostMapping("/api/v1/generate/{function}")
    public ResponseEntity generateFile(HttpServletResponse httpServletResponse, @PathVariable("function") String function, @RequestBody String input){
        log.info("ACTION=Generate Function={} input={}", function, input);
        try {
            String output = convertor.convertJsonToYaml(input);
            zipFileBuilder.buildZipFile(httpServletResponse.getOutputStream(), function, output);
            return ResponseEntity.ok(output);
        } catch (IOException e) {
            log.error("ACTION=generate exception", e);
            // implement a exceptioncontroller - advice
            throw new RuntimeException(e);
        }
    }
}
