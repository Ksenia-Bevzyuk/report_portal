package io.reportportal.demo.model;

import lombok.AllArgsConstructor;
import lombok.Value;
import java.util.List;

@Value
@AllArgsConstructor
public class ListOfUserApiKey {
    private List<IdApiKey> items;
}
