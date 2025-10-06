package com.JSONUpdate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonValueUpdater {

    /**
     * Updates a given key's value (top-level or nested) inside a JSON file.
     *
     * @param filePath Absolute path of the JSON file
     * @param key      The key to update
     * @param newValue The new value (String, Boolean, or Number)
     */
    public static void updateJsonValue(String filePath, String key, Object newValue) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File(filePath));
            boolean updated = updateNode(root, key, newValue);
            if (updated) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), root);
                System.out.println("Successfully updated key: \"" + key + "\" to value: " + newValue);
            } else {
                System.out.println("Key \"" + key + "\" not found in JSON.");
            }
        } catch (IOException e) {
            System.err.println("Error updating JSON: " + e.getMessage());
        }
    }
    private static boolean updateNode(JsonNode node, String key, Object newValue) {
        boolean updated = false;
        if (node.isObject()) {
            ObjectNode objNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> fields = objNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                if (entry.getKey().equals(key)) {
                    if (newValue instanceof Boolean)
                        objNode.put(key, (Boolean) newValue);
                    else if (newValue instanceof Integer)
                        objNode.put(key, (Integer) newValue);
                    else if (newValue instanceof Double)
                        objNode.put(key, (Double) newValue);
                    else
                        objNode.put(key, newValue.toString());
                    updated = true;
                } else {
                    updated = updateNode(entry.getValue(), key, newValue) || updated;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayItem : node) {
                updated = updateNode(arrayItem, key, newValue) || updated;
            }
        }
        return updated;
    }
    public static void main(String[] args) {
        String filePath = "/Users/manikanta.c/Desktop/config.json";

        updateJsonValue(filePath, "campaign_id", "HiPOCTeam");
        updateJsonValue(filePath, "commonConfigFilePath", "E:\\NewPath\\CVS-Configuration.json");
        updateJsonValue(filePath, "daysUntilSecondEscalation", 30);
    }
}
