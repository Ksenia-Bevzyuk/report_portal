package io.reportportal.demo.model;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Dashboard {
    String description;
    String name;

    public static String genName() {
        Faker faker = new Faker();
        return faker.app().name();
    }

    public static String genDescription() {
        Faker faker = new Faker();
        return faker.programmingLanguage().name();
    }
}
