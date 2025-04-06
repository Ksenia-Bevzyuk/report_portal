package io.reportportal.demo.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties
@Value
@AllArgsConstructor
public class IdApiKey {
    private int id;
    private String name;
}
