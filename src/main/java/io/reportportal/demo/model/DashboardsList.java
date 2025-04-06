package io.reportportal.demo.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@JsonIgnoreProperties
@Value
@AllArgsConstructor
public class DashboardsList {
    List<Dashboard> content;
}
