package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TestBoxParametrizedTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void precondition() {
        Selenide.open("https://demoqa.com/text-box");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {"Name1", "Name2"})
    @ParameterizedTest(name = "Проверка формы demoqa.com по имени \"{0}\"")
    void userNameTest(String userName) {
        $("#userName").setValue(userName);
        $("#submit").click();
        $("#output").shouldHave(text(userName));
    }

    @CsvSource({
            "Name1, name1@test.com, street 1",
            "Name2, name2@email.com, street 2"
    })
    @ParameterizedTest(name = "Проверка формы demoqa.com \"{0}\"")
    void commonFormTest(String userName, String userEmail, String userAddress) {
        $("#userName").setValue(userName);
        $("#userEmail").setValue(userEmail);
        $("#currentAddress").setValue(userAddress);
        $("#submit").click();
        $("#output").shouldHave(text(userName), text(userEmail), text(userAddress));
    }
}
