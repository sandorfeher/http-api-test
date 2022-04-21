package httpapi.testing.core.utils.data.template;

import com.fasterxml.jackson.databind.JsonNode;
import httpapi.testing.core.utils.data.template.model.RequestBodyTemplate;

/**
 * Resolves http request body templates.
 */
public interface RequestBodyTemplateResolver {
    /**
     * Resolves the given {@link RequestBodyTemplate}.
     *
     * @param requestBodyTemplate - Payload template to be resolved
     * @return a http request payload body.
     */
    JsonNode resolve(RequestBodyTemplate requestBodyTemplate);
}
