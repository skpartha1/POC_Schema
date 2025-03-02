package org.example.convertor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

public class JsonToYamlConvertorTest {

    ObjectMapper om = new ObjectMapper();

    @Test
    public void testConvertor() throws IOException {

        String input = IOUtils.toString(this.getClass().getResourceAsStream("/input/Request.json"), Charset.defaultCharset());

        JsonToYamlConvertor underTest = new JsonToYamlConvertor();
        String response = underTest.convertJsonToYaml(input);

        assert  response != null;

    }

}