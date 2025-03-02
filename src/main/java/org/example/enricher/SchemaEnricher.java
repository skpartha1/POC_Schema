package org.example.enricher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.AppConstants;

import java.util.function.Function;
@Slf4j
public class SchemaEnricher implements Function<JsonNode, JsonNode> {
    @Override
    public JsonNode apply(JsonNode jsonNode) {
        ObjectNode schema = AppConstants.om.createObjectNode();
        schema.put("description", "Schema of the function");
        schema.put("type", "object");
        schema.set("properties", jsonNode);
        return schema;
    }
}
