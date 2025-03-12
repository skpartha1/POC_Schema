package org.example.enricher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.constant.AppConstants;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class PropertiesMapper implements Function<JsonParser, JsonNode> {

    @Override
    public JsonNode apply(JsonParser jsonNode) {
        ObjectNode outputNode = AppConstants.om.createObjectNode();

        log.info("ACTION=add-property input={}", jsonNode);
        ObjectNode innerNode = AppConstants.om.createObjectNode();
        String currentName = "NA";
        String value = "NA";
        try {
             currentName = jsonNode.getCurrentName();
             value = jsonNode.getText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("ACTION=add-property processing={}", jsonNode);
        innerNode.put("type", determineDataType(jsonNode.currentToken()));

        innerNode.put("description", String.format("%s value", currentName));

        innerNode.put("example", value);

        log.info("ACTION=add-property inner_node={}", innerNode);

        outputNode.set(currentName, innerNode);

        log.info("ACTION=add-property output_node={}", outputNode);
        return outputNode;
    }

    private String determineDataType(String val){
        if(StringUtils.isNumeric(val)) return "Integer";
        if(StringUtils.isAlpha(val)) return "String";

        return "Object";

    }
    private String determineDataType(JsonToken token){
        if(token.isNumeric()) return "Integer";

        return "String";

    }
}
