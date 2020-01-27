package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderFormTest {
    SelenideElement form = $("form");
    SelenideElement name = form.$("[data-test-id=name] input");
    SelenideElement phone = form.$("[data-test-id=phone] input");
    SelenideElement agreement = form.$("[data-test-id=agreement]");
    SelenideElement button = form.$("button");

    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }


    @Test
    void shouldTest() {
        name.setValue("Иванов Василий");
        phone.setValue("+79270000000");
        agreement.click();
        button.click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! " +
                "Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void invalidNameTest() {
        name.setValue("Petrov Vasya");
        phone.setValue("+79270000000");
        agreement.click();
        button.click();
        $("span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).
                getCssValue("color: #ff5c5c;");
    }

    @Test
    void emptyNameFieldTest() {
        phone.setValue("+79270000000");
        agreement.click();
        button.click();
        $("span.input__sub").shouldHave(exactText("Поле обязательно для заполнения")).
                getCssValue("color: #ff5c5c;");
    }

    @Test
    void emptyTelFieldTest() {
        name.setValue("Иванов Василий");
        agreement.click();
        button.click();
        $("#root > div > form > div:nth-child(2) > span > span > span.input__sub").
                shouldHave(exactText("Поле обязательно для заполнения")).
                getCssValue("color: #ff5c5c;");
    }

    @Test
    void invalidTelTest() {
        name.setValue("Иванов Василий");
        phone.setValue("123456789123456789");
        agreement.click();
        button.click();
        $("#root > div > form > div:nth-child(2) > span > span > span.input__sub").
                shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).
                getCssValue("color: #ff5c5c;");
    }

    @Test
    void agreementNotClickTest() {
        name.setValue("Иванов Вася");
        phone.setValue("+79270000000");
        button.click();
        $("span.checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки" +
                " и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй")).
                getCssValue("color: #ff5c5c!important;");
    }
}
