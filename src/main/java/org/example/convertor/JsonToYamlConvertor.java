package org.example.convertor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.AppConstants;
import org.example.enricher.PropertiesMapper;
import org.example.enricher.SchemaEnricher;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
public class JsonToYamlConvertor {

    PropertiesMapper propertiesMapper = new PropertiesMapper();

    public String convertJsonToYaml(String input) throws IOException {
        log.info("ACTION=convert input={}", input);

        ArrayNode arrNode = AppConstants.om.createArrayNode();

        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(input);

        log.info("ACTION=convert parser={}", jParser);

        while ( jParser.nextToken() != JsonToken.END_OBJECT ) {

            if(jParser.getCurrentName() != null) {
                JsonNode tOutput = propertiesMapper.apply(jParser);

                arrNode.add(tOutput);
            }

        }

        jParser.close();

        JsonNode schema = new SchemaEnricher().apply(arrNode);

        ObjectNode response = AppConstants.om.createObjectNode();
        response.set("content", AppConstants.om.createObjectNode().set("application/json", schema));

        String output = new YAMLMapper().writeValueAsString(response);

        log.info("ACTION=convert output={}", output);

        return output;
    }
}
