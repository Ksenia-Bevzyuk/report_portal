package io.reportportal.demo.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Value;

@JsonIgnoreProperties
@Value
@AllArgsConstructor
public class User {
    private int id;
}
