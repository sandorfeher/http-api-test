package httpapi.testing.core.utils.data.template.model;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import httpapi.testing.core.utils.data.template.expression.model.Expression;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RequestBodyTemplate {
    @NonNull
    private final JsonNode resourceTemplate;
    @NonNull
    private final List<Expression> resolutionValues;

}
