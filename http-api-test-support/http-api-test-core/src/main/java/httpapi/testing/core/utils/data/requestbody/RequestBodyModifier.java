package httpapi.testing.core.utils.data.requestbody;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Modifies http request body field.
 */
public interface RequestBodyModifier {

    /**
     * Update the value at the given path if node exists else insert new field.
     * <p>
     * Examples of how different type of <code>Object</code> value handled
     * <p>
     * - Primitives: Support all primitive types wrapped/unwrapped
     * </p>
     * <p>
     * - List: Value will be represented as a JSON Array
     * </p>
     * - Map/Custom objects: Inner JSON Object will be created.
     * <p>
     *
     * @param requestBody Http request body to be modified
     * @param jsonPath    Path to set
     * @param value       New value
     * @return The modified http request body.
     */
    JsonNode modifyRequestBodyField(JsonNode requestBody, String jsonPath, Object value);

    /**
     * Remove http request body field at the given path.
     *
     * @param requestBody Http request body to be modified
     * @param jsonPath    Path to delete
     * @return The modified request body.
     */
    JsonNode removeRequestBodyField(JsonNode requestBody, String jsonPath);
}
