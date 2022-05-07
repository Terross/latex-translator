package ru.leti.textranslate.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en");

    @Getter
    private final String value;
}
