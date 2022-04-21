package httpapi.testing.core.utils.data.template.expression.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Expression {
    @NonNull
    private final Action action;
    @NonNull
    private final String jsonPathToSet;
    private final Object value;

}
