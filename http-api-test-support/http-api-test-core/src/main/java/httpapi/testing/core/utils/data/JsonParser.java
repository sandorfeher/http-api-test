package httpapi.testing.core.utils.data;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonParser {
    private final ObjectMapper objectMapper;

    @Value("${http.request.body.root.resource.directory}")
    private String requestBodyRootResourceFolder;

    @Value("${http.request.body.fragment.root.resource.directory}")
    private String requestBodyFragmentResourceFolder;

    /**
     * Reads a http request payload body JSON file from {@code classpath:testDataRootPath}.
     * <p>
     * {@code testDataRootPath} is specified by {@code http.request.payload.root.resource.directory} property.
     *
     * @param relativeFilePath - relative path of the JSON file to load in {@code testDataRootPath}
     * @return The http request payload body.
     */
    @SneakyThrows
    public JsonNode parseHttpRequestBodyJsonFromFile(String relativeFilePath) {
        var resource = Resources.getResource(requestBodyRootResourceFolder + relativeFilePath);
        return objectMapper.readTree(Resources.toString(resource, StandardCharsets.UTF_8));
    }

    /**
     * Reads a http request payload body fragment JSON file from {@code classpath:testDataFragmentRootPath}.
     * <p>
     * {@code testDataRootPath} is specified by {@code http.request.payload.fragment.root.resource.directory} property.
     *
     * @param relativeFilePath - relative path of the JSON file to load in {@code testDataRootPath}
     * @return The http request payload body.
     */
    @SneakyThrows
    public JsonNode parseRequestPayloadFragmentJsonFromFile(String relativeFilePath) {
        var resource = Resources.getResource(requestBodyFragmentResourceFolder + relativeFilePath);
        return objectMapper.readTree(Resources.toString(resource, StandardCharsets.UTF_8));
    }
}

