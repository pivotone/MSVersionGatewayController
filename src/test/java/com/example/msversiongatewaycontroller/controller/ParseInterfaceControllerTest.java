package com.example.msversiongatewaycontroller.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


class ParseInterfaceControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseInterfaceControllerTest.class);

    @Test
    void parseJson() throws JsonProcessingException {
        String docs = "{\"openapi\":\"3.0.3\",\"info\":{\"title\":\"service\",\"description\":\"service api description\",\"termsOfService\":\"http://localhost\",\"contact\":{\"name\":\"Hou ShengMing\",\"url\":\"http://localhost\",\"email\":\"18865290907@163.com\"},\"license\":{\"name\":\"Apache2.0\",\"url\":\"http://www.apache.org/licenses/LICENSE-2.0\"},\"version\":\"0.0.1\"},\"tags\":[{\"name\":\"infos\",\"description\":\"Demo Controller\"}],\"paths\":{\"/api/infos\":{\"get\":{\"tags\":[\"infos\"],\"summary\":\"get infos\",\"operationId\":\"getInfosUsingGET\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Result\"}}}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"extensions\":{\"x-forward-compatible-marker\":\"1\"}},\"post\":{\"tags\":[\"infos\"],\"summary\":\"update infos partly\",\"operationId\":\"updateInfosUsingPOST\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Result\"}}}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"extensions\":{\"x-forward-compatible-marker\":\"1\"}}}},\"components\":{\"schemas\":{\"Result\":{\"title\":\"Result\",\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"string\"},\"data\":{\"type\":\"object\"},\"message\":{\"type\":\"string\"}}}}}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(docs);
        JsonNode info = node.get("info");
        LOGGER.info("Service name is " + info.get("title").asText());

        JsonNode paths = node.get("paths");
        Iterator<String> keys = paths.fieldNames();

        while(keys.hasNext()) {
            String path = keys.next();
            LOGGER.info("解析到的path路径为： " + path);
        }
    }
}