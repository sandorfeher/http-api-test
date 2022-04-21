package httpapi.testing.core.utils.data.template.expression.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Action {
    SET("set"),
    REMOVE("remove");

    @Getter
    private final String keyword;

    public static Action getByKeyword(String keyword) {
        return Arrays.stream(Action.values()).filter(e -> keyword.equals(e.getKeyword())).findFirst().orElseThrow();
    }
}

