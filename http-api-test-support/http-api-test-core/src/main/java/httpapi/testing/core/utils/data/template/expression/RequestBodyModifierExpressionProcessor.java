package httpapi.testing.core.utils.data.template.expression;

import httpapi.testing.core.utils.data.template.expression.model.Expression;

/**
 * Process Expressions defined in Cucumber table.
 * <p>
 * Those expressions will be used to resolve http request payload body templates.
 */
public interface RequestBodyModifierExpressionProcessor {
    /**
     * Process an expression from cucumber data table.
     *
     * @param expression - The given expression from cucumber data table
     * @return The parsed {@link Expression}
     */
    Expression process(String expression);
}
