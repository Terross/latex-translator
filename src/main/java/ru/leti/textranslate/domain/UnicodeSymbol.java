package ru.leti.textranslate.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UnicodeSymbol {

    NEW_LINE("%0A"),
    NEW_LINE_GOOGLE("% 0A"),
    LESS("&lt;"),
    MORE("&gt;"),
    APOSTROPHE("&#39;");

    @Getter
    private final String value;
}
