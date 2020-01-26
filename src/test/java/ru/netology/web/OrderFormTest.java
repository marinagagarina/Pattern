package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderFormTest {
    @Test
    void shouldTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иванов Василий");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! " +
                "Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void invalidNameTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Petrov Vasya");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).
                getCssValue("color: #ff5c5c;");
    }
    @Test
    void emptyNameFieldTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("span.input__sub").shouldHave(exactText("Поле обязательно для заполнения")).
                getCssValue("color: #ff5c5c;");
    }
    @Test
    void emptyTelFieldTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иванов Василий");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("#root > div > form > div:nth-child(2) > span > span > span.input__sub").
                shouldHave(exactText("Поле обязательно для заполнения")).
                getCssValue("color: #ff5c5c;");
    }
    @Test
    void invalidTelTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иванов Василий");
        form.$("[data-test-id=phone] input").setValue("123456789123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("#root > div > form > div:nth-child(2) > span > span > span.input__sub").
                shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).
                getCssValue("color: #ff5c5c;");
    }
    @Test
    void agreementNotClickTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иванов Вася");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("button").click();
        $("span.checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки" +
                " и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй")).
                getCssValue("color: #ff5c5c!important;");
    }

}
