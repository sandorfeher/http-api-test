package httpapi.testing.core.steps.transformer;

import java.util.List;
import java.util.stream.Collectors;

import httpapi.testing.core.utils.data.template.expression.RequestBodyModifierExpressionProcessor;
import httpapi.testing.core.utils.data.template.expression.model.Expression;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CommonTransformer {
    private final RequestBodyModifierExpressionProcessor expressionProcessor;

    @DataTableType
    public List<Expression> transform(DataTable dataTable) {
        return dataTable.entries().stream()
            .map(e -> e.get("expressions"))
            .map(expressionProcessor::process)
            .collect(Collectors.toList());
    }
}
