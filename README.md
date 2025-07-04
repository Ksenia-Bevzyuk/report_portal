# Report_Portal

Тестирование UI и API сервиса Report Portal

https://demo.reportportal.io/

** default 1q2w3e **


## Documentation

[Documentation API](https://developers.reportportal.io/api-docs/service-api/)


## Technologies
- Java 17

#### Properties
- maven 17
- aspectj 1.9.7
- allure 2.25.0

#### Dependencies
- junit:junit-jupiter:5.8.2(test)
- io.rest-assured:rest-assured:4.4.0
- com.google.code.gson:gson:2.8.9
- io.qameta.allure:allure-junit5:2.25.0
- io.qameta.allure:allure-rest-assured:2.25.0
- com.github.javafaker:javafaker:1.0.2
- org.projectlombok:lombok:1.18.20(provided)
- org.aeonbits.owner:owner:1.0.12
- org.seleniumhq.selenium:selenium-java:4.27.0
- org.seleniumhq.selenium:selenium-chrome-driver:LATEST

#### Plugins
- maven-surefire-plugin 2.22.2
- org.aspectj:aspectjweaver:1.9.7
- allure-maven 2.10.0
- maven-compiler-plugin 3.5.1
- lombok 1.18.20


## Running Tests

To run tests, run the following command

```bash
  mvn clean test
```

To view the allure-report, run the following command

```bash
  mvn allure:serve
```
